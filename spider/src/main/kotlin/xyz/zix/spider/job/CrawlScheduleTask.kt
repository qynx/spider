package xyz.zix.spider.job

import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import xyz.zix.spider.crawler.CrawlTrigger
import xyz.zix.spider.repo.enums.ScheduleStatusEnum
import xyz.zix.spider.repo.query.CrawlScheduleQuery
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import java.util.*
import javax.annotation.Resource

@Component
class CrawlScheduleTask {

    @Resource
    lateinit var scheduleSqlService: ScheduleSqlService
    @Resource
    lateinit var crawlJobService: CrawlJobSqlService
    @Resource
    lateinit var crawlTrigger: CrawlTrigger

    @Scheduled(fixedDelay = 1000L)
    fun trigger() {
        val query = CrawlScheduleQuery()
        query.status = ScheduleStatusEnum.WAIT
        query.maxScheduleTime = Date()
        query.last = " order by schedule_time "
        val list = scheduleSqlService.list(query)

        runBlocking {
            for (item in list) {
                crawlTrigger.trigger(item)
            }
        }

    }

}