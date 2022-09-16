package com.ssfw.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author a
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String STANDARD_TIMEZONE = "GMT+8";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final ObjectMapper OBJECT_NON_NULL_MAPPER = new ObjectMapper();

    static {
        config(OBJECT_MAPPER);
        configNonNull(OBJECT_NON_NULL_MAPPER);
    }

    public static void config(ObjectMapper mapper){
        //对象的所有字段全部列入
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下的样式
        mapper.setDateFormat(new SimpleDateFormat(STANDARD_DATE_FORMAT));
        //设置时区
        mapper.setTimeZone(TimeZone.getTimeZone(STANDARD_TIMEZONE));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //LocalDateTime序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(timeModule);
    }

    public static void configNonNull(ObjectMapper mapper){
        //对象的所有字段全部列入
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //取消默认转换timestamps形式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下的样式
        mapper.setDateFormat(new SimpleDateFormat(STANDARD_DATE_FORMAT));
        //设置时区
        mapper.setTimeZone(TimeZone.getTimeZone(STANDARD_TIMEZONE));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //LocalDateTime序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(timeModule);
    }

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String obj2String(T obj) {

        return obj2String(obj,false);
    }

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @param nonNull 是否不包含null值字段
     * @return Json格式字符串
     */
    public static <T> String obj2String(T obj,boolean nonNull) {

        if (obj == null) {
            return null;
        }
        ObjectMapper mapper;
        mapper = nonNull? OBJECT_NON_NULL_MAPPER : OBJECT_MAPPER;
        try {
            return obj instanceof String ? (String) obj : mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 对象转json，支持过滤给定的属性
     * @param obj 目标对象
     * @param id 过滤ID 要求目标对象类被注解@JsonFilter("#id")
     * @param excludePropNames 过滤的属性
     * @param <T> any
     * @return json string
     */
    public static <T> String obj2StringExclude(T obj,String id , String... excludePropNames) {

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(id, SimpleBeanPropertyFilter.serializeAllExcept(excludePropNames));
        return obj2String(obj,filterProvider);
    };

    /**
     * 对象转json，支持只保留给定的属性
     * @param obj 目标对象
     * @param id 过滤ID 要求目标对象类被注解@JsonFilter("#id")
     * @param includePropNames 保留的属性
     * @param <T> any
     * @return json string
     */
    public static <T> String obj2StringInclude(T obj,String id , String... includePropNames) {

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(id, SimpleBeanPropertyFilter.filterOutAllExcept(includePropNames));
        return obj2String(obj,filterProvider);
    };

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @param filterProvider 过滤器
     * @return Json格式字符串
     */
    public static <T> String obj2String(T obj, FilterProvider filterProvider) {

        if (obj == null) {
            return null;
        }
        if (null != filterProvider){
            ObjectMapper objectMapper = new ObjectMapper();
            config(objectMapper);
            objectMapper.setFilterProvider(filterProvider);
            try {
                return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.warn("Parse Object to String error : {}", e.getMessage());
                return null;
            }
        }
        return obj2String(obj);
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     * @param obj 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String obj2StringPretty(T obj) {

        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     * @param str 要转换的字符串
     * @param elementClasses 元素类
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T string2Obj(String str, Class<T> elementClasses){
        if(StringUtils.isEmpty(str) || elementClasses == null){
            return null;
        }
        try {
            return elementClasses.equals(String.class) ? (T) str : OBJECT_MAPPER.readValue(str, elementClasses);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> List<T> string2List(@NotNull String str,@NotNull Class<T> elementClasses){


        JavaType javaType = getCollectionType(ArrayList.class, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    public static <T> List<T> string2List(@NotNull String str,@NotNull TypeReference<List<T>> typeReference){


        try {
            return OBJECT_MAPPER.readValue(str, typeReference);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }
    /**
     * 字符串转换为Map对象
     * @param str 要转换的字符串
     * @return Map对象
     */
    public static Map<String, Object> string2Map(String str) {

        try {
            return OBJECT_MAPPER.readValue(str, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 字符串转换为自定义对象
     * 集合对象与Json字符串之间的转换
     *
     * @param str 要转换的字符串
     * @param typeReference 使用TypeReference可以明确的指定反序列化的类型
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : OBJECT_MAPPER.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClasses) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClazz, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}" + e.getMessage());
            return null;
        }
    }

    public static <T> Map<String, Object> obj2Map(T obj) {
        String json = obj2String(obj);
        return string2Map(json);
    }

    /**
     * 时间序列化时变为时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }

    /**
     * 时间戳反序列化时间
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            long timestamp = jsonParser.getLongValue();
            return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
        }
    }
}
