package com.netease.welkin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期处理器
 * 
 * @author zxwu
 */
public final class DateUtil {
    private DateUtil() {
    }

    // 标准日期时间格式
    /** yyyy-MM-dd HH:mm:ss */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /** yyyyMMdd HH*/
    public static final String FORMAT_DATEHOUR = "yyyyMMdd-HH";
    /** yyyy-MM-dd */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /** yyyy-MM */
    public static final String FORMAT_MONTH = "yyyy-MM";
    /** HH:mm:ss */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /** HH:mm */
    public static final String FORMAT_MINUTE = "HH:mm";
    /** mm:ss */
    public static final String FORMAT_SECOND = "mm:ss";

    // 无符号格式
    /** yyyyMMddHHmmss */
    public static final String FORMAT_DATETIME_UNSIGNED = "yyyyMMddHHmmss";
    /** yyyyMMdd */
    public static final String FORMAT_DATE_UNSIGNED = "yyyyMMdd";
    /** yyyyMM */
    public static final String FORMAT_MONTH_UNSIGNED = "yyyyMM";
    /** HHmmss */
    public static final String FORMAT_TIME_UNSIGNED = "HHmmss";
    /** HHmm */
    public static final String FORMAT_MINUTE_UNSIGNED = "HHmm";
    /** mmss */
    public static final String FORMAT_SECOND_UNSIGNED = "mmss";

    /**
     * 按指定格式格式化时期时间
     * 
     * @param date date
     * @param format format
     * @return String
     */
    public static String format(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    /**
     * 按指定格式格式化时期时间
     * 
     * @param date date
     * @param format format
     * @return String
     */
    public static String format(String date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(parse(date, FORMAT_DATETIME));
    }

    /**
     * 按指定格式解析字符串，将字符串转为日期时间格式
     * 
     * @param str str
     * @param format format
     * @return Date
     */
    public static Date parse(String str, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指定月之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }
    
    /**
     * 
     * @param hour 日期 为null时表示当前
     * @param hour 相加(相减)的小时数
     */
    public static Date nextHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }
    
    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指定月之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * @return 今天0:0:0的Calender
     */
    public static Calendar startOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c;
    }

    /**
     * @return 今天23:59:59的Calender
     */
    public static Calendar endOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c;
    }
    
    public static long convert2long(String date, String format) {
    	  try {
    	   if (StringUtils.isNotBlank(date)) {
    	    SimpleDateFormat sf = new SimpleDateFormat(format);
    	    return sf.parse(date).getTime();
    	   }
    	  } catch (ParseException e) {
    	   e.printStackTrace();
    	  }
    	  return 0;
    	 }
}
