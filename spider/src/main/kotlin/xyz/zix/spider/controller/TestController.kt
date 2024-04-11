package xyz.zix.spider.controller

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import xyz.zix.spider.cli.DownloadCli
import xyz.zix.spider.cli.RestCli
import xyz.zix.spider.repo.service.rest.RestReq
import xyz.zix.spider.repo.vo.ZixRsp
import javax.annotation.Resource

@RestController
@RequestMapping("/api/zix/test")
class TestController {

    @Resource
    lateinit var restCli: RestCli
    @Resource
    lateinit var downloadCli: DownloadCli

    @RequestMapping("/req")
    @ResponseBody
    suspend fun req(@RequestParam("count", required = false) count: Int?): ZixRsp<Any> {
        val cnt = count ?: 1
        val curr = System.currentTimeMillis()
        coroutineScope {
            val list = ArrayList<Deferred<RestReq>>()

            for (i in 1..cnt) {
                list.add(
                    async {
                        downloadCli.post("http://127.0.0.1:8080/", "")
                    })
            }

            for (item in list) {
                item.await()
            }
        }
        return ZixRsp.success(System.currentTimeMillis() - curr)
    }
}