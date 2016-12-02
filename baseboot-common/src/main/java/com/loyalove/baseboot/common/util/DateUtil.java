package com.loyalove.baseboot.common.util;

import com.google.common.collect.Maps;
import com.loyalove.baseboot.common.enums.WeekEnum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

/**
 * 日期时间工具类
 *
 * @author zhoubo@yiji.com
 * @version 1.0
 */
public class DateUtil {

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日 yyyy-MM-dd
     */
    public static final String dtSimple = "yyyy-MM-dd";

    /**
     * 年月 yyyy-MM
     */
    public static final String dtSimpleYm = "yyyy-MM";

    /**
     * 年月日 yyyy/MM/dd
     */
    public static final String dtSimpleSlash = "yyyy/MM/dd";

    /**
     * 年月日(中文) yyyy年MM月dd日
     */
    public static final String dtSimpleChinese = "yyyy年MM月dd日";

    public static final String week = "EEEE";
    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String dtShort = "yyyyMMdd";

    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String dtLong = "yyyyMMddHHmmss";

    /**
     * 时分秒 HH:mm:ss
     */
    public static final String hmsFormat = "HH:mm:ss";

    /**
     * 时分秒 HHmmss
     */
    public static final String hmsFormat1 = "HHmmss";

    /**
     * 年-月-日 小时:分钟 yyyy-MM-dd HH:mm
     */
    public static final String simpleFormat = "yyyy-MM-dd HH:mm";


    /**
     * 日期格式化工具类
     */
    /**
     * DateFormat的缓存容器 <br>
     * 减少初始化DateFormat的耗时，增加工具类性能
     */
    protected static final ThreadLocal<ConcurrentMap<String, DateFormat>> formaterCache = new ThreadLocal<ConcurrentMap<String, DateFormat>>() {
        @Override
        protected ConcurrentMap<String, DateFormat> initialValue() {
            return Maps.newConcurrentMap();
        }
    };


