package xyz.zix.spider.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import xyz.zix.spider.cli.WcpCli
import xyz.zix.spider.cli.WxPushCli
import xyz.zix.spider.common.dto.WcpMsgDTO
import xyz.zix.spider.common.dto.WcpMsgTextVO
import xyz.zix.spider.repo.vo.ZixRsp
import xyz.zix.spider.repo.vo.wx.push.MsgType
import java.util.StringJoiner
import javax.annotation.Resource

@RestController
@RequestMapping("/api/zix/message")
class MessageController {

    @Resource
    lateinit var wxPushCli: WxPushCli
    @Resource
    lateinit var wcpCli: WcpCli

    @RequestMapping("/send")
    @ResponseBody
    suspend fun sendMsg(@RequestParam("title") title:String, @RequestParam("content") content: String) :ZixRsp<Any?> {
        val msg = WcpMsgDTO()
        msg.msgtype = MsgType.TEXT.wcpValue
        msg.text = WcpMsgTextVO()
        msg.text.content =  content
        wcpCli.sendHookMsg(msg)
        return ZixRsp.success(null)
    }
}