package xyz.zix.spider.repo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LabelVO implements Serializable {

    private String value;

    private String label;

}
