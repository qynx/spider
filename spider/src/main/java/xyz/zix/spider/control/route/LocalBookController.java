package xyz.zix.spider.control.route;

import cn.hutool.core.io.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.consts.PathConsts;

import java.nio.charset.Charset;
import java.nio.file.Paths;

@RestController
@RequestMapping("/zix/book")
public class LocalBookController {


    @RequestMapping("/render")
    public Object render() {
        String filePath =
        Paths.get(PathConsts.DOWNLOAD_PATH, "sheng.txt").toString();
        String content = FileUtil.readString(filePath, Charset.defaultCharset());
        content = content.replace("\n", "<br>");
        return new ResponseEntity<String>(content, HttpStatus.OK);
    }
}