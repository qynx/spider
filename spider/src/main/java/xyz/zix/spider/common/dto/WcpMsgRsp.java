package xyz.zix.spider.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WcpMsgRsp implements Serializable {

    @JsonProperty(value = "errcode")
    private Integer errCode;

    @JsonProperty(value = "errmsg")
    private String errMsg;

    public boolean success() {
        return this.errCode == 0;
    }
}
