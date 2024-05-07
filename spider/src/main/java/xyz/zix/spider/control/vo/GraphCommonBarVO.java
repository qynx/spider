package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GraphCommonBarVO implements Serializable {

    private String type = "line";

    private String title;

    private List<String> hx;

    private List<Double> vy;

    private List<Integer> vyInt;

}
