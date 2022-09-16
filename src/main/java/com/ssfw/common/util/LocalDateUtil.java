package com.ssfw.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author : xavier
 * 2020-09-27 14:58
 */
public final class LocalDateUtil {

    private LocalDateUtil() {
    }

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * 当前时间
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 当前日期
     * @return
     */
    public static LocalDate nowLocalDate() {
        return LocalDate.now();
    }

    /**
     * 当前时间
     * @return
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * localDateTime 转为Date
     *
     * @param dateTime
     * @return
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (null == dateTime){
            return null;
        }
        return Date.from(dateTime.atZone(ZONE_ID).toInstant());
    }

    /**
     * localDate 转为date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        if (null == localDate){
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
    }


    /**
     * Date 转化为 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        if (null == date){
            return null;
        }
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Date 转换为 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (null == date){
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID);
    }

    /**
     * LocalDate 转换为 LocalDateTime
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        if (null == localDate){
            return null;
        }
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    /**
     * LocalDateTime 转换为 LocalDate
     *
     * @param dateTime
     * @return
     */
    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        if (null == dateTime){
            return null;
        }
        return LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
    }


    /**
     * Date 转换为字符串
     *
     * @param date 日期
     * @param formatter
     * @return
     */
    public static String format(Date date, DateFormatter formatter) {
        if (null == date){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZONE_ID);
        return formatter.getDateTimeFormatter().format(localDateTime);
    }


    /**
     * Date 转换为字符串
     *
     * @param dateTime 日期
     * @param pattern 格式
     * @return
     */
    public static String format(LocalDateTime dateTime, String pattern, Locale locale) {

        if (null == dateTime){
            return null;
        }
        return DateTimeFormatter.ofPattern(pattern, locale).format(dateTime);
    }

    /**
     * Date 转换为字符串
     *
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String format(LocalDate date, String pattern, Locale locale) {

        if (null == date){
            return null;
        }
        return DateTimeFormatter.ofPattern(pattern, locale).format(date);
    }

    /**
     * Date 转换为字符串
     *
     * @param date 日期
     * @param pattern 格式
     * @return
     */
    public static String format(LocalDate date, String pattern) {
       return format(date, pattern, Locale.getDefault());
    }

    /**
     * Date 转换为字符串
     *
     * @param dateTime 日期
     * @param pattern 格式
     * @return
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return format(dateTime, pattern, Locale.getDefault());
    }


    /**
     * Date 转换为字符串 yyyy-MM-dd 格式
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, DateFormatter.DATE_FORMATTER);
    }

    /**
     * TemporalAccessor 转换为字符串 yyyy-MM-dd 格式
     *
     * @param date
     * @return
     */
    public static String formatDate(TemporalAccessor date) {
        if (null == date){
            return null;
        }
        return DateFormatter.DATE_FORMATTER.dateTimeFormatter.format(date);
    }


    /**
     * Date 转换为字符串 yyyy-MM-dd HH:mm 格式
     *
     * @param date
     * @return
     */
    public static String formatDateMinute(Date date) {
        return format(date, DateFormatter.DATE_MINUTE_FORMATTER);
    }

    /**
     * TemporalAccessor 转换为字符串 yyyy-MM-dd HH:mm 格式
     *
     * @param date
     * @return
     */
    public static String formatDateMinute(TemporalAccessor date) {
        if (null == date){
            return null;
        }
        return DateFormatter.DATE_MINUTE_FORMATTER.dateTimeFormatter.format(date);
    }


    /**
     * Date 转换为字符串 yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, DateFormatter.DATE_TIME_FORMATTER);
    }

    /**
     * TemporalAccessor 转换为字符串 yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date
     * @return
     */
    public static String formatDateTime(TemporalAccessor date) {
        if (null == date){
            return null;
        }
        return DateFormatter.DATE_TIME_FORMATTER.dateTimeFormatter.format(date);
    }


    /**
     * Date 转换为字符串 yyyy年MM月dd日 格式
     *
     * @param date
     * @return
     */
    public static String formatDateCn(Date date) {
        return format(date, DateFormatter.DATE_CN_FORMATTER);
    }

    /**
     * TemporalAccessor 转换为字符串 yyyy年MM月dd日 格式
     *
     * @param date
     * @return
     */
    public static String formatDateCn(TemporalAccessor date) {
        if (null == date){
            return null;
        }
        return DateFormatter.DATE_CN_FORMATTER.dateTimeFormatter.format(date);
    }


    /**
     * Date 转换为字符串 yyyy年MM月dd日 HH时mm分ss秒 格式
     *
     * @param date
     * @return
     */
    public static String formatDateTimeCn(Date date) {
        return format(date, DateFormatter.DATE_TIME_CN_FORMATTER);
    }

    /**
     * TemporalAccessor 转换为字符串 yyyy年MM月dd日 HH时mm分ss秒 格式
     *
     * @param date
     * @return
     */
    public static String formatDateTimeCn(TemporalAccessor date) {
        if (null == date){
            return null;
        }
        return DateFormatter.DATE_TIME_CN_FORMATTER.dateTimeFormatter.format(date);
    }

    /**
     * 字符串转换为date
     *
     * @param text
     * @param formatter
     * @return
     */
    public static Date parse(String text, DateFormatter formatter) {
        return formatter.parse(text);
    }

    /**
     * 格式字符串 转换为 date
     *
     * @param text 格式yyyy-MM-dd || yyyy-MM-dd HH:mm:ss || yyyy-MM-dd HH:mm
     * @return
     */
    public static Date parseDateFriend(String text) {

        if (null == text){
            return null;
        }
        try {
           return parseDateTime(text);
        }catch (Exception ignore){
            try {
                return parseDate(text);
            }catch (Exception ignore2){
                return parseDateMinute(text);
            }
        }
    }


    /**
     * yyyy-MM-dd 格式字符串 转换为 date
     *
     * @param text
     * @return
     */
    public static Date parseDate(String text) {
        return DateFormatter.DATE_FORMATTER.parse(text);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式字符串 转换为 date
     *
     * @param text
     * @return
     */
    public static Date parseDateMinute(String text) {
        return DateFormatter.DATE_MINUTE_FORMATTER.parse(text);
    }

    /**
     * 字符串 转换为 date  yyyy-MM-dd HH:mm:ss 格式
     *
     * @param text
     * @return
     */
    public static Date parseDateTime(String text) {
        return DateFormatter.DATE_TIME_FORMATTER.parse(text);
    }

    public enum DateFormatter {
        /**
         * 格式yyyy
         */
        YEAR_FORMATTER(DateTimeFormatter.ofPattern("yyyy", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                Year year = Year.parse(text, dateTimeFormatter);
                return Date.from(year.atDay(1).atStartOfDay(ZONE_ID).toInstant());
            }
        },
        /**
         * 格式 yyyy-MM
         */
        YEAR_MONTH_FORMATTER(DateTimeFormatter.ofPattern("yyyy-MM", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                YearMonth yearMonth = YearMonth.parse(text, dateTimeFormatter);
                return Date.from(yearMonth.atDay(1).atStartOfDay(ZONE_ID).toInstant());
            }
        },

        /**
         * 格式 yyyy-MM-dd
         */
        DATE_FORMATTER(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                LocalDate localDate = LocalDate.parse(text, dateTimeFormatter);
                return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
            }
        },
        /**
         * 格式 yyyy-MM-dd HH:mm
         */
        DATE_MINUTE_FORMATTER(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                LocalDate localDate = LocalDate.parse(text, dateTimeFormatter);
                return Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
            }
        },

        /**
         * 格式 yyyy-MM-dd HH:mm:ss
         */
        DATE_TIME_FORMATTER(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                LocalDateTime localDateTime = LocalDateTime.parse(text, dateTimeFormatter);
                return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
            }
        },
        /**
         * 格式 yyyy年MM月dd日
         */
        DATE_CN_FORMATTER(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                LocalDateTime localDateTime = LocalDateTime.parse(text, dateTimeFormatter);
                return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
            }
        },
        /**
         * 格式 yyyy年MM月dd日 HH时mm分ss秒
         */
        DATE_TIME_CN_FORMATTER(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINA)) {
            @Override
            public Date parse(String text) {
                LocalDateTime localDateTime = LocalDateTime.parse(text, dateTimeFormatter);
                return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
            }
        };
        protected final DateTimeFormatter dateTimeFormatter;

        DateFormatter(DateTimeFormatter dateTimeFormatter) {
            this.dateTimeFormatter = dateTimeFormatter;
        }

        public DateTimeFormatter getDateTimeFormatter() {
            return dateTimeFormatter;
        }

        public abstract Date parse(String text);
    }


    public static String getSecondTimestamp(Date date) {
        if (null == date) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
        return String.valueOf(date.getTime() / 1000);
    }

}
