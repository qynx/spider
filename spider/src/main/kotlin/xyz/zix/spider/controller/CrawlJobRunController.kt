package xyz.zix.spider.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import xyz.zix.spider.crawler.CrawlTrigger
import xyz.zix.spider.repo.domain.ScheduleEn
import xyz.zix.spider.repo.enums.ScheduleStatusEnum
import xyz.zix.spider.repo.enums.ScheduleTypeEnum
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import xyz.zix.spider.repo.vo.ZixRsp
import java.util.*
import javax.annotation.Resource

@RestController
@RequestMapping
class CrawlJobRunController {


    @Resource
    lateinit var crawlTrigger: CrawlTrigger
    @Resource
    lateinit var crawlJobSqlService: CrawlJobSqlService
    @Resource
    lateinit var scheduleSqlService: ScheduleSqlService

    @RequestMapping("/api/zix/crawl_job/run")
    @ResponseBody
    suspend fun run(@RequestParam("id") id:Long ) :ZixRsp<Long> {
        val schedule = ScheduleEn()
        schedule.jobId = id
        schedule.type = ScheduleTypeEnum.MANUAL
        schedule.status = ScheduleStatusEnum.WAIT
        schedule.scheduleTime = Date()
        scheduleSqlService.save(schedule)
        crawlTrigger.trigger(schedule)
        return ZixRsp.success(schedule.id)
    }
}