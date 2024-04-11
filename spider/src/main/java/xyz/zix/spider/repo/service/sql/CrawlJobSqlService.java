package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import xyz.zix.spider.repo.domain.CrawlJobEn;
import xyz.zix.spider.repo.mapper.CrawlJobEnMapper;
import xyz.zix.spider.repo.query.CrawlJobQuery;

import java.util.Objects;

@Repository
public class CrawlJobSqlService extends BaseSqlService<CrawlJobEnMapper, CrawlJobEn, CrawlJobQuery> {

    @Override
    LambdaQueryWrapper<CrawlJobEn> toWrapper(CrawlJobQuery crawlJobQuery) {
        LambdaQueryWrapper<CrawlJobEn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(crawlJobQuery.getStatus()), CrawlJobEn::getStatus, crawlJobQuery.getStatus());
        wrapper.le(Objects.nonNull(crawlJobQuery.getNextTriggerTimeMax()), CrawlJobEn::getNextTriggerTime, crawlJobQuery.getNextTriggerTimeMax());
        wrapper.ge(Objects.nonNull(crawlJobQuery.getNextTriggerTimeMin()), CrawlJobEn::getNextTriggerTime, crawlJobQuery.getNextTriggerTimeMin());
        return wrapper;
    }
}
