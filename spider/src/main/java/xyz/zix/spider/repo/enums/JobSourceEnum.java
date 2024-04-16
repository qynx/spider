package xyz.zix.spider.repo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobSourceEnum {

    ECHO(0),
    TE_SE_NOVEL(1),
    POETRY_PUSH(2),
    SHU_69(3),
    ;


    @EnumValue
    private final Integer code;
}
