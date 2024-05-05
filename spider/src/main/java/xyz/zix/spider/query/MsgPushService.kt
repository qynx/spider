package xyz.zix.spider.query

import org.springframework.stereotype.Component
import xyz.zix.spider.cli.WcpCli
import xyz.zix.spider.cli.WxPushCli
import xyz.zix.spider.common.dto.WcpMsgRsp
import xyz.zix.spider.common.dto.WxPushConfigDTO
import xyz.zix.spider.common.utils.ConfigUtils
import xyz.zix.spider.repo.vo.wx.push.MsgType
import xyz.zix.spider.repo.vo.wx.push.WxPushMsgBodyVO
import javax.annotation.Resource

@Component
class MsgPushService {

    @Resource
    lateinit var wxPushCli: WxPushCli

    @Resource
    lateinit var wcpCli: WcpCli

    val wxPushConfig: WxPushConfigDTO = ConfigUtils.loadConfig(WxPushConfigDTO::class.java)

    suspend fun sendWxPushMsg(title: String, content: String, uid: String?) {
        val msg = WxPushMsgBodyVO()
        msg.contentType = MsgType.TEXT.value
        msg.content = content
        msg.summary = title
        uid?.let {
            msg.uids = listOf(it)
        }.let {
            msg.uids = listOf(wxPushConfig.wxPushUid)
        }
        wxPushCli.push(msg)
    }

    suspend fun sendWcpHookMsg(title:String, content:String, type:MsgType) :WcpMsgRsp {
        return wcpCli.sendHookMsg(title, content, type)
    }

}