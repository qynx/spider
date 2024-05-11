package xyz.zix.spider.control.route;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.common.utils.CommonUtils;
import xyz.zix.spider.consts.PathConsts;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.GraphAxisVo;
import xyz.zix.spider.control.vo.GraphRsp;
import xyz.zix.spider.control.vo.GraphSeriesVO;
import xyz.zix.spider.control.vo.WorkCheckInVO;
import xyz.zix.spider.control.vo.graph.GraphLegendVO;
import xyz.zix.spider.control.vo.graph.GraphToolTipVO;
import xyz.zix.spider.repo.domain.WorkCheckInEn;
import xyz.zix.spider.repo.mapper.WorkCheckInMapper;
import xyz.zix.spider.repo.query.WorkCheckInQuery;
import xyz.zix.spider.repo.service.sql.WorkCheckInSqlService;
import xyz.zix.spider.repo.utils.JsonUtils;
import xyz.zix.spider.repo.vo.ZixRsp;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zix/work/check_in")
public class WorkCheckInController extends BaseControlService<
        WorkCheckInEn, WorkCheckInQuery, WorkCheckInVO, WorkCheckInSqlService> {

    @RequestMapping("/parse")
    @ResponseBody
    public Object parse() {
        String path = Paths.get(PathConsts.DOCUMENTS_PATH, "check-in", "202403.json").toString();
        String json = FileUtil.readString(path, Charset.defaultCharset());
        List<WorkCheckInEn> list = JsonUtils.fromJson(json, new TypeReference<List<WorkCheckInEn>>() {
        });
        list.forEach(it -> it.setDurationMinute(it.calDurationByStartEnd()));
        commonMapper.insertWorkCheckIn(list);
        return ZixRsp.success(null);
    }

    @RequestMapping("mul_month/graph")
    @ResponseBody
    public Object mulMonthGraph() {
        String monthStart = "2024-03-01";
        String monthEnd = "2024-04-30";
        List<WorkCheckInEn> list = service.list(new WorkCheckInQuery().setDateEnd(monthEnd).setDateStart(monthStart));

        final GraphRsp graphRsp = new GraphRsp();

        List<Integer> points = CommonUtils.range2List(1, 31);
        graphRsp.setLegend(new GraphLegendVO());

        graphRsp.setXAxis(new GraphAxisVo().setType("category")
                .setData(new ArrayList<>(points)));

        graphRsp.setYAxis(new GraphAxisVo().setType("value"));

        graphRsp.setTooltip(new GraphToolTipVO().setTrigger("item"));

        graphRsp.setSeries(new ArrayList<>());

        Map<String, List<WorkCheckInEn>> monthGroup = list.stream()
                .collect(Collectors.groupingBy(it -> it.getDate().substring(0, 7)));

        for (Map.Entry<String, List<WorkCheckInEn>> entry : monthGroup.entrySet()) {
            GraphSeriesVO seriesVO = new GraphSeriesVO();
            seriesVO.setName(entry.getKey());
            seriesVO.setType("line");
            List<Integer> value = CommonUtils.range2List(1, 31);
            for (WorkCheckInEn item : entry.getValue()) {
                String date = item.getDate();
                int day = Integer.parseInt(date.substring(date.length() - 2));
                value.set(day - 1, item.getDurationMinute());
            }
            seriesVO.setData(new ArrayList<>(value));
            graphRsp.getSeries().add(seriesVO);
        }

        return ZixRsp.success(graphRsp);
    }

    @RequestMapping("/curr_month/graph")
    @ResponseBody
    public Object graph() {
        GraphRsp graphRsp = new GraphRsp();

        List<WorkCheckInEn> list = service.list(new WorkCheckInQuery());
        list = list.stream().sorted(Comparator.comparing(WorkCheckInEn::getDate)).collect(Collectors.toList());

        graphRsp.setXAxis(new GraphAxisVo());
        graphRsp.getXAxis().setType("category");
        graphRsp.getXAxis().setData(list.stream().map(WorkCheckInEn::getDate).collect(Collectors.toList()));

        graphRsp.setYAxis(new GraphAxisVo());
        graphRsp.getYAxis().setType("value");

        graphRsp.setTooltip(new GraphToolTipVO());
        graphRsp.getTooltip().setTrigger("item");
        graphRsp.getTooltip().setFormatter("{c}min({c/60}h)");


        graphRsp.setSeries(new ArrayList<>());
        GraphSeriesVO seriesVO = new GraphSeriesVO();
        seriesVO.setData(list.stream().map(WorkCheckInEn::getDurationMinute).collect(Collectors.toList()));
        seriesVO.setType("line");
        graphRsp.getSeries().add(seriesVO);

        return ZixRsp.success(graphRsp);
    }


}
