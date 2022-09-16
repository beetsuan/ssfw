package com.ssfw.common.util;

import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * @author a
 */
public class MapUtil {
    /**
     * 将map中value == null的属性键值对清空（移除）
     * @param map Map
     */
    public static void clearNullValue(final Map map){
        if (null == map){
            return;
        }
        map.keySet().removeIf(s -> null == map.get(s));
    }
    /**
     * 将map中value == null || value == '' 的属性键值对清空（移除）
     * @param map Map
     */
    public static void clearEmptyValue(final Map map){
        if (null == map){
            return;
        }
        map.keySet().removeIf(s -> null == map.get(s) || "".equals(map.get(s).toString()));
    }

    /**
     * 将map中的所有字符值试图转换为日期类型的值
     * @param map Map
     */
    public static void parseDateStrToDate(final Map<String,Object> map){

        if (null == map){
            return;
        }
        map.forEach((s, o) -> {
            if (o instanceof String){
                map.put(s,parseStrValueToDate(o));
            }
        });
    }

    private static Object parseStrValueToDate(Object strDate) {

        if (null == strDate || strDate.toString().length()<8){
            return strDate;
        }
        try {
            return LocalDateUtil.parseDateTime(strDate.toString());
        } catch (DateTimeParseException e) {
            try {
                return LocalDateUtil.parseDateMinute(strDate.toString());
            } catch (DateTimeParseException ex) {
                try {
                    return LocalDateUtil.parseDate(strDate.toString());
                } catch (DateTimeParseException ignore) {
                    return strDate;
                }
            }

        }
    }
}
