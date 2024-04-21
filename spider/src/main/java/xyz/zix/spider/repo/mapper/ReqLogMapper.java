package xyz.zix.spider.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.zix.spider.repo.domain.ReqLogEn;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReqLogMapper extends BaseMapper<ReqLogEn> {


    @Select("select req_time_min, count(1) as cnt from req_log where req_time >=#{startTime} and req_time <=#{endTime} group by req_time_min")
    List<Map<String, Object>> selectQpm(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
