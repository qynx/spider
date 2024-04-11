package xyz.zix.spider.repo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PoetryTypeEnum {
    SHI(1, "诗"),
    CI(2, "词"),
    ;

    @EnumValue
    private final Integer value;
    private final String label;
}

