package xyz.zix.spider.control.adapter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.CrawlJobVO;
import xyz.zix.spider.repo.domain.CrawlJobEn;

import java.util.Date;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface CrawlJobAdapter extends VoAdapter<CrawlJobEn, CrawlJobVO> {

    default CrawlJobVO toV(CrawlJobEn en) {
        CrawlJobVO res = iAdapt(en);
        if (Objects.nonNull(res)) {
            if (Objects.nonNull(res.getNextTriggerTime()) && res.getNextTriggerTime() > 0) {
                res.setNextTriggerTimeStr(DateUtil.format(new Date(res.getNextTriggerTime()), "yy/MM/dd HH:mm:ss"));
            } else {
                res.setNextTriggerTimeStr("2099?");
            }
        }
        return res;
    }

    CrawlJobVO iAdapt(CrawlJobEn en);
}
