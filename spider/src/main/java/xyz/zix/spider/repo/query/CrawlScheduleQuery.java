package xyz.zix.spider.repo.query;

import lombok.Data;
import xyz.zix.spider.repo.enums.ScheduleStatusEnum;
import xyz.zix.spider.repo.enums.ScheduleTypeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
public class CrawlScheduleQuery implements Serializable {

    private Long current;

    private Long pageSize;

    private ScheduleTypeEnum type;

    private Date scheduleTime;

    private ScheduleStatusEnum status;

    private Date maxScheduleTime;

    private Long jobId;

    private String last;
}
