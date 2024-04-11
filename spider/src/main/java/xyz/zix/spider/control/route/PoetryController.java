package xyz.zix.spider.control.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.PoetryVO;
import xyz.zix.spider.repo.domain.PoetryEn;
import xyz.zix.spider.repo.enums.PoetryTypeEnum;
import xyz.zix.spider.repo.query.PoetryQuery;
import xyz.zix.spider.repo.service.sql.PoetrySqlService;
import xyz.zix.spider.repo.vo.ZixRsp;

@RestController
@RequestMapping("/api/zix/poetry")
public class PoetryController  extends BaseControlService<PoetryEn, PoetryQuery, PoetryVO, PoetrySqlService> {

    protected void prepareSave(PoetryVO last, PoetryVO curr) {
        if (null == curr.getType()) {
            curr.setType(PoetryTypeEnum.SHI);
        }
        curr.setContent(StringUtils.trim(curr.content));
    }

    @GetMapping("/get")
    @ResponseBody
    public Object get2(@RequestParam("id") Long id) {
        return ZixRsp.success(get(id));
    }

    @PostMapping("/page")
    @ResponseBody
    public Object page2(@RequestBody PoetryQuery query) {
        return ZixRsp.success(page(query, query.getCurrent(), query.getPageSize()));
    }

    @PostMapping("/save")
    @ResponseBody
    public Object save2(@RequestBody PoetryVO poetryVO) {
        return ZixRsp.success(save(poetryVO));
    }

    @PostMapping("/del")
    @ResponseBody
    public Object del2(@RequestParam("id") Long id) {
        return ZixRsp.success(del(id));
    }



}
