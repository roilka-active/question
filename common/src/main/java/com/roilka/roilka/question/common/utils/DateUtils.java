package com.roilka.roilka.question.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 时间工具类
 *
 * @author zhanghui
 */
@Slf4j
public class DateUtils {
    public DateUtils() {
    }

    private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    private static final String YYYYMMDD = "yyyy-MM-dd";

    /**
     * 比较时间
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int compareTime(Date time1, Date time2) {
        return time1.compareTo(time2);
    }

    /**
     * 比较时间是否在指定的范围内
     *
     * @param time1 开始范围
     * @param time2 结束范围
     * @param time3 待比较的时间
     * @return
     */
    public static boolean validateTimeScope(Date time1, Date time2, Date time3) {
        boolean flag = false;
        if (time3.compareTo(time1) >= 1 && time2.compareTo(time3) >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 时间运算返回时间戳
     *
     * @param time1 结束时间
     * @param time2 比较时间
     * @return
     */
    public static Long timeOperation(Date time1, Date time2) {
        return time1.getTime() - time2.getTime();
    }

    /**
     * 时间运算(默认返回秒)
     *
     * @param time1  结束时间
     * @param time2  比较时间
     * @param number 指定的时间
     * @return
     */
    public static Long timeOperation(Date time1, Date time2, Integer number) {
        if (number == null) {
            number = 1000;
        }
        return timeOperation(time1, time2) / number;
    }

    /**
     * 计算同一年内，两个日期相差几个小时
     *
     * @param time1
     * @param time2
     * @return
     */
    public static Integer timeOperationHour(Date time1, Date time2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(time1);
        calendar2.setTime(time2);

        return (calendar2.get(Calendar.DAY_OF_YEAR) - calendar1.get(Calendar.DAY_OF_YEAR)) * 24 +
                calendar2.get(Calendar.HOUR_OF_DAY) - calendar1.get(calendar1.HOUR_OF_DAY);
    }

    /**
     * 得到几天后的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到当前年份几小时前的时间
     *
     * @param date
     * @return
     */
    public static Date getPreHour(Date date, int n) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        // 默认是当天
        int resultDay = now.get(Calendar.DAY_OF_YEAR);
        // 如果大于24小时,换算为天计数，折算后结果继续运算
        if (n > 24) {
            int day = n / 24;
            resultDay = now.get(Calendar.DAY_OF_YEAR) - day;
            n %= 24;
        }
        int resultHour = now.get(Calendar.HOUR_OF_DAY) - n;
        // 如果当天时间不够减，则向前一天推算
        if (resultHour < 0) {
            resultDay--;
            resultHour = now.getActualMaximum(Calendar.HOUR_OF_DAY) + resultHour + 1;
        }
        // 如果当年的天数不够计算，则向前一年推算
        if (resultDay < 0) {
            now.set(Calendar.YEAR, -1);
            resultDay = now.getActualMaximum(Calendar.DAY_OF_YEAR) + resultDay;
        }
        now.set(Calendar.DAY_OF_YEAR, resultDay);
        now.set(Calendar.HOUR_OF_DAY, resultHour);

        return now.getTime();
    }

    /**
     * 得到几小时后的时间
     *
     * @param date
     * @return
     */
    public static Date getAfterHour(Date date, int n) {

        Calendar now = Calendar.getInstance();
        if (n > (now.getActualMaximum(Calendar.YEAR) * 24)) {
            log.error("当前计算不支持跨越两年计算，date={}，n={}", date, n);
        }
        now.setTime(date);
        // 默认是当天
        int resultDay = now.get(Calendar.DAY_OF_YEAR);
        // 如果大于24小时,换算为天计数，折算后结果继续运算
        if (n > 24) {
            int day = n / 24;
            resultDay = now.get(Calendar.DAY_OF_YEAR) + day;
            n %= 24;
        }
        int resultHour = now.get(Calendar.HOUR_OF_DAY) + n;
        // 如果当天时间不够减，则向后一天推算
        if (resultHour > now.getActualMaximum(Calendar.HOUR_OF_DAY) + 1) {
            resultDay++;
            resultHour -= now.getActualMaximum(Calendar.HOUR_OF_DAY) + 1;
        }
        // 如果当年的天数不够计算，则向后一年推算
        if (resultDay > now.getActualMaximum(Calendar.DAY_OF_YEAR)) {
            now.set(Calendar.YEAR, 1);
            resultDay -= now.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        now.set(Calendar.DAY_OF_YEAR, resultDay);
        now.set(Calendar.HOUR_OF_DAY, resultHour);

        return now.getTime();
    }

    public static void main(String[] args) {
//        List<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        List<Integer> b = new ArrayList<>();
//        b.add(4);
//        b.add(2);
//        b.add(3);
//        a.removeAll(b);
//        System.out.println("a:"+a+"/r b:"+b);
//        b.removeAll(a);
//        System.out.println("a:"+a+"/r b:"+b);
//      List<Integer> re =   a.stream().filter(r -> r >2).collect(Collectors.toList());
//        System.out.println(re);
//        System.out.println(getAfterHour(new Date(), 20000));
        String t = "1601395200000";
        Date date = new Date(Long.valueOf(t));
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(t);
        System.out.println(format);
    }

    /**
     * @param time 时间戳
     * @return
     * @throws ParseException
     */
    public static String timeToString(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(time);
    }

    /**
     * 时间类型转换成字符串
     *
     * @param date
     * @return
     */
    public static String timeToString(Object date) {
        SimpleDateFormat simple = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return simple.format(date);
    }

    /**
     * 时间类型转换成字符串
     *
     * @param date
     * @return
     */
    public static String timeToStringOnlyDate(Object date) {
        SimpleDateFormat simple = new SimpleDateFormat(YYYYMMDD);
        return simple.format(date);
    }

    /**
     * 时间增加一年
     *
     * @param date
     * @return
     */
    public static Date addNYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        cal.add(Calendar.YEAR, n);//增加N年
        return cal.getTime();
    }

    /**
     * 时间戳转换成Date类型
     *
     * @param time 时间戳
     * @return
     * @throws ParseException
     */
    public static String stringFormatToDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
        return simpleDateFormat.format(date);
    }

    /**
     * Date 转换成 format 格式的字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToStringByFormat(Date date, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取某天零点毫秒数
     *
     * @param date 某天的时间
     * @return
     */
    public static Long getCurrentZeroTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        String res = sdf.format(date);
        Date date1 = new Date();
        try {
            date1 = sdf.parse(res);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.getTime();
    }

    /**
     * 获取某天零点毫秒数
     *
     * @param date 某天的时间
     * @return
     */
    public static Date getCurrentZeroDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        String res = sdf.format(date);
        Date date1 = new Date();
        try {
            date1 = sdf.parse(res);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 获取指定日期对象
     *
     * @param date 指定日期
     * @return
     */
    public static Date getDate(String date) {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }


    public static String getHHmm(Time time) {
        if (time == null) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

}
