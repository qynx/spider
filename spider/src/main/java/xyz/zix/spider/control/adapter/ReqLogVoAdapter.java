package xyz.zix.spider.control.adapter;

import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.ReqLogVO;
import xyz.zix.spider.repo.domain.ReqLogEn;

@Mapper(componentModel = "spring")
public interface ReqLogVoAdapter extends VoAdapter<ReqLogEn, ReqLogVO> {
}
