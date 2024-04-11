package xyz.zix.spider.repo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xyz.zix.spider.repo.enums.JobSourceEnum;
import xyz.zix.spider.repo.enums.CrawlJobStatusEnum;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "crawl_job", autoResultMap = true)
public class CrawlJobEn implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "`name`")
    private String name;

    private JobSourceEnum sourceEnum;

    private String cron;

    private String startUrl;

    private CrawlJobStatusEnum status;

    private Long nextTriggerTime;

    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

}