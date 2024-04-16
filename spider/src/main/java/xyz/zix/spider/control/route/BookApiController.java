package xyz.zix.spider.control.route;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.consts.PathConsts;
import xyz.zix.spider.repo.vo.ZixRsp;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/zix/book")
public class BookApiController {

    @RequestMapping("/get")
    @ResponseBody
    public Object get() {
        String filePath =
                Paths.get(PathConsts.DOWNLOAD_PATH, "sheng.txt").toString();
        String content = FileUtil.readString(filePath, Charset.defaultCharset());

        List<String> lines = Stream.of(content.split("\n")).collect(Collectors.toList());
        List<String> resLines = new ArrayList<>();
        for (String line : lines) {
            String pure = StringUtils.trim(line);
            Pattern pattern = Pattern.compile("第(\\d+)章");
            if (pattern.matcher(pure).find()) {
                line = String.format("<h3>%s</h3>", line);
            }
            resLines.add(line);
        }
        content = resLines.stream().collect(Collectors.joining("\n"));
        content = content.replace("\n", "<br>");
        content = content.replace(" 　　", "<br><br>&nbsp;&nbsp;");
        content = content.replace(" ", "&nbsp;&nbsp;");
        return ZixRsp.success(content);
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("第(\\d+)章");
        System.out.println(pattern.matcher("第2章   第二").find());
    }
}
