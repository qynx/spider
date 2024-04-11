package xyz.zix.spider.repo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrawlJobStatusEnum {

    RUNNING(1),
    STOP(2),
    ;

    @EnumValue
    private final Integer value;

}
