package xyz.zix.spider.control.vo;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GraphItemVO implements Serializable {

    private Serializable value;

    private String name;
}
