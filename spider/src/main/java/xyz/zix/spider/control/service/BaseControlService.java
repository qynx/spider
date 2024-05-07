package xyz.zix.spider.control.service;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import xyz.zix.spider.control.adapter.VoAdapter;
import xyz.zix.spider.exceptions.RsNotFoundException;
import xyz.zix.spider.repo.kt.mapper.KtCommonMapper;
import xyz.zix.spider.repo.service.sql.BaseSqlService;
import xyz.zix.spider.repo.vo.PageVO;

import java.util.Objects;

public abstract class BaseControlService<D, Q, V, S extends BaseSqlService<? extends BaseMapper<D>, D, Q>> {


    @Autowired
    protected VoAdapter<D, V> adapter;
    @Autowired
    protected S service;
    @Autowired
    protected KtCommonMapper ktCommonMapper;

    public PageVO<V> page(Q q, Long current, Long pageSize, String last) {
        PageVO<D> page = service.page(q, current, pageSize, last);
        return page.adapt(adapter::toV);
    }

    public PageVO<V> page(Q q, Long current, Long pageSize) {
        PageVO<D> page = service.page(q, current, pageSize);
        return page.adapt(adapter::toV);
    }

    protected void prepareSave(V last, V curr) {

    }

    public V get(Long id) {
        V r = adapter.toV(service.getById(id));
        if (null == r) {
            throw new RsNotFoundException("");
        }
        return r;
    }

    public V save(V v) {
        Long id = getId(v);
        if (null == id) {
            return create(v);
        } else {
            return update(v);
        }
    }

    public V create(V v) {
        prepareSave(null, v);
        D d = adapter.toD(v);
        service.save(d);
        V saved = adapter.toV(d);
        return get(getId(saved));
    }

    public V update(V v) {
        Long id = getId(v);
        Assert.isTrue(Objects.nonNull(id), "更新时id不能为空");
        V last = get(id);
        prepareSave(last, v);
        D d = adapter.toD(v);
        service.updateById(d);
        return adapter.toV(service.getById(id));
    }

    public V del(Long id) {
        get(id);
        service.removeById(id);
        return null;
    }

    protected Long getId(V v) {
        return (Long) ReflectUtil.getFieldValue(v, "id");
    }
}
