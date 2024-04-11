package xyz.zix.spider.repo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ScheduleTypeEnum {

    MANUAL(1),
    AUTO(2),
    ;

    @EnumValue
    private final Integer value;

}
