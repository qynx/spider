package xyz.zix.spider.common.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import xyz.zix.spider.common.dto.WxCoprConfigDTO;
import xyz.zix.spider.common.utils.ConfigUtils;

@Component
@Getter
public class MsgConfig {

    private WxCoprConfigDTO wxCorpConfig = ConfigUtils.loadConfig(WxCoprConfigDTO.class);

}
