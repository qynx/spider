package xyz.zix.spider.cli

import cn.hutool.crypto.digest.DigestUtil
import cn.hutool.crypto.digest.MD5
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.domain.ReqLogEn
import xyz.zix.spider.repo.enums.ContentTypeEnum
import xyz.zix.spider.repo.service.rest.RestReq
import xyz.zix.spider.repo.service.sql.ReqLogSqlService
import xyz.zix.spider.repo.utils.JsonUtils
import java.lang.Exception
import java.time.Duration
import java.util.*
import javax.annotation.Resource

@Component
class DownloadCli {

    @Resource
    lateinit var reqLogSqlService: ReqLogSqlService

    @Resource
    lateinit var restCli: RestCli

    @Resource
    lateinit var stringRedisTemplate: StringRedisTemplate

    suspend fun get(url: String): RestReq {
        val req = RestReq()
        req.method = HttpMethod.GET
        req.url = url
        return req(req, null)
    }

    suspend fun post(url: String, bod: String?): RestReq {
        val req = RestReq()
        req.method = HttpMethod.POST
        req.url = url
        req.body = bod
        return req(req, null)
    }

    suspend fun doReq(req: RestReq): RestReq {
        val key = DigestUtil.md5Hex(req.url) + ":" + (
                req.headers?.let {
                    if (it.isEmpty()) {
                        ""
                    } else {
                        DigestUtil.md5Hex(it.toString())
                    }
                } ?: {
                    ""
                })
        if (HttpMethod.GET == req.method) {
            val last:String? = stringRedisTemplate.opsForValue().get(key)
            if (!last.isNullOrEmpty()) {
                val lastReq = JsonUtils.fromJson(last, RestReq::class.java)
                req.rspStatusCode = lastReq.rspStatusCode
                req.rspBody = lastReq.rspBody
                req.rspHeader = lastReq.rspHeader
                return req
            }
        }
        val newReq = restCli.req(req)
        if (HttpMethod.GET == newReq.method && HttpStatus.OK.value() == newReq.rspStatusCode) {
            stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(newReq), Duration.ofMinutes(60))
        }
        return newReq
    }

    suspend fun req(req: RestReq, scheduleId: Long?): RestReq {
        val start = System.currentTimeMillis()
        val reqLog = ReqLogEn()

        try {
            doReq(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val end = System.currentTimeMillis()

        reqLog.url = req.url
        reqLog.method = req.method?.name ?: "GET"
        reqLog.reqBody = req.body
        reqLog.reqHeader = JsonUtils.toJson(req.headers)
        reqLog.consumeTime = (end - start)
        reqLog.reqTime = Date(start)
        reqLog.rspStatus = req.rspStatusCode ?: -1
        reqLog.rspBody = req.rspBody
        reqLog.scheduleId = scheduleId
        reqLog.rspHeader = JsonUtils.toJson(req.rspHeader)
        if (Objects.nonNull(req.rspHeader)) {
            val rspContentType = req.rspHeader.getFirst("Content-Type")
            val type = ContentTypeEnum.derive(rspContentType)
            reqLog.rspContentType = type
        }
        reqLogSqlService.save(reqLog)

        req.rspLogId = reqLog.id

        return req
    }
}
