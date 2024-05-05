package xyz.zix.spider.controller

import org.springframework.web.bind.annotation.*
import xyz.zix.spider.common.dto.PoetryDTO
import xyz.zix.spider.control.route.PoetryController
import xyz.zix.spider.control.vo.PoetrySourceVO
import xyz.zix.spider.control.vo.PoetryVO
import xyz.zix.spider.crawler.GuShiWenCrawler
import xyz.zix.spider.handler.PoetryPushHandler
import xyz.zix.spider.repo.vo.ZixRsp
import javax.annotation.Resource
import kotlin.coroutines.Continuation

@RestController
@RequestMapping("/api/zix/poetry")
class PoetryKtController {

    @Resource
    lateinit var poetryPushHandler: PoetryPushHandler

    @Resource
    lateinit var guShiWenCrawler: GuShiWenCrawler

    @Resource
    lateinit var poetryController: PoetryController

    @PostMapping("/save_source")
    @ResponseBody
    suspend fun saveSource(@RequestBody poetrySourceVO: PoetrySourceVO): Any {
        val dto = guShiWenCrawler.parseByLink(poetrySourceVO.link)

        val vo = PoetryVO()
        vo.title = dto.title
        vo.author = dto.author
        vo.content = dto.content
        return poetryController.save2(vo)
    }

    @RequestMapping("/push")
    @ResponseBody
    suspend fun push(@RequestParam("id") id: Long): ZixRsp<Any?> {
        poetryPushHandler.push(id)
        return ZixRsp.success(null)
    }
}