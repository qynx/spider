package xyz.zix.spider.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import xyz.zix.spider.handler.PoetryPushHandler
import xyz.zix.spider.repo.vo.ZixRsp
import javax.annotation.Resource

@RestController
@RequestMapping("/api/zix/poetry")
class PoetryKtHandler {

    @Resource
    lateinit var poetryPushHandler: PoetryPushHandler

    @RequestMapping("/push")
    @ResponseBody
    suspend fun push(@RequestParam("id") id:Long) : ZixRsp<Any?> {
        poetryPushHandler.push(id)
        return ZixRsp.success(null)
    }
}