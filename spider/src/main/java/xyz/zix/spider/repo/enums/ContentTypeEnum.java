package xyz.zix.spider.repo.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum ContentTypeEnum {

    HTML("html"),
    JSON("json"),
    TEXT("text"),
    IMAGE("image"),
    VIDEO("video"),

    ;

    @EnumValue
    private final String type;

    public static ContentTypeEnum derive(String httpRspType) {
        if (StringUtils.isEmpty(httpRspType)) {
            return TEXT;
        }
        if (httpRspType.contains("image")) {
            return IMAGE;
        }
        if (httpRspType.contains("mp4")) {
            return VIDEO;
        }
        if (httpRspType.contains("html")) {
            return HTML;
        }
        if (httpRspType.contains("json")) {
            return JSON;
        }
        return TEXT;
    }

}
