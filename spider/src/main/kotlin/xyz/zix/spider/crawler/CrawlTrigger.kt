package xyz.zix.spider.crawler

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.domain.ScheduleEn
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.enums.ScheduleStatusEnum
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService
import xyz.zix.spider.repo.service.sql.ScheduleSqlService
import java.lang.Exception
import javax.annotation.Resource
import kotlin.collections.HashMap

@Component
class CrawlTrigger(
    private final val applicationContext: ApplicationContext
) {

    val beanMap: HashMap<JobSourceEnum, BaseCrawlHandler> = HashMap()

    init {
        applicationContext.getBeansOfType(BaseCrawlHandler::class.java)
            .values
            .forEach { beanMap[it.crawlSource()] = it }
    }

    @Resource
    lateinit var scheduleSqlService: ScheduleSqlService

    @Resource
    lateinit var crawlJobSqlService: CrawlJobSqlService

    suspend fun trigger(schedule: ScheduleEn) {
        val statusUpdates = ScheduleEn()
        statusUpdates.id = schedule.id
        statusUpdates.status = ScheduleStatusEnum.RUNNING
        scheduleSqlService.updateById(statusUpdates)

        val job = crawlJobSqlService.getById(schedule.jobId)
        val bean = beanMap[job.sourceEnum]
        val scheduleId = schedule.id
        GlobalScope.launch {
            try {
                bean!!.start(job.startUrl, scheduleId)
                statusUpdates.status = ScheduleStatusEnum.SUCCESS
                scheduleSqlService.updateById(statusUpdates)
            } catch (e: Exception) {
                e.printStackTrace()
                statusUpdates.status=  ScheduleStatusEnum.FAIL
                scheduleSqlService.updateById(statusUpdates)
            }
        }
    }

}