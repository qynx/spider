package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import xyz.zix.spider.repo.domain.ScheduleEn;
import xyz.zix.spider.repo.mapper.CrawlScheduleMapper;
import xyz.zix.spider.repo.query.CrawlScheduleQuery;

import java.util.Objects;

@Repository
public class ScheduleSqlService extends BaseSqlService<CrawlScheduleMapper, ScheduleEn, CrawlScheduleQuery> {

    @Override
    LambdaQueryWrapper<ScheduleEn> toWrapper(CrawlScheduleQuery crawlScheduleQuery) {
        return new LambdaQueryWrapper<ScheduleEn>()
                .eq(Objects.nonNull(crawlScheduleQuery.getType()), ScheduleEn::getType, crawlScheduleQuery.getType())
                .eq(Objects.nonNull(crawlScheduleQuery.getJobId()), ScheduleEn::getJobId, crawlScheduleQuery.getJobId())
                .eq(Objects.nonNull(crawlScheduleQuery.getStatus()), ScheduleEn::getStatus, crawlScheduleQuery.getStatus())
                .eq(Objects.nonNull(crawlScheduleQuery.getScheduleTime()), ScheduleEn::getScheduleTime, crawlScheduleQuery.getScheduleTime())
                .le(Objects.nonNull(crawlScheduleQuery.getMaxScheduleTime()), ScheduleEn::getScheduleTime, crawlScheduleQuery.getMaxScheduleTime())
                .last(StringUtils.isNotEmpty(crawlScheduleQuery.getLast()), crawlScheduleQuery.getLast())
                ;
    }

    public void addFinishCount(Long id, Long cnt) {
        baseMapper.addFinish(id, cnt);
    }

    public void setTotalCnt(Long id, Long cnt) {
        ScheduleEn en = new ScheduleEn();
        en.setId(id);
        en.setTotalCount(cnt);
        updateById(en);
    }

}
