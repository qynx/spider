package xyz.zix.spider.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WcpMsgDTO implements Serializable {

    private String agentid;

    private String touser;

    private String msgtype;

    private WcpMsgTextVO text;

}
