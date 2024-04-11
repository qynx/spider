package xyz.zix.spider.utils;


import org.springframework.scheduling.support.CronExpression;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZixCronUtils {

    public static long nextExecTime(String cron) {
        return nextExecTime(cron, ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai")));
    }

    public static long nextExecTime(String cron, long base) {
        return nextExecTime(cron, ZonedDateTime.ofInstant(Instant.ofEpochMilli(base), ZoneId.of("Asia/Shanghai")));
    }

    public static long nextExecTime(String cron, ZonedDateTime base) {
        return Date.from(CronExpression.parse(cron).next(base).toInstant()).getTime();
    }

    public static void main(String[] args) {
        String cron = "*/1 * * * * ?";
        long curr = System.currentTimeMillis();
        long next1 = nextExecTime(cron, curr);
        long next2 = nextExecTime(cron, next1);
        System.out.println(curr +  " " + next1 + " " + next2);
    }
}
