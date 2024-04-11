package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ScheduleVO implements Serializable {

    private Long id;

    private Date scheduleTime;

    private String status;

    private String scheduleTimeStr;

    private Date createdAt;

    private Date updatedAt;
}
