package xyz.zix.spider.control.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class GraphAxisVo implements Serializable {

    private String type;

    private List<Object> data;

}
