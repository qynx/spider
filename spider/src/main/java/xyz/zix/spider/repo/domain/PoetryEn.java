package xyz.zix.spider.repo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import xyz.zix.spider.repo.enums.PoetryTypeEnum;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "`poetry`")
public class PoetryEn implements Serializable {

    @TableId(type = IdType.AUTO)
    public Long id;

    public String title;

    public String author;

    private String period;

    public PoetryTypeEnum type;

    public String content;

    public Date createdAt;

    public Date updatedAt;
}