package xyz.zix.spider.crawler

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import xyz.zix.spider.cli.DownloadCli
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.service.rest.RestReq
import javax.annotation.Resource

abstract class BaseCrawlHandler {

    @Resource
    lateinit var downloadCli: DownloadCli

    abstract fun crawlSource(): JobSourceEnum

    abstract suspend fun start(startUrl: String, scheduleId:Long)

    suspend fun quickDownload(url: String, scheduleId: Long?) :RestReq {
        val req = RestReq()
        req.url = url
        return restReq(req, scheduleId)
    }

    suspend fun restReq(
        jobId: Long?,
        method: HttpMethod, url: String,
        param: Map<String, Any>, body: String,
        header: HttpHeaders,
    ): RestReq {
        val req = RestReq()
        req.method = method
        req.url = url
        req.param = param
        req.body = body
        req.headers = header
        return restReq(req, jobId)
    }

    suspend fun restReq(req: RestReq, scheduleId: Long?): RestReq {
        return downloadCli.req(req, scheduleId)
    }

}