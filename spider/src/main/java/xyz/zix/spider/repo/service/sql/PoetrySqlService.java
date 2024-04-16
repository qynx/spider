package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import xyz.zix.spider.repo.domain.PoetryEn;
import xyz.zix.spider.repo.kt.mapper.PoetryEnMapper;
import xyz.zix.spider.repo.query.PoetryQuery;

@Repository
public class PoetrySqlService extends BaseSqlService<PoetryEnMapper, PoetryEn, PoetryQuery> {

    @Override
    LambdaQueryWrapper<PoetryEn> toWrapper(PoetryQuery query) {
        return new LambdaQueryWrapper<PoetryEn>()
                .eq(StringUtils.isNotEmpty(query.getAuthor()), PoetryEn::getAuthor, query.getAuthor())
                .eq(StringUtils.isNotEmpty(query.getTitle()), PoetryEn::getTitle, query.getTitle())
                .like(StringUtils.isNotEmpty(query.getTitleLike()), PoetryEn::getTitle, query.getTitleLike())
                ;
    }
}
