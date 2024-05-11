package xyz.zix.spider.repo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@TableName(value = "`work_check_in`")
@Data
public class WorkCheckInEn {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer durationMinute;

    private String start;

    private String end;

    private String date;

    public Integer calDurationByStartEnd() {
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            return 0;
        }
        Integer start = calMinuteFromDayStart(getStart());
        Integer end = calMinuteFromDayStart(getEnd());
        // 说明跨天
        if (end < start) {
            end += 24 * 60;
        }
        return end - start;
    }

    public Integer calMinuteFromDayStart(String format) {
        String hour = StringUtils.split(format, ":")[0];
        String minute = StringUtils.split(format, ":")[1];
        return Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
    }

}
