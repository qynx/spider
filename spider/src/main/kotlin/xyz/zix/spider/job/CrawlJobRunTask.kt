package xyz.zix.spider.job

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.domain.CrawlJobEn
import xyz.zix.spider.repo.domain.ScheduleEn
import xyz.zix.spider.repo.enums.CrawlJobStatusEnum
import xyz.zix.spider.repo.enums.ScheduleStatusEnum
import xyz.zix.spider.repo.enums.ScheduleTypeEnum
import xyz.zix.spider.repo.query.CrawlJobQuery
import xyz.zix.spider.repo.query.CrawlScheduleQuery
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import xyz.zix.spider.utils.ZixCronUtils
import java.util.*
import javax.annotation.Resource

@Component
class CrawlJobRunTask {

    @Resource
    private lateinit var crawlJobSqlService: CrawlJobSqlService

    @Resource
    private lateinit var scheduleSqlService: ScheduleSqlService

    @Scheduled(fixedDelay = 1000L)
    fun schedule() {
        val query = CrawlJobQuery()
        query.status = CrawlJobStatusEnum.RUNNING
        query.nextTriggerTimeMax = System.currentTimeMillis() + 60000L
        query.nextTriggerTimeMin = System.currentTimeMillis() - 3 * 86400000L
        val list = crawlJobSqlService.list(query)

        for (job in list) {
            if (job.cron.isNullOrBlank()) {
                continue
            }
            if (!CronExpression.isValidExpression(job.cron)) {
                continue
            }

            val nextDate = ZixCronUtils.nextExecTime(job.cron, job.nextTriggerTime)
            val updates = CrawlJobEn()
            updates.id = job.id
            updates.nextTriggerTime = nextDate
            crawlJobSqlService.updateById(updates)

            insert(job, Date(job.nextTriggerTime))
        }
    }

    fun insert(job: CrawlJobEn, triggerTime: Date) {
        val en = ScheduleEn()
        en.scheduleTime = triggerTime
        en.jobId = job.id
        en.status = ScheduleStatusEnum.WAIT
        en.type = ScheduleTypeEnum.AUTO

        val query = CrawlScheduleQuery()
        query.jobId = en.jobId
        query.type = ScheduleTypeEnum.AUTO
        query.scheduleTime = triggerTime
        val list = scheduleSqlService.list(query)
        if (list.isNotEmpty()) {
            return
        }
        scheduleSqlService.save(en)
    }

}