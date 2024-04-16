package xyz.zix.spider.control.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CrawlJobSummaryVO implements Serializable {

    private Long totalScheduleCount;

    private Long runningScheduleCount;

    private Long downloadCnt;

}