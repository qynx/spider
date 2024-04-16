package xyz.zix.spider.repo.service.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import xyz.zix.spider.repo.vo.PageVO;

import java.util.List;

public abstract class BaseSqlService<M extends BaseMapper<T>, T, Q> extends ServiceImpl<M, T> {

    abstract LambdaQueryWrapper<T> toWrapper(Q q);

    public List<T> list(Q q) {
        LambdaQueryWrapper<T> wrapper = toWrapper(q);
        return list(wrapper);
    }

    public Long count(Q q) {
        LambdaQueryWrapper<T> wrapper = toWrapper(q);
        return count(wrapper);
    }

    public PageVO<T> page(Q q, Long current, Long pageSize) {
        return page(q, current, pageSize, null);
    }

    public PageVO<T> page(Q q, Long current, Long pageSize, String last) {
        LambdaQueryWrapper<T> wrapper = toWrapper(q);
        if (StringUtils.isNotEmpty(last)) {
            wrapper.last(last);
        }
        Page<T> r = new Page<T>(current, pageSize);
        page(r, wrapper);
        PageVO<T> res =  new PageVO<T>();
        res.setTotal(r.getTotal());
        res.setCurrent(r.getCurrent());
        res.setPageSize(r.getSize());
        res.setRecords(r.getRecords());
        return res;
    }
}
