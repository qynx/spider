package xyz.zix.spider.cli

import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import xyz.zix.spider.common.dto.WcpMsgDTO
import xyz.zix.spider.common.dto.WcpMsgRsp
import xyz.zix.spider.common.dto.WcpMsgTextVO
import xyz.zix.spider.common.dto.WxCoprConfigDTO
import xyz.zix.spider.common.utils.ConfigUtils
import xyz.zix.spider.repo.service.rest.RestReq
import xyz.zix.spider.repo.utils.JsonUtils
import xyz.zix.spider.repo.vo.wx.push.MsgType
import java.lang.RuntimeException
import java.util.StringJoiner
import javax.annotation.Resource

@Component
class WcpCli {

    val config: WxCoprConfigDTO = ConfigUtils.loadConfig(WxCoprConfigDTO::class.java)
    val log: Log = LogFactory.get()

    @Resource
    lateinit var restCli: RestCli

    suspend fun sendHookMsg(title: String, content: String, type: MsgType): WcpMsgRsp {
        val msg = WcpMsgDTO()
        msg.msgtype = type.wcpValue
        when {
            type == MsgType.TEXT -> {
                val text = WcpMsgTextVO()
                text.content = "${title}\n\n${content}"
                msg.text = text
            }

            else -> throw RuntimeException("not support type " + type)
        }
        return sendHookMsg(msg)
    }

    suspend fun sendHookMsg(msg: WcpMsgDTO): WcpMsgRsp {
        val req = RestReq()
        req.method = HttpMethod.POST
        req.url = config.wxCorpHookUrl + "&debug=1"
        req.body = JsonUtils.toJson(msg)
        req.headers.add("Content-Type", "application/json")
        restCli.req(req)
        log.info("sendHookMsg ${req.body} finish ${req.rspStatusCode} ${req.rspBody}")
        return JsonUtils.fromJson(req.rspBody, WcpMsgRsp::class.java)
    }
}