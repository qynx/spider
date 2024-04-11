package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReqLogVO implements Serializable {

    private Long id;

    private String method;

    private String reqBody;

    private String rspBody;

    private String reqHeader;

    private String rspHeader;

    private String url;

    private Integer rspStatus;

    private String rspContentType;

    private Long jobId;

    private Date reqTime;

    private Long consumeTime;

    private Long scheduleId;

    private Date createdAt;

    private Date updatedAt;

}
