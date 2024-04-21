package xyz.zix.spider.repo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import xyz.zix.spider.repo.enums.ContentTypeEnum;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "req_log", autoResultMap = true)
@Getter
@Setter
public class ReqLogEn implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String method;

    private String reqBody;

    private String rspBody;

    private String reqHeader;

    private String rspHeader;

    private String url;

    private ContentTypeEnum rspContentType;

    private Integer rspStatus;

    private Long jobId;

    private Date reqTime;

    private String reqTimeMin;

    private Long consumeTime;

    private Long scheduleId;

    private Date createdAt;

    private Date updatedAt;

}

