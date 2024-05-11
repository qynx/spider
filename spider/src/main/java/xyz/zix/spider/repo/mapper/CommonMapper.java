package xyz.zix.spider.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.zix.spider.repo.domain.WorkCheckInEn;

import java.util.List;
import java.util.Map;

import static xyz.zix.spider.common.consts.SqlConstKt.INSERT_WORK_CHECK_IN_ON_DP;

public interface CommonMapper extends BaseMapper<WorkCheckInEn> {

    @Select("${sql}")
    void select(@Param("sql") String sql);

    @Insert(INSERT_WORK_CHECK_IN_ON_DP)
    void insertWorkCheckIn(@Param("li") List<WorkCheckInEn> list);

    List<Map<String, Object>> dayCheckInDuration();

}
