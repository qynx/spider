package xyz.zix.spider.control.route;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.ScheduleVO;
import xyz.zix.spider.repo.domain.ScheduleEn;
import xyz.zix.spider.repo.enums.ScheduleStatusEnum;
import xyz.zix.spider.repo.query.CrawlScheduleQuery;
import xyz.zix.spider.repo.service.sql.ScheduleSqlService;
import xyz.zix.spider.repo.vo.ZixRsp;


@RestController
@RequestMapping("/api/zix/schedule")
public class CrawlJobScheduleController extends BaseControlService<ScheduleEn, CrawlScheduleQuery, ScheduleVO, ScheduleSqlService> {



    @PostMapping("/page")
    @ResponseBody
    public Object page(@RequestBody CrawlScheduleQuery query) {
        return ZixRsp.success(page(query, query.getCurrent(), query.getPageSize()));
    }

    @PostMapping("/suspend")
    @ResponseBody
    public Object suspend(@RequestParam("id") Long id) {
        get(id);
        ScheduleEn en = new ScheduleEn();
        en.setId(id);
        en.setStatus(ScheduleStatusEnum.SUSPEND);
        service.updateById(en);
        return get(id);
    }

    @PostMapping("/recover")
    @ResponseBody
    public Object recover(@RequestParam("id") Long id) {
        get(id);
        ScheduleEn en = new ScheduleEn();
        en.setId(id);
        en.setStatus(ScheduleStatusEnum.WAIT);
        service.updateById(en);
        return get(id);
    }

}
