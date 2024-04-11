package xyz.zix.spider.crawler

import cn.hutool.core.date.DateUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import javax.annotation.Resource

@Component
class EchoCrawler : BaseCrawlHandler() {

    val log: Log = LogFactory.get()

    @Resource
    private lateinit var scheduleSqlService: ScheduleSqlService
    @Resource
    private lateinit var crawlJobSqlService: CrawlJobSqlService

    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.ECHO
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {
        val schedule = scheduleSqlService.getById(scheduleId)
        val job = crawlJobSqlService.getById(schedule.jobId)

        log.info("trigger echo content: $startUrl name: ${job.name} time: ${DateUtil.format(schedule.scheduleTime, "yy/MM/dd HH:mm:ss")}")
    }

}