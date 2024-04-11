package xyz.zix.spider.control.route;

import cn.hutool.core.io.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.consts.PathConsts;
import xyz.zix.spider.repo.vo.ZixRsp;

import java.nio.charset.Charset;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/zix/book")
public class BookApiController {

    @RequestMapping("/get")
    @ResponseBody
    public Object get() {
        String filePath =
                Paths.get(PathConsts.DOWNLOAD_PATH, "long.txt").toString();
        String content = FileUtil.readString(filePath, Charset.defaultCharset());
        content = content.replace("\n", "<br>");
        content = content.replace(" 　　", "<br><br>&nbsp;&nbsp;");
        content = content.replace(" ", "&nbsp;&nbsp;");
        return ZixRsp.success(content);
    }
}
