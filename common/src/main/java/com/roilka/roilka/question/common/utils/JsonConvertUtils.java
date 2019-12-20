package com.roilka.roilka.question.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName JsonConvertUtils
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/20 14:55
 **/
public class JsonConvertUtils {

    public static final ObjectMapper mapper = new ObjectMapper();
    public static final ObjectMapper mapperIgnoreNull = new ObjectMapper();
    static{
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperIgnoreNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String objectToJson(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String objectToJson(Object obj, boolean ignoreNull){
        try {
            if(ignoreNull)
                return mapperIgnoreNull.writeValueAsString(obj);
            else
                return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<? extends T> clas){
        try {
            return mapper.readValue(json, clas);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<? extends T> clas, Class<?>... parameterClasses){
        try {
            return mapper.readValue(json, getJavaType(clas, parameterClasses));
        } catch (IOException e) {
            return null;
        }
    }

    public static JavaType getJavaType(Class<?> clas, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(clas, parameterClasses);
    }

    /**
     * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public static void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            //logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            //logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(Object jsonData, Class<T> beanType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = mapper.readValue(objectToJson(jsonData), javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> T json2Object(Object json, Class<? extends T> clas){
        try {
            return mapper.readValue(json + "", clas);
        } catch (IOException e) {
            return null;
        }
    }
}
