package xyz.zix.spider.control.vo.graph;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GraphToolTipVO implements Serializable {

    private String trigger = "item";

    private String formatter;

}
