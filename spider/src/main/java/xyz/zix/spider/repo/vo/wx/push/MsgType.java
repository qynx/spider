package xyz.zix.spider.repo.vo.wx.push;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgType {

    TEXT(1, "text"),
    HTML(2, null),
    MARKDOWN(3, "markdown"),
    ;

    private final int value;

    private final String wcpValue;

}
