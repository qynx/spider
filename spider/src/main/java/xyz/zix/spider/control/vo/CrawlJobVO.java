package xyz.zix.spider.control.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import xyz.zix.spider.repo.enums.JobSourceEnum;
import xyz.zix.spider.repo.enums.CrawlJobStatusEnum;

import java.io.Serializable;
import java.util.Date;

@Data
public class CrawlJobVO implements Serializable {

    private Long id;

    private String name;

    private JobSourceEnum sourceEnum;

    private CrawlJobStatusEnum status;

    private String cron;

    private String startUrl;

    private Long nextTriggerTime;

    private String nextTriggerTimeStr;

    @TableLogic
    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

}
