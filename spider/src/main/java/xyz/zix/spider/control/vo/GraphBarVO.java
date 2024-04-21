package xyz.zix.spider.control.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class GraphBarVO implements Serializable {

    private String type = "line";

    private List<String> hx;

    private List<Double> vy;

}
