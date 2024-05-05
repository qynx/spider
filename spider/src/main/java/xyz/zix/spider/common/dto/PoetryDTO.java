package xyz.zix.spider.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data

public class PoetryDTO implements Serializable {

    private String link;

    private String author;

    private String title;

    private String content;

}