    /**
     * 获取格式
     *
     * @param format
     * @return
     */
    public static final DateFormat getFormat(String format) {
        DateFormat dateFormat = formaterCache.get().get(format);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            formaterCache.get().put(format, dateFormat);
        }
        return dateFormat;
    }

    /**
     * 格式化传入日期
     *
     * @param date    日期
     * @param formate 格式
     * @return 格式化后的日期
     */
    public static String formate(Date date, String formate) {
        return getFormat(formate).format(date);
    }

    public static final String simpleFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simple).format(date);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static final String dtSimpleFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimple).format(date);
    }

    /**
     * yyyy-MM
     *
     * @param date
     * @return
     */
    public static final String dtSimpleYmFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimpleYm).format(date);
    }

    /**
     * yyyy-mm 日期格式转换为日期
     *
     * @param strDate
     * @return
     */
    public static final Date strToDtSimpleYmFormat(String strDate) {
        if (strDate == null) {
            return null;
        }
        try {
            return getFormat(dtSimpleYm).parse(strDate);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy-mm-dd 日期格式转换为日期
     *
     * @param strDate
     * @return
     */
    public static final Date strToDtSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        }
        try {
            return getFormat(dtSimple).parse(strDate);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy/MM/dd
     *
     * @param date
     * @return
     */
    public static final String dtSimpleSlashFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimpleSlash).format(date);
    }

    /**
     * yyyy/mm/dd 日期格式转换为日期
     *
     * @param strDate
     * @return
     */
    public static final Date strToDtSimpleSlashFormat(String strDate) {
        if (strDate == null) {
            return null;
        }
        try {
            return getFormat(dtSimpleSlash).parse(strDate);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static final String dtSimpleChineseFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimpleChinese).format(date);
    }

    /**
     * yyyy-MM-dd到 yyyy年MM月dd日 转换
     *
     * @param date
     * @return
     */
    public static final String dtSimpleChineseFormatStr(String date) throws ParseException {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimpleChinese).format(string2Date(date));
    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final Date string2Date(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return getFormat(dtSimple).parse(stringDate);
    }

    /**
     * 返回日期时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final Date string2DateTime(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return getFormat(simple).parse(stringDate);
    }

    /**
     * 返回日期时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final Date string2DateTimeByAutoZero(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return localDateTime2Date(date2LocalDate(string2Date(stringDate)).atTime(0, 0, 0));
    }

    /**
     * 返回日期时间(时分秒：23:59:59)
     *
     * @param stringDate String 型
     * @return
     * @throws ParseException
     */
    public static final Date string2DateTimeBy23(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return localDateTime2Date(date2LocalDate(string2Date(stringDate)).atTime(23, 59, 59));
    }

    /**
     * 计算日期差值
     *
     * @param String
     * @param String
     * @return int（天数）
     */
    public static final int calculateDecreaseDate(String beforDate, String afterDate) throws ParseException {
        Date date1 = getFormat(dtSimple).parse(beforDate);
        Date date2 = getFormat(dtSimple).parse(afterDate);
        long decrease = getDateBetween(date1, date2) / 1000 / 3600 / 24;
        int dateDiff = (int) decrease;
        return dateDiff;
    }

    /**
     * 计算时间差
     *
     * @param dBefor 首日
     * @param dAfter 尾日
     * @return 时间差(毫秒)
     */
    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0;
        long lAfter = 0;
        long lRtn = 0;
        /** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();
        lRtn = lAfter - lBefor;
        return lRtn;
    }

    /**
     * 返回日期时间
     *
     * @param stringDate (yyyyMMdd)
     * @return
     * @throws ParseException
     */
    public static final Date shortstring2Date(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }
        return getFormat(dtShort).parse(stringDate);
    }

    /**
     * 返回短日期格式（yyyyMMdd格式）
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final String shortDate(Date Date) {
        if (Date == null) {
            return null;
        }
        return getFormat(dtShort).format(Date);
    }

    /**
     * 返回长日期格式（yyyyMMddHHmmss格式）
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final String longDate(Date Date) {
        if (Date == null) {
            return null;
        }
        return getFormat(dtLong).format(Date);
    }

    /**
     * yyyy-MM-dd 日期字符转换为长整形
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static final Long string2DateLong(String stringDate) throws ParseException {
        Date d = string2Date(stringDate);
        if (d == null) {
            return null;
        }
        return Long.valueOf(d.getTime());
    }

    /**
     * 日期转换为字符串 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static final String hmsFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(hmsFormat).format(date);
    }

    /**
     * 时间转换字符串 2005-06-30 15:50
     *
     * @param date
     * @return
     */
    public static final String simpleDate(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simpleFormat).format(date);
    }

    /**
     * 字符串 2005-06-30 15:50 转换成时间
     *
     * @param date String
     * @return
     * @throws ParseException
     */
    public static final Date simpleFormatDate(String dateString) throws ParseException {

        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }

        return getFormat(simpleFormat).parse(dateString.trim());
    }


    /**
     * 把日期类型的日期换成数字类型 YYYYMMDD类型
     *
     * @param date
     * @return
     */
    public static final Long dateToNumber(Date date) {
        if (date == null) {
            return null;
        }
        String formated = getFormat(dtShort).format(date);
        return Long.valueOf(formated);
    }

    /**
     * 取得相差的天数
     *
     * @param startDate 格式为 2008-08-01
     * @param endDate   格式为 2008-08-01
     * @return
     */
    public static long countDays(String startDate, String endDate) {
        Date tempDate1 = null;
        Date tempDate2 = null;
        long days = 0;
        try {
            tempDate1 = DateUtil.string2Date(startDate);
            tempDate2 = DateUtil.string2Date(endDate);
            days = (tempDate2.getTime() - tempDate1.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return localDateTime2Date(LocalDateTime.now());
    }

    /**
     * 日期格式（yyyyMMdd格式）
     *
     * @return
     */
    public static String nowStr() {
        return shortDate(new Date());
    }

    /**
     * 将字符串按format格式转换为date类型
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return getFormat(format).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 是否闰年
     *
     * @param year
     * @return
     */
    public static final boolean isLeapYear(Date date) {
        return date2LocalDate(date).isLeapYear();
    }

    /**
     * 获取输入日期的相差日期
     *
     * @param dt
     * @param idiff
     * @return
     */
    public static final String getDiffDate(Date dt, int idiff) {

        LocalDate localDate = date2LocalDate(dt).plus(idiff, ChronoUnit.DAYS);
        return dtSimpleFormat(localDate2Date(localDate));
    }

    /**
     * 获取明天
     *
     * @return
     */
    public static Date tomorrow() {
        LocalDate localDate = LocalDate.now().plus(1, ChronoUnit.DAYS);
        return localDate2Date(localDate);
    }

    /**
     * 获取输入月份的相差日期
     *
     * @param dt    日期
     * @param idiff 相差月份
     * @return
     */
    public static final String getDiffMon(Date dt, int idiff) {
        LocalDate localDate = date2LocalDate(dt).plus(idiff, ChronoUnit.MONTHS);
        return dtSimpleFormat(localDate2Date(localDate));
    }

    /**
     * 获取输入年份的相差日期
     *
     * @param dt    日期
     * @param idiff 相差年份
     * @return
     */
    public static final String getDiffYear(Date dt, int idiff) {
        LocalDate localDate = date2LocalDate(dt).plus(idiff, ChronoUnit.YEARS);
        return dtSimpleFormat(localDate2Date(localDate));
    }

    /**
     * 获取输入星期的相差日期
     *
     * @param dt    日期
     * @param idiff 相差年份
     * @return
     */
    public static final String getDiffWeek(Date dt, int idiff) {
        LocalDate localDate = date2LocalDate(dt).plus(idiff, ChronoUnit.WEEKS);
        return dtSimpleFormat(localDate2Date(localDate));
    }


    /**
     * 获取某日期是星期几 返回英文星期 eg：monday
     *
     * @param dt
     * @return
     */
    public static String getDayOfWeekEN(Date dt) {
        return date2LocalDate(dt).getDayOfWeek().toString();
    }

    /**
     * 获取某日期是星期几  返回中文格式星期 eg ：星期一
     *
     * @param dt
     * @return
     */
    public static String getDayOfWeekCN(Date dt) {

        return WeekEnum.getMsgByCode(getDayOfWeekEN(dt));
    }

    /**
     * 获取date那天的开始时间，00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfTheDate(Date date) {
        if (date == null) {
            return null;
        }
        return localDateTime2Date(date2LocalDate(date).atTime(0, 0, 0));
    }

    /**
     * 获取date那天的结束时间，23:59:
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfTheDate(Date date) {
        if (date == null) {
            return null;
        }
        return localDateTime2Date(date2LocalDate(date).atTime(23, 59, 59));
    }

    /**
     * 判断日期前后
     * date1 > date2 返回 true
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isAfter(Date date1, Date date2) {
        return date2LocalDateTime(date1).isAfter(date2LocalDateTime(date2));
    }

    /**
     * date 转换为 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * date 转换为 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        return date2LocalDateTime(date).toLocalDate();
    }

    /**
     * date 转换为  LocalTime
     *
     * @param date
     * @return LocalTime
     */
    public static LocalTime date2LocalTime(Date date) {
        return date2LocalDateTime(date).toLocalTime();
    }

    /**
     * localDateTime  转换为 Date
     *
     * @param localDateTime
     * @return Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * localDate 转换为 Date
     *
     * @param localDate
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 根据传入的日期与时间戳 转换成Date 类型时间
     *
     * @param localDate 日期
     * @param localTime 时间
     * @return Date
     */
    public static Date localTime2Date(LocalDate localDate, LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    public static void main(String args[]) {
        Date now = now();
        System.out.println("================>>>>" + getDayOfWeekCN(now));
    }
}
