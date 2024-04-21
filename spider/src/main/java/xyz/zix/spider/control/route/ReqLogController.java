package xyz.zix.spider.control.route;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.GraphBarVO;
import xyz.zix.spider.control.vo.GraphRsp;
import xyz.zix.spider.control.vo.ReqLogVO;

import xyz.zix.spider.repo.domain.ReqLogEn;
import xyz.zix.spider.repo.mapper.ReqLogMapper;
import xyz.zix.spider.repo.query.ReqLogQuery;
import xyz.zix.spider.repo.service.sql.ReqLogSqlService;
import xyz.zix.spider.repo.vo.PageVO;
import xyz.zix.spider.repo.vo.ZixRsp;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zix/req_log")
public class ReqLogController extends BaseControlService<ReqLogEn, ReqLogQuery, ReqLogVO, ReqLogSqlService> {

    @Resource
    private ReqLogMapper reqLogMapper;

    @PostMapping("/page")
    @ResponseBody
    public ZixRsp<PageVO<ReqLogVO>> page2(@RequestBody ReqLogQuery query) {
        return ZixRsp.success(page(query, query.getCurrent(), query.getPageSize()));
    }

    @PostMapping("/graph")
    @ResponseBody
    public ZixRsp<GraphRsp> graph() {
        Date now = new Date();
        Date start = DateUtil.offset(now, DateField.MINUTE, -59);
        Map<String, Long> map = new HashMap<>();
        int offMinutes = 0;
        while (true) {
            Date date = DateUtil.offset(now, DateField.MINUTE, offMinutes);
            if (date.getTime() < start.getTime()) {
                break;
            }
            map.put(DateUtil.format(date, "yyyy-MM-dd HH:mm"), 0L);
            offMinutes -= 1;
        }

        List<Map<String, Object>> list = reqLogMapper.selectQpm(start, now);
        for (Map<String, Object> item : list) {
            map.put(MapUtil.getStr(item, "req_time_min"), MapUtil.getLong(item, "cnt"));
        }

        GraphRsp rsp = new GraphRsp();
        rsp.setBar(new GraphBarVO());
        List<String> hxList = map.keySet().stream().sorted().collect(Collectors.toList());
        rsp.getBar().setHx(hxList.stream().map(i -> i.substring(11)).collect(Collectors.toList()));
        rsp.getBar().setVy(hxList.stream().map(i -> map.get(i)).map(i -> Double.valueOf(i)).collect(Collectors.toList()));
        return ZixRsp.success(rsp);
    }
}
