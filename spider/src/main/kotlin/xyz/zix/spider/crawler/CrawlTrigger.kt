package xyz.zix.spider.crawler

import cn.hutool.log.Log
import cn.hutool.log.LogFactory
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
import java.util.*
import javax.annotation.Resource
import kotlin.collections.HashMap

@Component
class CrawlTrigger(
    private final val applicationContext: ApplicationContext
) {

    val beanMap: HashMap<JobSourceEnum, BaseJobHandler> = HashMap()
    val log:Log = LogFactory.get()

    init {
        applicationContext.getBeansOfType(BaseJobHandler::class.java)
            .values
            .forEach {
                log.info("${it.crawlSource()} load bean")
                beanMap[it.crawlSource()] = it }
    }

    @Resource
    lateinit var scheduleSqlService: ScheduleSqlService

    @Resource
    lateinit var crawlJobSqlService: CrawlJobSqlService


    suspend fun trigger(schedule: ScheduleEn) {
        val statusUpdates = ScheduleEn()
        statusUpdates.id = schedule.id
        statusUpdates.status = ScheduleStatusEnum.RUNNING
        statusUpdates.startTime = Date()
        scheduleSqlService.updateById(statusUpdates)

        val job = crawlJobSqlService.getById(schedule.jobId)
        val bean = beanMap[job.sourceEnum]
        val scheduleId = schedule.id
        GlobalScope.launch {
            try {
                bean!!.start(job.startUrl, scheduleId)
                statusUpdates.status = ScheduleStatusEnum.SUCCESS
                statusUpdates.endTime = Date()
                scheduleSqlService.updateById(statusUpdates)
            } catch (e: Exception) {
                log.error("", e)
                statusUpdates.endTime = Date()
                statusUpdates.status=  ScheduleStatusEnum.FAIL
                scheduleSqlService.updateById(statusUpdates)
            }
        }
    }

}