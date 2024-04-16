package xyz.zix.spider.control.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleVO implements Serializable {

    private Long id;

    private Date scheduleTime;

    private String status;

    private Date startTime;

    private Date endTime;

    private String consumeTimeStr;

    private String scheduleTimeStr;

    private Date createdAt;

    private Date updatedAt;

    private Long totalCount;

    private Long finishCount;

    private String processRatio;
}
