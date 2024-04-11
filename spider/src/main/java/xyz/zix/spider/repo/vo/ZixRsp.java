package xyz.zix.spider.repo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ZixRsp<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public Boolean getSuccess() {
        return code == 0;
    }

    public static <R> ZixRsp<R> ofFail(Integer code, String msg) {
        return new ZixRsp<R>().setCode(code).setMsg(msg);
    }

    public static <R> ZixRsp<R> success(R data) {
        return new ZixRsp<R>().setCode(0).setData(data);
    }
}
