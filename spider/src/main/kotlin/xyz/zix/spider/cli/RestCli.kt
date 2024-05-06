package xyz.zix.spider.cli

import cn.hutool.core.codec.Base64
import cn.hutool.core.io.IoUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpMethod
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.util.UriComponentsBuilder
import xyz.zix.spider.repo.service.rest.RestReq
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

class RestCli constructor(
    private val webClient: WebClient
) {
    val log: Log = LogFactory.get()

    suspend fun get(url: String): RestReq {
        val req = RestReq()
        req.method = HttpMethod.GET
        req.url = url
        return req(req)
    }

    suspend fun req(req: RestReq): RestReq {
        val webReq = webClient.method(req.method)
        req.headers?.forEach {
            for (item in it.value) {
                webReq.header(it.key, item)
            }
        }
        val urlBuilder = UriComponentsBuilder.fromUriString(req.url)
        if (req.param != null) {
            req.param.forEach {
                if (it.value is String) {
                    urlBuilder.queryParam(it.key, it.value)
                } else if (it.value is List<*>) {
                    for (v in it.value as List<*>) {
                        urlBuilder.queryParam(it.key, v)
                    }
                }
            }
        }
        webReq.uri(urlBuilder.build().toUri())
        if (!req.body.isNullOrBlank()) {
            webReq.bodyValue(req.body)
        }

        try {

            webReq.awaitExchange {
                val rspArray = it.awaitBodyOrNull<ByteArrayResource>()
                log.info("rsp array len ${rspArray!!.byteArray.size}")
                req.rspBody = String(rspArray!!.byteArray, Charset.defaultCharset())
                req.rspByte = Base64.encode(rspArray!!.byteArray)
                req.rspHeader = it.headers().asHttpHeaders()
                req.rspStatusCode = it.rawStatusCode()

                log.info("req ${req.url} finish ${req.rspStatusCode}")
            }
        } catch (e: Exception) {
            log.info("req ${req.url} has error ${e::class.java} ")

            if (e is HttpStatusCodeException) {
                req.rspStatusCode = e.statusCode.value()
                req.rspHeader = e.responseHeaders
                req.rspBody = e.responseBodyAsString
            } else {
                e.printStackTrace()
                req.rspEx = e
            }
            req.rspEx = e
        }

        return req
    }

}