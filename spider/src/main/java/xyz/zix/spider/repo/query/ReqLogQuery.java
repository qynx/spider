package xyz.zix.spider.repo.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqLogQuery implements Serializable {

    private Long scheduleId;

    private Long current;

    private Long pageSize;
}
