package com.joy.baselib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author ymx
 */
public class DateUtils {

    public static final SimpleDateFormat FULL_DATA1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    public static final SimpleDateFormat FULL_DATA2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat SIMLE_DATA1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMLE_DATA2 = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat SIMLE_DATA3 = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat SPE_DATA = new SimpleDateFormat("Gyyyy年MM月dd日");
    public static final SimpleDateFormat HOUR_MINUTE_SECOND = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat UPDATE_TIME = new SimpleDateFormat("HH:mm");

    /**
     * 将Date转为字符串日期
     *
     * @param df
     * @param date
     * @return
     */
    public static String getStringDate(DateFormat df, Date date) {
        return df.format(date);
    }

    /**
     * 将字符串日期转为Date
     *
     * @param df
     * @param date
     * @return
     */
    public static Date getDate(DateFormat df, String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将long时间转换为字符串日期
     *
     * @param longDate
     * @param df
     * @return
     */
    public static String parseLongToString(long longDate, DateFormat df) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longDate);
        return df.format(calendar.getTime());
    }

    public static StringBuilder getUpdateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        Date now = calendar.getTime();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long year = day / 365;
        long month = day / 30;
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder sb = new StringBuilder();

        if (year > 0) {
            sb.append(year + "年前");
            return sb;
        }
        if (month > 0) {
            sb.append(month + "个月前");
            return sb;
        }
        if (day > 0) {
            sb.append(day + "天前");
            return sb;
        }
        if (hour > 0) {
            String str = getStringDate(UPDATE_TIME, date);
            if (calendar2.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                sb.append("今天" + str);
                return sb;
            } else {
                sb.append("昨天" + str);
                return sb;
            }
        }
        return null;
    }

}
