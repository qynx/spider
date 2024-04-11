package xyz.zix.spider.common.utils;

import cn.hutool.core.io.FileUtil;
import xyz.zix.spider.consts.PathConsts;
import xyz.zix.spider.repo.utils.JsonUtils;

import java.nio.charset.Charset;

public class ConfigUtils {

    public static  <T> T loadConfig(Class<T> type) {
        return loadConfig(PathConsts.DEFAULT_CONFIG_FILE_PATH, type);
    }

    public static  <T> T loadConfig(String path, Class<T> type) {
        String str = FileUtil.readString(path, Charset.defaultCharset());
        return JsonUtils.fromJson(str, type);
    }
}
