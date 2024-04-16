package xyz.zix.spider.repo.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PoetryQuery implements Serializable {

    private String title;

    private String titleLike;

    private String author;

    private Long current;

    private Long pageSize;

}
