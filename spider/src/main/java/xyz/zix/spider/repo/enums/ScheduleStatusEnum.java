package xyz.zix.spider.repo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScheduleStatusEnum {

    WAIT(1),
    RUNNING(2),
    SUCCESS(3),
    FAIL(4),
    SUSPEND(5),
    ;

    @EnumValue
    private final Integer value;

}
