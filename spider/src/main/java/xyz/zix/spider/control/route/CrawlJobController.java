package xyz.zix.spider.control.route;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.CrawlJobSummaryVO;
import xyz.zix.spider.control.vo.CrawlJobVO;
import xyz.zix.spider.exceptions.CommonException;
import xyz.zix.spider.repo.domain.CrawlJobEn;
import xyz.zix.spider.repo.enums.JobSourceEnum;
import xyz.zix.spider.repo.enums.CrawlJobStatusEnum;
import xyz.zix.spider.repo.enums.ScheduleStatusEnum;
import xyz.zix.spider.repo.query.CrawlJobQuery;
import xyz.zix.spider.repo.query.CrawlScheduleQuery;
import xyz.zix.spider.repo.service.sql.CrawlJobSqlService;
import xyz.zix.spider.repo.service.sql.ScheduleSqlService;
import xyz.zix.spider.repo.vo.ZixRsp;
import xyz.zix.spider.utils.ZixCronUtils;

import javax.annotation.Resource;
import java.util.Objects;


@RestController
@RequestMapping("/api/zix/crawl_job")
public class CrawlJobController extends BaseControlService<CrawlJobEn, CrawlJobQuery, CrawlJobVO, CrawlJobSqlService> {

    @Resource
    private ScheduleSqlService scheduleSqlService;


    protected void prepareSave(CrawlJobVO last, CrawlJobVO curr) {
        if (StringUtils.isNotBlank(curr.getCron())) {
            boolean valid = CronExpression.isValidExpression(curr.getCron());
            if (!valid) {
                throw new CommonException("cron表达式非法");
            }
        }
        if (null == last) {
            if (null == curr.getSourceEnum()) {
                curr.setSourceEnum(JobSourceEnum.TE_SE_NOVEL);
            }
            if (Objects.isNull(curr.getStatus())) {
                curr.setStatus(CrawlJobStatusEnum.RUNNING);
            }
            if (StringUtils.isNotBlank(curr.getCron()) && CronExpression.isValidExpression(curr.getCron())) {
                curr.setNextTriggerTime(
                        ZixCronUtils.nextExecTime(curr.getCron())
                );
            }
        } else {
            if (StringUtils.isNotBlank(curr.getCron()) && !Objects.equals(curr.getCron(), last.getCron())) {
                curr.setNextTriggerTime(ZixCronUtils.nextExecTime(curr.getCron()));
            }
        }
    }

    @PostMapping("/recover")
    @ResponseBody
    public Object recover(@RequestBody CrawlJobVO req) {
        CrawlJobVO crawlJobVO = get(req.getId());
        if (CrawlJobStatusEnum.RUNNING.equals(crawlJobVO.getStatus())) {
            return ZixRsp.success(crawlJobVO);
        }
        CrawlJobEn en = new CrawlJobEn();
        en.setId(crawlJobVO.getId());
        en.setStatus(CrawlJobStatusEnum.RUNNING);
        en.setNextTriggerTime(ZixCronUtils.nextExecTime(crawlJobVO.getCron(), System.currentTimeMillis()));
        service.updateById(en);
        return get2(crawlJobVO.getId());
    }

    @PostMapping("/del")
    public Object del2(@RequestParam("id") Long id) {
        return ZixRsp.success(del(id));
    }

    @GetMapping("/suspend")
    public Object suspend(@RequestParam("id") Long id) {
        CrawlJobVO vo  = get(id);
        CrawlJobVO updates = new CrawlJobVO();
        updates.setId(id);
        updates.setStatus(CrawlJobStatusEnum.STOP);
        update(updates);
        return ZixRsp.success(get(id));
    }

    @GetMapping("/summary")
    @ResponseBody
    public Object summary(@RequestParam("id") Long id) {
        CrawlJobSummaryVO summaryVO = new CrawlJobSummaryVO();
        CrawlScheduleQuery scheduleQuery = new CrawlScheduleQuery();
        scheduleQuery.setJobId(id);
        summaryVO.setTotalScheduleCount(scheduleSqlService.count(scheduleQuery));
        scheduleQuery.setStatus(ScheduleStatusEnum.RUNNING);
        summaryVO.setRunningScheduleCount(scheduleSqlService.count(scheduleQuery));
        summaryVO.setDownloadCnt(0L);
        return ZixRsp.success(summaryVO);
    }

    @GetMapping("/get")
    @ResponseBody
    public Object get2(@RequestParam("id") Long id) {
        CrawlJobVO r = get(id);
        return ZixRsp.success(r);
    }

    @PostMapping("/page")
    @ResponseBody
    public Object page(@RequestBody CrawlJobQuery query) {
        return ZixRsp.success(page(query, query.getCurrent(), query.getPageSize(), " order by id desc "));
    }


    @PostMapping("/save")
    @ResponseBody
    public Object save2(@RequestBody CrawlJobVO vo) {
        return ZixRsp.success(save(vo));
    }

}
