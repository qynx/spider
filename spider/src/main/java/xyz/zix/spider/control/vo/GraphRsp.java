package xyz.zix.spider.control.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.zix.spider.control.vo.graph.GraphLegendVO;
import xyz.zix.spider.control.vo.graph.GraphToolTipVO;

import java.io.Serializable;
import java.util.List;

@Data
public class GraphRsp implements Serializable {

    private GraphCommonBarVO bar;

    private GraphTitleVO title;

    @JsonProperty("xAxis")
    private GraphAxisVo xAxis;

    @JsonProperty("yAxis")
    private GraphAxisVo yAxis;

    private GraphToolTipVO tooltip;

    private GraphLegendVO legend;

    private List<GraphSeriesVO> series;

}
