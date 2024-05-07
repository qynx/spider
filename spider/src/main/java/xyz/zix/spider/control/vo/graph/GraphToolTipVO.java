package xyz.zix.spider.control.vo.graph;

import lombok.Data;

import java.io.Serializable;

@Data
public class GraphToolTipVO implements Serializable {

    private String trigger = "item";

    private String formatter;

}
