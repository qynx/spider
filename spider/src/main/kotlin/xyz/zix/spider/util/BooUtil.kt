package xyz.zix.spider.util

import cn.hutool.core.io.FileUtil
import xyz.zix.spider.consts.PathConsts
import xyz.zix.spider.repo.vo.ChapterVO
import java.io.File
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.nio.file.Paths

fun saveBook(title:String, chapterList:List<ChapterVO>) {
    val str = StringBuilder()
    str.append(title).append("\n\n")
    chapterList.forEachIndexed{idx, item ->
        str.append("第${idx+1}章 ${item.title} \n\n ${item.content.trim()} \n\n")
    }
    val storePath = Paths.get(PathConsts.DOWNLOAD_PATH, "$title.txt").toString()
    FileUtil.writeString(str.toString(), File(storePath), Charset.defaultCharset())
}