package com.parkingwang.android;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 董棉生(dongmiansheng @ parkingwang.com)
 * @since 18-10-25
 */

public class SwTime {

    private static final String HHMM = "HH:mm";
    private static final String MMDD = "MM-dd";
    private static final String MMDDHHMM = "MM-dd HH:mm";
    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    private static final String YYYYMMDDHHMM_DIAGONAL = "yyyy/MM/dd HH:mm";


    /**
     * 秒与毫秒的倍数
     */
    public static final long SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final long MIN = SEC * 60;
    /**
     * 时与毫秒的倍数
     */
    public static final long HOUR = MIN * 60;
    /**
     * 天与毫秒的倍数
     */
    public static final long DAY = HOUR * 24;


    public static final SafeSimpleDateFormat FORMAT_HHMM = new SafeSimpleDateFormat(HHMM);

    public static final SafeSimpleDateFormat FORMAT_MMDD = new SafeSimpleDateFormat(MMDD);

    public static final SafeSimpleDateFormat FORMAT_MMDDHHMM = new SafeSimpleDateFormat(MMDDHHMM);

    public static final SafeSimpleDateFormat FORMAT_YYYYMMDD = new SafeSimpleDateFormat(YYYYMMDD);

    public static final SafeSimpleDateFormat FORMAT_YYYYMMDDHHMM = new SafeSimpleDateFormat(YYYYMMDDHHMM);

    public static final SafeSimpleDateFormat FORMAT_YYYYMMDDHHMM_DIAGONAL =
            new SafeSimpleDateFormat(YYYYMMDDHHMM_DIAGONAL);

    private SwTime() {
    }

    /**
     * 获取当前时间字符串
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurrentTime(SafeSimpleDateFormat format) {
        return format.format(new Date());
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param seconds 秒时间戳
     * @param format  时间格式
     * @return 时间字符串
     */
    public static String seconds2String(long seconds, SafeSimpleDateFormat format) {
        return format.format(new Date(seconds * SEC));
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliSeconds2String(long milliseconds, SafeSimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }


    /**
     * 将时间字符串转为时间戳
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳   -1 不存在
     */
    public static long string2Milliseconds(String time, SafeSimpleDateFormat format) {
        Date date = format.parse(time);
        return date == null ? -1L : date.getTime();
    }

    /**
     * 将时间字符串转为时间戳
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 秒时间戳   -1 不存在
     */
    public static long string2Seconds(String time, SafeSimpleDateFormat format) {
        Date date = format.parse(time);
        return date == null ? -1L : date.getTime() / SEC;
    }


    public static Calendar setCalendar(int calendarField, int value) {
        Calendar calendar = getCalendar();
        calendar.set(calendarField, value);
        return calendar;
    }

    public static Calendar getCalendar() {
        return SafeCalendar.INSTANCE.initialValue();
    }


    /**
     * 判断当前日期是星期几
     *
     * @param strDate 要判断的时间
     * @return dayForWeek 判断结果   -1 不存在 ,从1到7开始分别表示从星期天到星期六
     * <ul>
     * <li>{@link Calendar#SUNDAY}<li/>
     * <li>{@link Calendar#MONDAY}<li/>
     * <li>{@link Calendar#TUESDAY}<li/>
     * <li>{@link Calendar#WEDNESDAY}<li/>
     * <li>{@link Calendar#THURSDAY}<li/>
     * <li>{@link Calendar#FRIDAY}<li/>
     * <li>{@link Calendar#SATURDAY}<li/>
     * <ul/>
     */
    public static int stringForWeek(String strDate, SafeSimpleDateFormat format) {
        Date date = format.parse(strDate);
        if (date == null) {
            return -1;
        }
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param milliSecond 要判断的毫秒时间
     * @return dayForWeek 判断结果   -1 不存在 ,从1到7开始分别表示从星期天到星期六
     * <ul>
     * <li>{@link Calendar#SUNDAY}<li/>
     * <li>{@link Calendar#MONDAY}<li/>
     * <li>{@link Calendar#TUESDAY}<li/>
     * <li>{@link Calendar#WEDNESDAY}<li/>
     * <li>{@link Calendar#THURSDAY}<li/>
     * <li>{@link Calendar#FRIDAY}<li/>
     * <li>{@link Calendar#SATURDAY}<li/>
     * <ul/>
     */
    public static int milliSecondForWeek(long milliSecond) {
        Calendar c = SafeCalendar.INSTANCE.initialValue();
        c.setTime(new Date(milliSecond));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 是否是当天
     *
     * @param time
     * @param format
     * @return
     */
    public static boolean isToday(String time, SafeSimpleDateFormat format) {
        long wee = (System.currentTimeMillis() / DAY) * DAY; //当前凌晨
        long milli = string2Milliseconds(time, format);
        return milli >= wee && milli < wee + DAY;
    }

    /**
     * 是否是当天
     *
     * @param milliSecond
     * @return
     */
    public static boolean isToday(long milliSecond) {
        long wee = (System.currentTimeMillis() / DAY) * DAY; //当前凌晨
        return milliSecond >= wee && milliSecond < wee + DAY;
    }

    static class SafeCalendar extends ThreadLocal<Calendar> {

        static final SafeCalendar INSTANCE = new SafeCalendar();

        private SafeCalendar() {
        }

        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    }


}
