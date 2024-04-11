package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import xyz.zix.spider.repo.domain.ReqLogEn;
import xyz.zix.spider.repo.mapper.ReqLogMapper;
import xyz.zix.spider.repo.query.ReqLogQuery;

@Repository
public class ReqLogSqlService extends BaseSqlService<ReqLogMapper, ReqLogEn, ReqLogQuery> {

    @Override
    LambdaQueryWrapper<ReqLogEn> toWrapper(ReqLogQuery reqLogQuery) {
        LambdaQueryWrapper<ReqLogEn> wrapper = new LambdaQueryWrapper<ReqLogEn>();
        return wrapper;
    }
}
