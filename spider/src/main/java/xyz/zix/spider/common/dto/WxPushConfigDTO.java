package xyz.zix.spider.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxPushConfigDTO implements Serializable {

    private String wxPushUid;
    private String wxPushToken;

}
