package com.roilka.roilka.question.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName CollectionUtil
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/20 14:52
 **/
@Slf4j
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotNullOrEmpty(Collection<? extends Object> collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNotNullOrEmpty(Map<? extends Object, ? extends Object> map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 字符串转为HashMap
     *
     * @param text
     * @return
     */
    public static Map<String, String> jsonToMap(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return null;
            }
            return JSON.parseObject(text, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Exception e) {
            log.error("convert str to map error, str:{}", text);
            return new HashMap<>();
        }
    }

    /**
     * 字符串转为HashMap
     *
     * @param text
     * @return
     */
    public static Map<String, String[]> jsonToParameterMap(String text) {
        try {
            if (StringUtils.isBlank(text)) {
                return null;
            }
            return JSON.parseObject(text, new TypeReference<HashMap<String, String[]>>() {
            });
        } catch (Exception e) {
            log.error("convert str to map error, str:{}", text);
            return new HashMap<>();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getParameter(String key, Map<String, String[]> parameterMap, Class<T> t) {
        if (null == parameterMap) {
            return null;
        }
        String[] value = parameterMap.get(key);
        if (null == value) {
            return null;
        }
        if (t == String.class) {
            if (value.length > 0) {
                return (T) value[0];
            }
        } else if (t == String[].class) {
            return (T) value;
        }
        return null;
    }

    /**
     * 通过对象的属性包装Map
     *
     * @param o
     * @return
     */
    public static Map<String, String> createMapByObjField(Object o) {
        Map<String, String> map = new HashMap<>();
        // 记录，老的JAVA，
        // getFields() 获取所有public和protected字段,包括父类字段, 1.8经测试只能是获取public
        // getDeclaredFields()
        // 获取所有字段,public和protected和private,但是不包括父类字段,经测试static final等会影响使用
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(o);
                if (obj != null) {
                    map.put(field.getName(), (String) obj);
                } else {
                    map.put(field.getName(), "");
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        log.debug("createMapByObjField key set:{}", map.keySet());
        return map;
    }

    /**
     * 除去数组中的空值和指定key
     *
     * @param map 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> emptyValueFilter(Map<String, String> map, Set<String> filterKeySet) {
        if (filterKeySet == null) {
            filterKeySet = new HashSet<String>();
        }

        Map<String, String> result = new HashMap<String, String>();
        if (map == null || map.size() <= 0) {
            return result;
        }

        for (String key : map.keySet()) {
            String value = map.get(key);
            if (value == null || value.equals("") || filterKeySet.contains(key)) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createAndLinkString(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);

            // 拼接时，不包括最后一个&字符
            if (i < keys.size() - 1) {
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 把所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createQueryString(@SuppressWarnings("rawtypes") Map map) {
        @SuppressWarnings("unchecked")
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);

            // 拼接时，不包括最后一个&字符
            if (i < keys.size() - 1) {
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 将ServletRequest的ParameterMap转换为QueryString
     *
     * @param map 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createQueryStringByParameterMap(Map<String, String[]> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String[] value = map.get(key);

            stringBuilder.append(key);
            stringBuilder.append("=");
            if (value != null && value.length > 0) {
                for (int j = 0; j < value.length; j++) {
                    if (j >= value.length - 1) {
                        stringBuilder.append(value[j]);
                    } else {
                        stringBuilder.append(value[j] + ",");
                    }
                }
            }
            // 拼接时，不包括最后一个&字符
            if (i < keys.size() - 1) {
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 字符串转为jsonToList
     *
     * @param <T>
     * @param text
     * @return
     */
    public static <T> List<T> jsonToList(String text, Class<T> cls) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseObject(text, new TypeReference<ArrayList<T>>() {
        });
    }

    /**
     * 字符串转为jsonToSet
     *
     * @param <T>
     * @param text
     * @return
     */
    public static <T> Set<T> jsonToSet(String text, Class<T> cls) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseObject(text, new TypeReference<HashSet<T>>() {
        });
    }

    /**
     *  将集合按照subSize 拆分成若干个子集合
     * @param list
     * @param subSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> subList(List<T> list, int subSize) {
        List<List<T>> result = new ArrayList<>();
        if (list == null || list.size() < 1) return result;

        int orgSize = list.size();
        if (orgSize <= subSize) {
            result.add(list);
            return result;
        }

        for (int start = 0, end = start + subSize; start < orgSize; start += subSize,end +=subSize) {

            result.add(list.subList(start, end > orgSize ? orgSize : end));
        }
        return result;
    }

    public static void main(String[] args) {
        List<Bean> list = new ArrayList<>();
        Bean bean = new Bean();
        bean.setAddress("a");
        bean.setCode(1);
        bean.setName("11");
        bean.setScore(21);
        list.add(bean);
        bean = new Bean();
        bean.setAddress("b");
        bean.setCode(1);
        bean.setName("11");
        bean.setScore(3);
        list.add(bean);
        bean = new Bean();
        bean.setAddress("a");
        bean.setCode(3);
        bean.setName("2");
        bean.setScore(2);
        list.add(bean);
        bean = new Bean();
        bean.setAddress("2");
        bean.setCode(5);
        bean.setName("55");
        bean.setScore(555);
        list.add(bean);
        //Map<Integer,String> map =list.stream().distinct().collect(Collectors.toMap(Bean::getCode, Bean::getName));
        //System.out.println(map);
        HashMap map1 = new HashMap();
        map1.put(1, "fd");
        String s = "I feel unhappy!";
        int  h = 0;
         h = (h = s.hashCode()) ^ (h >>> 16);
         System.out.println(h);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<>();
    }
    @Data
    public static class Bean{
        private int code;
        private String name;
        private Integer score;
        private String address;
    }

}
