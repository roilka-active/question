package com.roilka.roilka.question.common.demo.guava;/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2711:41
 */

/**
 * @ClassName CacheService
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/27 11:41
 **/
public interface CacheService {
    // 存方法
    void setCommonCache(String key,Object value);
    //
    Object getFromCommonCache(String key);
}
