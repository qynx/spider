package xyz.zix.spider.control.vo;

import lombok.Data;
import xyz.zix.spider.repo.enums.PoetryTypeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
public class PoetryVO implements Serializable {

    private Long id;

    public String title;

    public String author;

    private String period;

    public PoetryTypeEnum type;

    public String content;

    public Date createdAt;

    public Date updatedAt;
}
