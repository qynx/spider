package xyz.zix.spider.repo.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class PoetryQuery implements Serializable {

    private String title;

    private Long current;

    private Long pageSize;

}
