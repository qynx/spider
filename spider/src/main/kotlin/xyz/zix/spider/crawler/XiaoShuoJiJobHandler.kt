package xyz.zix.spider.crawler

import cn.hutool.core.codec.Base64
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.seimicrawler.xpath.JXDocument
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.vo.ChapterVO
import xyz.zix.spider.util.saveBook
import java.nio.charset.Charset

@Component
class XiaoShuoJiJobHandler : BaseCrawlHandler() {

    // http://www.xiaoshuojiji.com/

    val log:Log = LogFactory.get()

    val host = "http://www.xiaoshuojiji.com"

    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.XIAO_SHUO_JI
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {
        val list = findAllChapter(startUrl, scheduleId)

        scheduleSqlService.initTotalCnt(scheduleId, list.size.toLong())

        for (item in list) {
            item.content = parseContent(item.fullUrl, scheduleId)
            scheduleSqlService.addFinishCount(scheduleId, 1)
        }

        saveBook("下载完成 " + crawlSource(), list)
        inform(scheduleId)
    }

    suspend fun parseContent(url:String, scheduleId: Long) : String {
        val html = String(Base64.decode(quickDownload(url, scheduleId).rspByte), Charset.forName("gbk"))
        val root = JXDocument.create(html)

        val contentDiv = root.selNOne("//p[@id='articlecontent']")
        var contentHtml = contentDiv.asElement().html()

        contentHtml = contentHtml.replace("&nbsp;", " ")
        contentHtml = contentHtml.replace("<br>", "\n")
        return contentHtml
    }


    suspend fun findAllChapter(url: String, scheduleId: Long): ArrayList<ChapterVO> {
        val list = ArrayList<ChapterVO>()
        parseChapter(url, scheduleId, list)
        for (i in 1..list.size) {
            list[i - 1].index = i
        }
        return list
    }

    suspend fun parseChapter(url: String, scheduleId: Long, list: ArrayList<ChapterVO>) {
        val html = String(Base64.decode(quickDownload(url, scheduleId).rspByte), Charset.forName("gbk"))
        val root = JXDocument.create(html)

        val chapterLiList = root.selN("//div[@class='ml_list']//ul//li//a")

        for (item in chapterLiList) {

            val r = ChapterVO()

            r.fullUrl = url + item.asElement().attr("href")
            r.title = item.asElement().text()

            list.add(r)
        }
    }
}