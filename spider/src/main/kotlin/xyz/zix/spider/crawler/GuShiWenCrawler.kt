package xyz.zix.spider.crawler

import org.seimicrawler.xpath.JXDocument
import org.springframework.stereotype.Component
import xyz.zix.spider.cli.RestCli
import xyz.zix.spider.common.dto.PoetryDTO
import xyz.zix.spider.repo.enums.JobSourceEnum
import javax.annotation.Resource

@Component
class GuShiWenCrawler : BaseJobHandler() {

    @Resource
    lateinit var restCli: RestCli

    override fun crawlSource(): JobSourceEnum {
        return JobSourceEnum.GU_SHI_WEN
    }

    override suspend fun start(startUrl: String, scheduleId: Long) {

    }

    suspend fun parseByLink(url:String) : PoetryDTO {
        val html = restCli.get(url).rspBody
        val root = JXDocument.create(html)

        val contentDiv = root.selNOne("//div[@id='sonsyuanwen']//div[@class='contson']")
        val contentHtml = contentDiv.asElement().html()
        var content = contentHtml.replace("<br>", "\n")
        content = content.replace("\n\n", "\n")

        val title = root.selNOne("//div[@id='sonsyuanwen']//h1").asElement().text()

        val author = root.selNOne("//div[@id='sonsyuanwen']//p[@class='source']//a").asElement().text()

        val res = PoetryDTO()
        res.link = url
        res.content = content
        res.author = author
        res.title = title
        return res
    }

}