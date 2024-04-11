package xyz.zix.spider.consts;

import java.nio.file.Paths;

public class PathConsts {

    public static final String HOME_PATH = System.getProperty("user.home");

    public static final String CONFIG_PATH = Paths.get(HOME_PATH, ".config").toString();

    public static final String DEFAULT_CONFIG_FILE_PATH = Paths.get(CONFIG_PATH, "spider.config").toString();

    public static final String DOWNLOAD_PATH = Paths.get(HOME_PATH, "Downloads").toString();


}
