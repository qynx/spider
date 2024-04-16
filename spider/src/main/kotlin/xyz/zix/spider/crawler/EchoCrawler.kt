package xyz.zix.spider.crawler

import cn.hutool.core.date.DateUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import javax.annotation.Resource

@Component
class EchoCrawler : BaseJobHandler() {

    val log: Log = LogFactory.get()


    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.ECHO
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {
        val schedule = scheduleSqlService.getById(scheduleId)
        val job = crawlJobSqlService.getById(schedule.jobId)

        log.info("trigger echo content: $startUrl name: ${job.name} time: ${DateUtil.format(schedule.scheduleTime, "yy/MM/dd HH:mm:ss")}")
    }

}