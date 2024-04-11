package xyz.zix.spider.repo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChapterVO implements Serializable {


    private String fullUrl;
    private String title;
    private Integer index;

    private String content;
}
