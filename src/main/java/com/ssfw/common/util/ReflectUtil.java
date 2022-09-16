package com.ssfw.common.util;

import com.ssfw.auth.util.LoginUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射工具类
 * @author a
 */
public class ReflectUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 遍历object的属性
     * @param object 被遍历的对象
     * @param callback 每次的回调
     */
    public static Map<String,Object> foreach(Object object, Callback callback) throws Exception {
        //获取实体类的所有属性，返回Field数组
        final Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method m;
        String name;
        Object value;
        Map<String,Object> map = new HashMap<>(fields.length);

        for (Field field : fields) {
            field.setAccessible(true);
            if (null != field.getAnnotation(IgnoreField.class)){
                continue;
            }
            name = field.getName();
            //将属性的首字符大写，方便构造get，set方法
            name = name.substring(0,1).toUpperCase()+name.substring(1);
            try {
                m = clazz.getMethod("get"+name);
                //调用getter方法获取属性值
                value = m.invoke(object);
                if (null !=callback){
                    callback.accept(field.getName(),value);
                }
                map.put(field.getName(),value);
            } catch (NoSuchMethodException|SecurityException ignore) {
            }
        }
        return map;
    }

    public interface Callback{
        /**
         * 钩子回调
         * @param fieldName 字段名称
         * @param fieldValue 字段值
         */
        void accept(String fieldName,Object fieldValue);
    }

    public static boolean hasField(Class<?> targetClass, String fieldName){
        try {
            targetClass.getDeclaredField(fieldName);
        }catch (NoSuchFieldException ignore){
            return false;
        }
        return true;
    }

    /**
     * 给对象的属性设置值
     * @param object 对象
     * @param fieldName 属性字段名称
     * @param fieldValue 属性值
     */
    public static void setValue(Class<?> targetClass,Object object,String fieldName,Object fieldValue){

        try {
            Field f;
            try {
                f = targetClass.getDeclaredField(fieldName);
            }catch (NoSuchFieldException ignore){
                return;
            }
            f.setAccessible(true);
            f.set(object,fieldValue);

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 给对象的属性设置值
     * @param object 对象
     * @param fieldName 属性字段名称
     * @param fieldValue 属性值
     */
    public static void setValue(Object object,String fieldName,Object fieldValue){

        setValue(object.getClass(), object, fieldName, fieldValue);
    }

    /**
     * 给对象的属性设置值,当且仅当对象属性存在且没有值
     * @param object 对象
     * @param fieldName 属性字段名称
     * @param fieldValue 属性值
     */
    public static boolean setValueIfAbsent(Object object,String fieldName,Object fieldValue){

        if (ReflectUtil.hasField(object.getClass(), fieldName)) {
            Object value = ReflectUtil.getValue(object, fieldName);
            if (null == value){
                ReflectUtil.setValue(object,fieldName,fieldValue);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定字段的值
     * @date 2019年12月18日
     * @param entity 对象
     * @param filedName 字段名称
     */
    public static Object getValue(@NotNull final Object entity, @NotNull final String filedName) {

        if (null != entity) {

            Method[] methods = entity.getClass().getMethods();

            for (Method method : methods) {
                if (method.getName().equals("get" + filedName)) {
                    try {
                        return method.invoke(entity);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        log.error("getFieldValue Exception:"+e.getMessage());
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取指定注解的值
     * @date 2019年12月18日
     * @param entity 对象
     * @param annotationClass 注解类
     */
    public static List<Object> getValue(@NotNull final Object entity, @NotNull final Class<?> annotationClass) {

        final ArrayList<Object> objects = new ArrayList<>();
        if (null != entity) {

            String annotationClassName = annotationClass.getName();
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Arrays.stream(field.getAnnotations()).forEachOrdered(annotation -> {
                    String name = annotation.annotationType().getName();
                    if (name.equals(annotationClassName)){
                        try {
                            objects.add(field.get(entity));
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            log.error("getValueWithAnnotation Exception:"+e.getMessage());
                        }
                    }
                });
            }
            Method[] methods = entity.getClass().getMethods();
            for (Method method : methods) {
                Arrays.stream(method.getAnnotations()).forEachOrdered(annotation -> {
                    String name = annotation.annotationType().getName();
                    if (name.equals(annotationClassName)){
                        try {
                            objects.add(method.invoke(entity));
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            log.error("getValueWithAnnotation Exception:"+e.getMessage());
                        }
                    }
                });
            }
        }
        return objects;
    }


    /**
     * 反射工具类忽略的字段
     */
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IgnoreField{
    }

}
