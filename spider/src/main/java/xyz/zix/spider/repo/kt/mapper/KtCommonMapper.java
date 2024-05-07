package xyz.zix.spider.repo.kt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.zix.spider.repo.domain.PoetryEn;

import java.util.List;
import java.util.Map;

public interface KtCommonMapper extends BaseMapper<PoetryEn> {

    @Select("${sql}")
    List<Map<String, Object>> select(@Param("sql") String sql);


}
