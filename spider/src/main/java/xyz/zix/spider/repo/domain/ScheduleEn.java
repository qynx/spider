package xyz.zix.spider.repo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xyz.zix.spider.repo.enums.ScheduleStatusEnum;
import xyz.zix.spider.repo.enums.ScheduleTypeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "`schedule`", autoResultMap = true)
public class ScheduleEn implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long jobId;

    private ScheduleStatusEnum status;

    private ScheduleTypeEnum type;

    private Date scheduleTime;

    private String ext;

    private Date createdAt;

    private Date updatedAt;
}
