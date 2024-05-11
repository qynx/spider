package xyz.zix.spider.common.utils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static List<Integer> range2List(int start, int end) {
        List<Integer> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            res.add(i);
        }
        return res;
    }
}
