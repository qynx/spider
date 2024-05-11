package xyz.zix.spider.repo.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class WorkCheckInQuery implements Serializable {

    private String date;

    private String dateStart;

    private String dateEnd;
}
