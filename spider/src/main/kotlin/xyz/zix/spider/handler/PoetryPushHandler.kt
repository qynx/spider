package xyz.zix.spider.handler

import org.springframework.stereotype.Component
import xyz.zix.spider.query.MsgPushService
import xyz.zix.spider.repo.domain.PoetryEn
import xyz.zix.spider.repo.service.sql.PoetrySqlService
import xyz.zix.spider.repo.vo.wx.push.MsgType
import javax.annotation.Resource

@Component
class PoetryPushHandler {

    @Resource
    lateinit var msgPushService: MsgPushService
    @Resource
    lateinit var poetrySqlService: PoetrySqlService

    suspend fun push(id:Long) {
        val item = poetrySqlService.getById(id)
        push(item)
    }

    suspend fun push(item:PoetryEn) {
        val title = "${item.title} [${item.author}]"
        msgPushService.sendWcpHookMsg(title, item.content, MsgType.TEXT)
    }
}