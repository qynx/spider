package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import xyz.zix.spider.repo.domain.WorkCheckInEn;
import xyz.zix.spider.repo.mapper.WorkCheckInMapper;
import xyz.zix.spider.repo.query.WorkCheckInQuery;

import java.util.Objects;

@Repository
public class WorkCheckInSqlService extends BaseSqlService<WorkCheckInMapper, WorkCheckInEn, WorkCheckInQuery> {

    @Override
    LambdaQueryWrapper<WorkCheckInEn> toWrapper(WorkCheckInQuery workCheckInQuery) {
        return new LambdaQueryWrapper<WorkCheckInEn>()
                .eq(Objects.nonNull(workCheckInQuery.getDate()), WorkCheckInEn::getDate, workCheckInQuery.getDate())
                .ge(Objects.nonNull(workCheckInQuery.getDateStart()), WorkCheckInEn::getDate, workCheckInQuery.getDateStart())
                .le(Objects.nonNull(workCheckInQuery.getDateEnd()), WorkCheckInEn::getDate, workCheckInQuery.getDateEnd())
                ;
    }
}
