package xyz.zix.spider.control.adapter;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.ScheduleVO;
import xyz.zix.spider.repo.domain.ScheduleEn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ScheduleVoAdapter extends VoAdapter<ScheduleEn, ScheduleVO> {


    default ScheduleVO toV(ScheduleEn scheduleEn) {
        ScheduleVO res = i(scheduleEn);
        if (Objects.nonNull(res)) {
            res.setScheduleTimeStr(DateUtil.format(res.getScheduleTime(), "yy/MM/dd HH:mm:ss"));
        }
        if (Objects.nonNull(res.getStartTime()) && Objects.nonNull(res.getEndTime())) {
            long consumeTime = res.getEndTime().getTime() - res.getStartTime().getTime();
            res.setConsumeTimeStr(DateUtil.formatBetween(consumeTime, BetweenFormatter.Level.SECOND));
        }
        if (Objects.nonNull(res.getTotalCount()) && Objects.nonNull(res.getFinishCount())) {
            if (res.getTotalCount() > 0) {
                double r = res.getFinishCount() / (res.getTotalCount() + 0.0D);
                res.setProcessRatio(BigDecimal.valueOf(r).setScale(2, RoundingMode.HALF_UP).toString());
            }
        }
        return res;
    }

    ScheduleVO i(ScheduleEn en);
}
