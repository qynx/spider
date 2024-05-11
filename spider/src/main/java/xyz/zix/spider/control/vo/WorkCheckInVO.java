package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkCheckInVO implements Serializable {

    private Long id;

    private Long durationMinute;

    private String start;

    private String end;

    private String date;

}
