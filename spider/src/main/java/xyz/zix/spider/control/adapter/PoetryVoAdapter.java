package xyz.zix.spider.control.adapter;

import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.PoetryVO;
import xyz.zix.spider.repo.domain.PoetryEn;

@Mapper(componentModel = "spring")
public interface PoetryVoAdapter extends VoAdapter<PoetryEn, PoetryVO> {
}
