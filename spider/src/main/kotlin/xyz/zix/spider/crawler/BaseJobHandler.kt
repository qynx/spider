package xyz.zix.spider.crawler

import cn.hutool.core.date.DateUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import xyz.zix.spider.cli.DownloadCli
import xyz.zix.spider.query.MsgPushService
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.enums.ScheduleTypeEnum
import xyz.zix.spider.repo.service.rest.RestReq
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import xyz.zix.spider.repo.vo.wx.push.MsgType
import javax.annotation.Resource

abstract class BaseCrawlHandler() : BaseJobHandler() {
}

abstract class BaseJobHandler {

    @Resource
    lateinit var downloadCli: DownloadCli
    @Resource
    lateinit var scheduleSqlService: ScheduleSqlService
    @Resource
    lateinit var crawlJobSqlService: CrawlJobSqlService
    @Resource
    lateinit var msgPushService: MsgPushService

    abstract fun crawlSource(): JobSourceEnum

    abstract suspend fun start(startUrl: String, scheduleId:Long)

    suspend fun inform(scheduleId: Long) {
        val sch = scheduleSqlService.getById(scheduleId)
        val job = crawlJobSqlService.getById(sch.jobId)
        if (sch.type == ScheduleTypeEnum.MANUAL) {
            msgPushService.sendWcpHookMsg(
                "${job.name} 执行完成","${job.name} 执行完成 调度时间 ${DateUtil.format(sch.scheduleTime, "yy/MM/dd HH:mmm")}",
                MsgType.TEXT
            )
        }
    }

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