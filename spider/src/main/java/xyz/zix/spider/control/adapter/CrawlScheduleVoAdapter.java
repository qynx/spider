package xyz.zix.spider.control.adapter;

import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.ScheduleVO;
import xyz.zix.spider.repo.domain.ScheduleEn;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface CrawlScheduleVoAdapter extends VoAdapter<ScheduleEn, ScheduleVO> {


    default ScheduleVO toV(ScheduleEn scheduleEn) {
        ScheduleVO res = i(scheduleEn);
        if (Objects.nonNull(res)) {
            res.setScheduleTimeStr(DateUtil.format(res.getScheduleTime(), "yy/MM/dd HH:mm:ss"));
        }
        return res;
    }

    ScheduleVO i(ScheduleEn en);
}
