package xyz.zix.spider.repo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageVO<T> implements Serializable {

    private Long total;

    private Long current;

    private Long pageSize;

    private List<T> records;

    public <R> PageVO<R> adapt(Function<T, R> function) {
        PageVO<R> res = new PageVO<>();
        res.setTotal(getTotal());
        res.setCurrent(getCurrent());
        res.setPageSize(getPageSize());
        res.setRecords(records.stream().map(function).collect(Collectors.toList()));
        return res;
    }


}
