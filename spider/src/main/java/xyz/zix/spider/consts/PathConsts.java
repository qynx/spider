package xyz.zix.spider.consts;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import javax.print.DocFlavor;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class PathConsts {

    public static final String HOME_PATH = System.getProperty("user.home");

    public static final String CONFIG_PATH = Paths.get(HOME_PATH, ".config").toString();

    public static final String DOCUMENTS_PATH = Paths.get(HOME_PATH, "Documents").toString();

    public static final String BLOG_PROJECT_PATH = Paths.get(HOME_PATH, "open_repos", "sblg").toString();

    public static final String BLOG_POETRY_SRC_PATH = Paths.get(BLOG_PROJECT_PATH, "src", "posts", "诗词").toString();

    public static final String DEFAULT_CONFIG_FILE_PATH = Paths.get(CONFIG_PATH, "spider.config").toString();

    public static final String DOWNLOAD_PATH = Paths.get(HOME_PATH, "Downloads").toString();


    public static void main(String[] args) throws Throwable {
        String html = FileUtil.readString(Paths.get(DOWNLOAD_PATH, "test.html").toString(), Charset.defaultCharset());
        JXDocument root = JXDocument.create(html);
        JXNode jxNode = root.selNOne("//div[@class='txtnav']");
        String content = jxNode.asElement().html();
        List<JXNode> list = jxNode.sel("/div");
        Pattern pattern = Pattern.compile("<div([\\s\\S]*?)>([\\s\\S]*?)</div>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        content = pattern.matcher(content).replaceAll("");
        pattern = Pattern.compile("<h1([\\s\\S]*?)>([\\s\\S]*?)</h1>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        content = pattern.matcher(content).replaceAll("");
        content = content.replaceAll("<br>", "");
        System.out.println(content.trim());

    }
}
