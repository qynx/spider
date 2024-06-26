package xyz.zix.spider.control.route;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import xyz.zix.spider.common.consts.SqlConst;
import xyz.zix.spider.control.service.BaseControlService;
import xyz.zix.spider.control.vo.GraphItemVO;
import xyz.zix.spider.control.vo.GraphRsp;
import xyz.zix.spider.control.vo.GraphSeriesVO;
import xyz.zix.spider.control.vo.PoetryGraphReq;
import xyz.zix.spider.control.vo.PoetryVO;
import xyz.zix.spider.control.vo.graph.GraphLegendVO;
import xyz.zix.spider.control.vo.graph.GraphToolTipVO;
import xyz.zix.spider.crawler.GuShiWenCrawler;
import xyz.zix.spider.exceptions.CommonException;
import xyz.zix.spider.repo.domain.PoetryEn;
import xyz.zix.spider.repo.enums.PoetryTypeEnum;
import xyz.zix.spider.repo.query.PoetryQuery;
import xyz.zix.spider.repo.service.sql.PoetrySqlService;
import xyz.zix.spider.repo.vo.ZixRsp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/zix/poetry")
public class PoetryController  extends BaseControlService<PoetryEn, PoetryQuery, PoetryVO, PoetrySqlService> {

    @Resource
    private GuShiWenCrawler guShiWenCrawler;


    protected void prepareSave(PoetryVO last, PoetryVO curr) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(curr.getTitle())) {
            curr.setTitle(org.apache.commons.lang3.StringUtils.trim(curr.getTitle()));
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(curr.getAuthor())) {
            curr.setAuthor(org.apache.commons.lang3.StringUtils.trim(curr.getAuthor()));
        }
        if (null == curr.getType()) {
            curr.setType(PoetryTypeEnum.SHI);
        }
        curr.setContent(StringUtils.trim(curr.content));

        if (null == last) {
            List<PoetryEn> exist =
            service.list(new PoetryQuery().setTitle(curr.getTitle()).setAuthor(curr.getAuthor()));
            if (CollectionUtil.isNotEmpty(exist)) {
                throw new CommonException("该诗已录入 id: " + exist.get(0).getId());
            }
        }
    }

    @PostMapping("/graph")
    @ResponseBody
    public Object graph(@RequestBody PoetryGraphReq req) {
        GraphRsp graphRsp = new GraphRsp();
        graphRsp.setSeries(Arrays.asList(new GraphSeriesVO()));
        GraphSeriesVO graphSeriesVO = graphRsp.getSeries().get(0);
        graphSeriesVO.setType("pie");
        graphSeriesVO.setName("tt");
        List<Map<String, Object>> list =
        ktCommonMapper.select(SqlConst.Companion.getPOETRY_GROUP_BY_SQL());
        graphSeriesVO.setData(new ArrayList<>());
        for (Map<String, Object> item : list) {
            graphSeriesVO.getData().add(
                    new GraphItemVO().setName(MapUtil.getStr(item, "author")).setValue(MapUtil.getLong(item, "count"))
            );
        }
        graphRsp.setLegend(new GraphLegendVO());
        graphRsp.getLegend().setOrient("vertical");
        graphRsp.setTooltip(new GraphToolTipVO());
        graphRsp.getTooltip().setFormatter("{c}/{d}%s");
        return ZixRsp.success(graphRsp);
    }

    @PostMapping("/sync_blog")
    @ResponseBody
    public Object syncBlog(@RequestBody PoetryVO poetryVO) {
        return ZixRsp.ofFail(400, "暂未支持");
    }

    @GetMapping("/get")
    @ResponseBody
    public Object get2(@RequestParam("id") Long id) {
        return ZixRsp.success(get(id));
    }

    @PostMapping("/page")
    @ResponseBody
    public Object page2(@RequestBody PoetryQuery query) {
        return ZixRsp.success(page(query, query.getCurrent(), query.getPageSize(), " order by id desc "));
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
