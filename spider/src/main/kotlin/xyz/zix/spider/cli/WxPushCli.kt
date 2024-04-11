package xyz.zix.spider.cli

import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import xyz.zix.spider.common.dto.WxPushConfigDTO
import xyz.zix.spider.common.utils.ConfigUtils
import xyz.zix.spider.repo.service.rest.RestReq
import xyz.zix.spider.repo.utils.JsonUtils
import xyz.zix.spider.repo.vo.wx.push.WxPushMsgBodyVO
import xyz.zix.spider.repo.vo.wx.push.MsgType
import javax.annotation.Resource

@Component
class WxPushCli {

    private final val BASE_URL: String = "https://wxpusher.zjiecode.com";
    private final val SEND_MSG_URL: String = "${BASE_URL}/api/send/message"
    private final val config:WxPushConfigDTO = ConfigUtils.loadConfig(WxPushConfigDTO::class.java)
    @Resource
    private lateinit var restCli: RestCli
    val log:Log = LogFactory.get()

    suspend fun push(title: String, content:String) {
        val msg = WxPushMsgBodyVO()
        msg.uids = listOf(config.wxPushUid)
        msg.content = content
        msg.summary = title
        msg.contentType = MsgType.TEXT.value
        push(msg)
    }

    suspend fun push(msg: WxPushMsgBodyVO) {
        val req = RestReq()
        req.method = HttpMethod.POST
        req.url = SEND_MSG_URL
        val map = JsonUtils.toMap(msg)
        map["appToken"] = config.wxPushToken
        req.body = JsonUtils.toJson(map)
        req.headers.add("Content-Type", "application/json")
        restCli.req(req)
        log.info("send inform finish ${req.rspBody} ${req.rspStatusCode}")
    }
}