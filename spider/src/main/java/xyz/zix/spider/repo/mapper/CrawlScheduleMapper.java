package xyz.zix.spider.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import xyz.zix.spider.repo.domain.ScheduleEn;

public interface CrawlScheduleMapper extends BaseMapper<ScheduleEn> {

    @Update("update `schedule` set finish_count = finish_count + #{cnt} where id = #{id}")
    void addFinish(@Param("id") Long id, @Param("cnt") Long cnt);

}
