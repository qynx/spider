package xyz.zix.spider.crawler

import cn.hutool.core.date.DateUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import org.seimicrawler.xpath.JXDocument
import org.springframework.stereotype.Component
import xyz.zix.spider.repo.enums.JobSourceEnum
import xyz.zix.spider.repo.vo.BookVO
import xyz.zix.spider.repo.vo.ChapterVO
import xyz.zix.spider.repo.vo.ContentVO
import xyz.zix.spider.util.saveBook

@Component
class TeSeXiaoShuoCrawler : BaseJobHandler() {

    val schemaHost = "https://k.tesexiaoshuo.com"
    val log: Log = LogFactory.get()


    override suspend fun start(startUrl: String, scheduleId: Long) {
        val bookInfo = parseBookInfo(startUrl, scheduleId)

        val chapterList = findAllChapter(startUrl, scheduleId)

        scheduleSqlService.setTotalCnt(scheduleId, chapterList.size.toLong())

        log.info("find chapter finish total ${chapterList.size}")
        for (chapter in chapterList) {
            val chContent = quickDownload(chapter.fullUrl, scheduleId)
            chapter.content = parseContent(chapter, chContent.rspBody).content
            scheduleSqlService.addFinishCount(scheduleId, 1)
        }

        saveBook(bookInfo.title, chapterList)
        inform(scheduleId)
    }

    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.TE_SE_NOVEL
    }

    suspend fun parseBookInfo(url: String, schId: Long): BookVO {
        val html = quickDownload(url, schId).rspBody
        val doc = JXDocument.create(html)
        val titleLink = doc.selNOne("//h1[@class='bookTitle']//a")

        val res = BookVO()
        res.title = titleLink.asElement().text()
        return res
    }

    suspend fun findAllChapter(url: String, scheduleId: Long): ArrayList<ChapterVO> {
        val list = ArrayList<ChapterVO>()
        parseChapter(url, scheduleId, list)
        for (i in 1..list.size) {
            list[i - 1].index = i
        }
        return list
    }

    suspend fun parseChapter(url: String, scheduleId: Long, chapterList: ArrayList<ChapterVO>) {
        val body = quickDownload(url, scheduleId).rspBody
        val doc = JXDocument.create(body)
        val linkList = doc.selN("//dd[@class='col-sm-3']//a")
        for (item in linkList) {
            val fullUrl = schemaHost + item.asElement().attr("href")
            val vo = ChapterVO()
            vo.fullUrl = fullUrl
            vo.title = item.asElement().attr("title")
            chapterList.add(vo)
            log.info("find chapter ${vo.title} ${vo.fullUrl} ${chapterList.size}")
        }

        // 判断是否有下一页
        val pageEleList = doc.selN("//div[@class='listpage']")
        if (pageEleList.size < 1) {
            return
        }
        val pageEle = pageEleList[0]
        val nextEleList = pageEle.sel("//a")
        if (nextEleList.size < 2) {
            return
        }
        val nextEle = nextEleList[1].asElement()
        if (nextEle.text().contains("下一页")) {
            val link = nextEle.attr("href")
            log.info("find link $link")
            parseChapter(schemaHost + link, scheduleId, chapterList)
        }
    }

    fun parseContent(chapter: ChapterVO, html: String): ContentVO {
        val doc = JXDocument.create(html)
        val divContent = doc.selNOne("//div[@id='booktxt']")
        var text = divContent.asElement().html()
        text = text.replace("<p>", "")
        text = text.replace("</p>", "\n")

        val res = ContentVO()
        res.content = text
        return res
    }


}