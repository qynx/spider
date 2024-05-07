package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GraphSeriesVO implements Serializable {

    private String name;

    private String type;

    private  String radius = "50%";

    private List<Object> data;

}
