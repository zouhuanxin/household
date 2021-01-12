package com.zhx.household.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 时间减法
     */
    public static long subtractionTime(Date date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            long currentTime = System.currentTimeMillis();
            long createTime = date2.getTime();
            diff = (currentTime - createTime) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }


}
