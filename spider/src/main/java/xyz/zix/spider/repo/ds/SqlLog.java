package xyz.zix.spider.repo.ds;

import org.apache.ibatis.logging.Log;

public class SqlLog implements Log {

    private final String source;


    public SqlLog(String source) {
        this.source = source;
    }

    @Override
    public boolean isDebugEnabled() {
        String upSource = source.toUpperCase();
        if (upSource.contains("SCHEDULE") || upSource.contains("CRAWL") || upSource.contains("SPRING")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        System.out.println(s);
    }

    @Override
    public void error(String s) {
        System.out.println(s);

    }

    @Override
    public void debug(String s) {
        System.out.println(s);
    }

    @Override
    public void trace(String s) {
        System.out.println(s);
    }

    @Override
    public void warn(String s) {

    }
}
