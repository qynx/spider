package xyz.zix.spider.crawler

import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import kotlinx.coroutines.runBlocking
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import xyz.zix.spider.handler.PoetryPushHandler
import xyz.zix.spider.query.MsgPushService
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.service.sql.PoetrySqlService
import xyz.zix.spider.repo.vo.wx.push.MsgType
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Component
class PoetryPushCp : BaseCrawlHandler() {

    @Resource
    lateinit var poetrySqlService: PoetrySqlService

    @Resource
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Resource
    lateinit var msgPushService: MsgPushService
    @Resource
    lateinit var poetryPushHandler: PoetryPushHandler

    val log: Log = LogFactory.get()

    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.POETRY_PUSH
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {
        val poetryList = poetrySqlService.list()
        val prefix = "poetryPush1  D:"
        poetryList.shuffle()

        for (item in poetryList) {
            if (stringRedisTemplate.opsForValue().get(prefix + item.id) != null) {
                continue
            }
            poetryPushHandler.push(item)
            stringRedisTemplate.opsForValue().set(prefix + item.id, "1", 30, TimeUnit.DAYS)
            return
        }
    }
}