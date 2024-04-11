package xyz.zix.spider.repo.vo.wx.push;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxPushMsgBodyVO implements Serializable {

    private String content;

    private String summary;

    /**
     * @see MsgType
     */
    private Integer contentType;

    private List<String> uids;

}
