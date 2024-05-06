package xyz.zix.spider.crawler


import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.seimicrawler.xpath.JXDocument
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.vo.BookVO
import xyz.zix.spider.repo.vo.ChapterVO
import xyz.zix.spider.util.saveBook
import java.util.regex.Pattern

@Component
class Shu69JobHandler : BaseJobHandler() {

    val log:Log = LogFactory.get()
    // https://www.69shu.pro


    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.SHU_69
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {
        val bookInfo = parseBookInfo(startUrl, scheduleId)

        val allChapter = findAllChapter(startUrl, scheduleId)
        scheduleSqlService.setTotalCnt(scheduleId, allChapter.size.toLong())

        for (item in allChapter) {
            val content = parseChapter(item.fullUrl, scheduleId)
            item.content = content
        }

        saveBook(bookInfo.title, allChapter)
        inform(scheduleId)
    }

    suspend fun parseBookInfo(startUrl: String, scheduleId: Long): BookVO {
        val content = quickDownload(startUrl, scheduleId).rspBody

        val root = JXDocument.create(content)
        val res = BookVO()
        res.title = root.selNOne("//div[@class='booknav2']//a").asElement().text()
        return res
    }

    suspend fun parseChapter(url: String, scheduleId: Long): String {
        val text = quickDownload(url, scheduleId).rspBody
        val root = JXDocument.create(text)
        val contentEle = root.selNOne("//div[@class='txtnav']")
        var html = contentEle.asElement().html()

        var pattern = Pattern.compile("<h1([\\s\\S]*?)>([\\s\\S]*?)</h1>")
        html = pattern.matcher(html).replaceAll("")
        pattern = Pattern.compile("<div([\\s\\S]*?)>([\\s\\S]*?)</div>")
        html = pattern.matcher(html).replaceAll("")

        html = html.replace("<br>", "")
        return html
    }

    suspend fun findAllChapter(startUrl: String, scheduleId: Long): ArrayList<ChapterVO> {
        val text = quickDownload(startUrl, scheduleId).rspBody
        val root = JXDocument.create(text)
        val chapterEleList = root.selN("//div[@class='catalog']//ul//a")

        val list = ArrayList<ChapterVO>()
        for (ele in chapterEleList) {
            val vo = ChapterVO()
            vo.title = ele.asElement().text()
            vo.fullUrl = ele.asElement().attr("href")
            list.add(vo)
        }
        for (n in 1..list.size) {
            list[n - 1].index = n
        }
        return list
    }

}