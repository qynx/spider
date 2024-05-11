package xyz.zix.spider.control.adapter;

import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.WorkCheckInVO;
import xyz.zix.spider.repo.domain.WorkCheckInEn;

@Mapper(componentModel = "spring")
public interface WorkCheckInVoAdapter extends VoAdapter<WorkCheckInEn, WorkCheckInVO> {

}
