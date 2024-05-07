package xyz.zix.spider.control.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GraphAxisVo implements Serializable {

    private String type;

    private List<Object> data;

}
