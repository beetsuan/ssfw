package com.ssfw.common.util;


import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 数组工具类
 * @author a
 */
public class ArraysUtil {


    /**
     * 集合对象拼接为字符串对象
     *
     * @param join 分隔符
     * @param collection 集合
     * @return StringBuilder
     */
    public static StringBuilder toString(final String join, @NotNull Collection<?> collection){

        StringBuilder stringBuilder = new StringBuilder();
        final int[] index = {0};
        collection.forEach(o -> {
            if (null == o || StringUtils.isBlank(o.toString())){
                return;
            }
            if (index[0]++>0 && null!=join){ stringBuilder.append(join);}
            stringBuilder.append(o);
        });
        return stringBuilder;
    }

    /**
     *
     * 数组拼接为字符串对象
     * @param join 分隔符
     * @param array 数组
     * @param <T> T
     * @return 字符串对象
     */
    @SafeVarargs
    public static <T> StringBuilder toString(String join, @NotNull T... array){

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i>0 && null!=join){
                stringBuilder.append(join);
            }
            final @NotNull T t = array[i];
            if (null == t || StringUtils.isBlank(t.toString())){
                continue;
            }
            stringBuilder.append(t.toString().trim());

        }
        return stringBuilder;
    }


    /**
     * 对象拼接为字符串对象
     *
     *
     * @param join 分隔符
     * @param object 对象
     * @return 字符串对象
     */
    public static StringBuilder toString(String join, @NotNull Object object){

        StringBuilder stringBuilder = new StringBuilder();
        int length = object.toString().length();
        for (int i = 0; i < length; i++) {
            if (i>0 && null!=join){
                stringBuilder.append(join);
            }
            stringBuilder.append(object.toString().charAt(i));

        }
        return stringBuilder;
    }
}
