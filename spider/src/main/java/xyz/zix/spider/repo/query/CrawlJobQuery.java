package xyz.zix.spider.repo.query;

import lombok.Data;
import xyz.zix.spider.repo.enums.CrawlJobStatusEnum;

import java.io.Serializable;

@Data
public class CrawlJobQuery implements Serializable {

    private Long current;

    private Long pageSize;

    private CrawlJobStatusEnum status;

    private Long nextTriggerTimeMax;

    private Long nextTriggerTimeMin;

}
