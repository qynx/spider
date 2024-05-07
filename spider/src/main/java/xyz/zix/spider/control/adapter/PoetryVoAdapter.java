package xyz.zix.spider.control.adapter;

import cn.hutool.core.date.DateUtil;
import org.mapstruct.Mapper;
import xyz.zix.spider.control.vo.PoetryVO;
import xyz.zix.spider.repo.domain.PoetryEn;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface PoetryVoAdapter extends VoAdapter<PoetryEn, PoetryVO> {

    default PoetryVO toV(PoetryEn poetryEn) {
        PoetryVO poetryVO = iToV(poetryEn);
        if (Objects.nonNull(poetryVO)) {
            if (Objects.nonNull(poetryVO.getCreatedAt())) {
                poetryVO.setCreatedAtStr(
                        DateUtil.format(poetryVO.getCreatedAt(), "yyyy/MM/dd HH:mm")
                );
            }
        }
        return poetryVO;
    }
}
