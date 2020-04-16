package com.roilka.roilka.question.common.utils;

import com.alibaba.fastjson.JSON;
import com.roilka.roilka.question.common.cache.LoadCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName RedisUtils
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/20 14:42
 **/
public class RedisUtils {
    private Logger logger                     = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * redis操作类
     */
    private StringRedisTemplate redis;

    private String                     redisPrefix;

    /**
     * get or set 方法分布式锁的过期时间ms
     */
    private static final long          GET_OR_SET_EXPIRED_TIME    = 10000;

    /**
     * get or set 方法休眠时间ms
     */
    private static final long          GET_OR_SET_SLEEP_TIME      = 50;

    /**
     * 默认获取锁等待时间5秒
     */
    private static final int           DEFAULT_WAIT_TIME          = 5;

    /**
     * 获取锁的随机休眠时间
     */
    private static final int           GET_LOCK_RANDOM_SLEEP_TIME = 100;

    /**
     * 工具类实体
     */
    private static volatile RedisUtils instance;

    public RedisUtils(StringRedisTemplate redis, String redisPrefix) {
        this.redis = redis;
        this.redisPrefix = redisPrefix;
    }

    public RedisUtils() {
    }

    public RedisUtils getInstance(StringRedisTemplate redis, String redisPrefix) {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new RedisUtils(redis, redisPrefix);
                }
            }
        }
        return instance;
    }

    public static RedisUtils initInstance() {
        return instance;
    }
    /**
     * 获取存储的信息
     *
     * @param key
     * @return clazz
     */
    public <T> T redisGetWithInstance(String key, Class<? extends T> clazz) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据并封成对象返回:key={}", key);
        }

        if (StringUtils.isBlank(key)) {
            return null;
        }

        T result = null;

        try {
            String resultStr = redis.opsForValue().get(key);
            if (StringUtils.isBlank(resultStr)) {
                return null;
            }
            result = JsonConvertUtils.json2Object(resultStr, clazz);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据失败:key={}", key, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象返回结束:key={},result={}", key, result);
        }

        return result;
    }

    /**
     * 获取存储的信息
     *
     * @param key
     */
    public String redisGet(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据并封成对象返回:key={}", key);
        }

        String result = null;

        try {
            result = redis.opsForValue().get(key);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据失败:key={}", key, e);
            }

            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据并封成对象返回:key={},result={}", key, result);
        }

        return result;
    }

    /**
     * <p>
     * get or set 防缓存击穿,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param clazz T
     * @return T
     */
    public <T> T redisGetOrSet(String key, LoadCallback<T> callback, long expiredTime, Class<? extends T> clazz) {
        return redisGetOrSet(key, callback, expiredTime, GET_OR_SET_EXPIRED_TIME, clazz);
    }

    /**
     * <p>
     * get or set 防缓存击穿,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param waitLockTime 分布式锁的超时时间ms
     * @param clazz T
     * @return T
     */
    public <T> T redisGetOrSet(String key, LoadCallback<T> callback, long expiredTime, long waitLockTime,
                               Class<? extends T> clazz) {
        T value = redisGetWithInstance(key, clazz);
        if (null != value) {
            return value;
        }
        String keyMutex = key + "_mutex";
        Object lockValue = getAtomLock(keyMutex, waitLockTime / 1000);
        if (!StringUtils.isBlank(lockValue)) {
            //获取锁成功
            T loadValue = null;
            try {
                T checkValue = redisGetWithInstance(key, clazz);
                if (null != checkValue) {
                    return checkValue;
                }
                loadValue = callback.load();
                if (null == loadValue) {
                    loadValue = clazz.newInstance();
                }
                redisSet(key, loadValue, expiredTime, TimeUnit.MILLISECONDS);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("redisGetOrSet异常: ", e);
            } finally {
                //可能失败
                releaseLock(keyMutex, String.valueOf(lockValue));
            }
            return loadValue;
        } else {
            //其他线程休息50毫秒后重试
            try {
                Thread.sleep(GET_OR_SET_SLEEP_TIME);
            } catch (InterruptedException e) {
                logger.info("redisGetOrSet等待中断", e);
            }
            return redisGetOrSet(key, callback, expiredTime, waitLockTime, clazz);
        }
    }

    /**
     * 存储信息
     *
     * @param key
     * @param json
     */
    public void redisSet(String key, String json) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},value={}", key, json);
            }

            redis.opsForValue().set(key, json);

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},value={}", key, json, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},value={}", key, json);
        }

    }

    /**
     * 存储信息
     *
     * @param key
     * @param json
     * @param timeout
     * @param unit
     */
    public void redisSet(String key, String json, long timeout, TimeUnit unit) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},value={},timeout={},unit={}", key, json, timeout, unit);
            }

            redis.opsForValue().set(key, json, timeout, unit);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},value={},timeout={},unit={}", key, json, timeout, unit, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},value={},timeout={},unit={}", key, json, timeout, unit);
        }

    }

    /**
     * 存储信息
     *
     * @param key
     * @param json
     */
    public void redisSet(String key, Object json) {

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},value={}", key, json);
            }

            redis.opsForValue().set(key, JsonConvertUtils.objectToJson(json));

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},value={}", key, json, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},value={}", key, json);
        }
    }

    /**
     * 存储信息
     *
     * @param key
     * @param json
     * @param timeout
     * @param unit
     */
    public void redisSet(String key, Object json, long timeout, TimeUnit unit) {

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},value={},timeout={},unit={}", key, json, timeout, unit);
            }

            redis.opsForValue().set(key, JsonConvertUtils.objectToJson(json), timeout, unit);

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},value={},timeout={},unit={}", key, json, timeout, unit, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},value={},timeout={},unit={}", key, json, timeout, unit);
        }

    }

    /**
     * hashSet
     *
     * @param key
     * @param json
     */
    public void redisHashSet(String key, String hashKey, String json) {

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},hashKey={},value={}", key, hashKey, json);
            }

            redis.opsForHash().put(key, hashKey, json);

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},hashKey={},value={}", key, hashKey, json, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},hashKey={},value={}", key, hashKey, json);
        }

    }

    /**
     * hashSet
     *
     * @param key
     * @param json
     */
    public void redisHashSet(String key, String hashKey, Object json) {

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("开始向redis存储数据:key={},hashKey={},value={}", key, hashKey, json);
            }

            redis.opsForHash().put(key, hashKey, JsonConvertUtils.objectToJson(json));

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis存储数据失败:key={},hashKey={},value={}", key, hashKey, json, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("向redis存储数据结束:key={},hashKey={},value={}", key, hashKey, json);
        }

    }

    /**
     * <p>
     * 批量put hash
     * </p>
     *
     * @param key key
     * @param map hash键值对
     */
    public void redisHashPutAll(String key, Map<String, String> map) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},map={}", key, map);
        }

        if (CollectionUtil.isNullOrEmpty(map)) {
            return;
        }

        try {
            BoundHashOperations<String, String, String> operations = redis.boundHashOps(key);
            operations.putAll(map);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={},map={}", key, map, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},map={}", key, map);
        }

    }

    /**
     * <p>
     * 批量put hash
     * </p>
     *
     * @param key key
     * @param map hash键值对
     */
    public void redisHashPutAllObj(String key, Map<String, Object> map) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},map={}", key, map);
        }

        if (CollectionUtil.isNullOrEmpty(map)) {
            return;
        }

        try {
            BoundHashOperations<String, String, String> operations = redis.boundHashOps(key);
            Map<String, String> strMap = new HashMap<>(map.size());
            map.forEach((s, o) -> {
                if (String.class.isAssignableFrom(o.getClass())) {
                    strMap.put(s, String.valueOf(o));
                } else {
                    strMap.put(s, JsonConvertUtils.objectToJson(o));
                }
            });
            operations.putAll(strMap);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={},map={}", key, map, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},map={}", key, map);
        }

    }

    /**
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object redisHashGet(String key, String hashKey) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        Object result = null;

        try {
            result = redis.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},hashKey={},result={}", key, hashKey, result);
        }

        return result;
    }

    /**
     * <p>
     * 批量获取hash
     * </p>
     *
     * @param key key
     * @param hashKeys hashKey集合
     * @return Object集合，数据不存在时返回<code>null</code>
     */
    public List<Object> redisHashMultiGet(String key, List<String> hashKeys) {
        return redisHashMultiGetWithInstance(key, hashKeys, Object.class);
    }

    /**
     * <p>
     * get or set 防缓存击穿 hash,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param hashKey redis hashKey
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param clazz T
     * @return T
     */
    public <T> T redisHashGetOrSet(String key, String hashKey, LoadCallback<T> callback, long expiredTime,
                                   Class<? extends T> clazz) {
        return redisHashGetOrSet(key, hashKey, callback, expiredTime, GET_OR_SET_EXPIRED_TIME, clazz);
    }

    /**
     * <p>
     * get or set 防缓存击穿 hash,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param hashKey redis hashKey
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param waitLockTime 分布式锁的超时时间ms
     * @param clazz T
     * @return T
     */
    public <T> T redisHashGetOrSet(String key, String hashKey, LoadCallback<T> callback, long expiredTime,
                                   long waitLockTime, Class<? extends T> clazz) {
        T value = redisHashGetWithInstance(key, hashKey, clazz);
        if (null != value) {
            return value;
        }
        String keyMutex = key + hashKey + "_hash_mutex";
        Object lockValue = getAtomLock(keyMutex, waitLockTime / 1000);
        if (!StringUtils.isBlank(lockValue)) {
            //获取锁成功
            T loadValue = null;
            try {
                T checkValue = redisHashGetWithInstance(key, hashKey, clazz);
                if (null != checkValue) {
                    return checkValue;
                }
                loadValue = callback.load();
                if (null == loadValue) {
                    loadValue = clazz.newInstance();
                }
                redisHashSet(key, hashKey, loadValue);
                setExpire(key, expiredTime, TimeUnit.MILLISECONDS);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("redisHashGetOrSet异常: ", e);
            } finally {
                //可能失败
                releaseLock(keyMutex, String.valueOf(lockValue));
            }

            return loadValue;
        } else {
            //其他线程休息50毫秒后重试
            try {
                Thread.sleep(GET_OR_SET_SLEEP_TIME);
            } catch (InterruptedException e) {
                logger.info("redisGetOrSet等待中断", e);
            }
            return redisHashGetOrSet(key, hashKey, callback, expiredTime, waitLockTime, clazz);
        }
    }

    /**
     * redisHashGetWithInstance 获取存储的信息
     *
     * @param key
     * @return clazz
     */
    public <T> T redisHashGetWithInstance(String key, String hashKey, Class<? extends T> clazz) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        if (StringUtils.isBlank(hashKey)) {
            return null;
        }

        T result = null;

        try {
            Object resultStr = redis.opsForHash().get(key, hashKey);
            //如果为空,直接返回null
            if (resultStr == null || StringUtils.isBlank(resultStr + "")) {
                return null;
            }
            result = JsonConvertUtils.json2Object(resultStr, clazz);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据失败:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},hashKey={},result={}", key, hashKey, result);
        }

        return result;
    }

    /**
     * <p>
     * 批量获取hash
     * </p>
     *
     * @param key key
     * @param hashKeys hashKey集合,不能为空
     * @param clazz T
     * @return T集合，数据不存在时返回<code>null</code>
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> redisHashMultiGetWithInstance(String key, List<String> hashKeys, Class<? extends T> clazz) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},hashKeys={}", key, hashKeys);
        }

        if (CollectionUtil.isNullOrEmpty(hashKeys)) {
            return null;
        }

        List<T> result;

        try {
            BoundHashOperations<String, String, String> operations = redis.boundHashOps(key);
            List<String> ts = operations.multiGet(hashKeys);
            //如果为空,直接返回null
            if (CollectionUtil.isNullOrEmpty(ts)) {
                return null;
            }
            //只过滤null
            List<String> nonNullResult = ts.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtil.isNullOrEmpty(nonNullResult)) {
                return null;
            }
            result = (List<T>) JsonConvertUtils.jsonToList(nonNullResult.toString(), clazz);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={},hashKeys={}", key, hashKeys, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},result={}", key, result);
        }

        return result;
    }

    /**
     * <p>
     * 获取hash的key下的所有hashKey
     * </p>
     *
     * @param key key
     * @return hashKey集合，不存在时返回<code>null</code>
     */
    public Set<String> redisHashKeys(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},", key);
        }

        Set<String> result;

        try {
            BoundHashOperations<String, String, String> operations = redis.boundHashOps(key);
            result = operations.keys();
            if (CollectionUtil.isNullOrEmpty(result)) {
                return null;
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={}", key, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},result={}", key, result);
        }

        return result;
    }
    /**
     * <p>
     * 获取hash的key下的数量大小
     * </p>
     *
     * @param key key
     * @return hashKey集合，不存在时返回<code>null</code>
     */
    public Long redisHashSize(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},", key);
        }

        Long result;

        try {
            BoundHashOperations<String, String, String> operations = redis.boundHashOps(key);
            result = operations.size();

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={}", key, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},result={}", key, result);
        }

        return result;
    }
    /**
     * 获取key下所有hash
     *
     * @param key key
     * @param clazz T
     * @return T Map，数据不存在时返回<code>null</code>
     */
    public <T> Map<String, T> redisHashGetAllWithInstance(String key, Class<? extends T> clazz) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={}", key);
        }
        if (StringUtils.isNullOrEmpty(key)) {
            return null;
        }
        Map<String, T> all;
        try {
            all = redis.execute((RedisCallback<Map<String, T>>) con -> {
                Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
                if (CollectionUtil.isNullOrEmpty(result)) {
                    return null;
                }

                Map<String, T> ans = new HashMap<>(result.size());
                for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                    T t = JsonConvertUtils.jsonToObject(new String(entry.getValue()), clazz);
                    ans.put(new String(entry.getKey()), t);
                }
                return ans;
            });
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据异常:key={}", key, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据并封成对象结束:key={},result={}", key, all);
        }
        return all;
    }

    /**
     *
     * @param key
     * @param hashKey
     */
    public void redisHashDelete(String key, String hashKey) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始删除redis缓存数据:key={},hashKey={}", key, hashKey);
        }

        boolean flag = false;
        try {
            flag = redis.opsForHash().get(key, hashKey) != null ? true : false;
            if (flag) {
                redis.opsForHash().delete(key, hashKey);
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("删除redis缓存数据失败:key={},hashKey={}", key, hashKey, e);
            }
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("删除redis缓存数据结束:key={},hashKey={}", key, hashKey);
        }

        return;
    }

    /**
     * 追加信息
     *
     * @param key
     * @param json
     */
    public void redisAppend(String key, String json) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始向redis中追加数据:key={}", key);
        }

        try {
            redis.opsForValue().append(key, json);

        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("向redis中追加数据失败:key={}", key, e);
            }
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("向redis中追加数据结束:key={}", key);
        }
    }

    /**
     *  删除信息
     * @param key
     */
    public void redisDelete(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis删除数据:key={}", key);
        }

        boolean flag = false;
        try {
            flag = redis.opsForValue().get(key) != null ? true : false;
            if (flag) {
                redis.delete(key);
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis删除数据异常:key={}", key, e);
            }

            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis删除数据结束:key={}", key);
        }
    }

    /**
     * 删除，删除前未执行查询操作，请谨慎使用，建议使用{RedisUtils#redisDelete}
     *
     * @param key key
     */
    public void redisDel(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis删除数据:key={}", key);
        }

        try {
            redis.delete(key);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis删除数据异常:key={}", key, e);
            }

            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis删除数据结束:key={}", key);
        }
    }

    /**
     * 获取存储的信息转化为list
     *
     * @param key
     * @param clazz
     */
    public <T> List<T> redisGetToList(String key, Class<T> clazz) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据并封成list返回:key={}", key);
        }

        List<T> result = null;

        try {
            String resultstr = redis.opsForValue().get(key);
            if (StringUtils.isBlank(resultstr)) {
                return null;
            }
            result = JsonConvertUtils.jsonToList(resultstr, clazz);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据失败:key={}", key, e);
            }

            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据并封成对象返回结束:key={},result={}", key, result);
        }

        return result;
    }

    /**
     * <p>
     * get or set 防缓存击穿,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param clazz T
     * @return T
     */
    public <T> List<T> redisGetOrSetList(String key, LoadCallback<List<T>> callback, long expiredTime,
                                         Class<? extends T> clazz) {
        return redisGetOrSetList(key, callback, expiredTime, GET_OR_SET_EXPIRED_TIME, clazz);
    }

    /**
     * <p>
     * get or set 防缓存击穿,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param waitLockTime 分布式锁的超时时间ms
     * @param clazz T
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> redisGetOrSetList(String key, LoadCallback<List<T>> callback, long expiredTime,
                                         long waitLockTime, Class<? extends T> clazz) {
        List<T> values = (List<T>) redisGetToList(key, clazz);
        if (null != values) {
            return values;
        }
        String keyMutex = key + "_mutex_list";
        Object lockValue = getAtomLock(keyMutex, waitLockTime / 1000);
        if (!StringUtils.isBlank(lockValue)) {
            //获取锁成功
            List<T> loadValue;
            try {
                List<T> checkValue = (List<T>) redisGetToList(key, clazz);
                if (null != checkValue) {
                    return checkValue;
                }
                loadValue = callback.load();
                if (null == loadValue) {
                    loadValue = Collections.emptyList();
                }
                redisSet(key, loadValue, expiredTime, TimeUnit.MILLISECONDS);
            } finally {
                //可能失败
                releaseLock(keyMutex, String.valueOf(lockValue));
            }
            return loadValue;
        } else {
            //其他线程休息50毫秒后重试
            try {
                Thread.sleep(GET_OR_SET_SLEEP_TIME);
            } catch (InterruptedException e) {
                logger.info("redisGetOrSet等待中断", e);
            }
            return redisGetOrSetList(key, callback, expiredTime, waitLockTime, clazz);
        }
    }

    /**
     * 以key -> hashkey的形式获取存储的数据
     *
     * @param key
     * @param hashKey
     * @return clazz
     */
    public <T> List<T> redisHashGetToList(String key, String hashKey, Class<T> clazz) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        if (StringUtils.isBlank(key) || StringUtils.isBlank(hashKey)) {
            return null;
        }

        List<T> result = null;

        try {
            Object resultobj = redis.opsForHash().get(key, hashKey);
            //如果为空,直接返回null
            if (StringUtils.isBlank(resultobj)) {
                return null;
            }
            result = JsonConvertUtils.jsonToList(resultobj + "", clazz);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得数据失败:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得数据结束:key={},hashKey={}", key, hashKey);
        }

        return result;
    }

    /**
     * <p>
     * get or set 防缓存击穿 hash,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param hashKey redis hashKey
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param clazz T
     * @return T
     */
    public <T> List<T> redisHashGetOrSetList(String key, String hashKey, LoadCallback<List<T>> callback,
                                             long expiredTime, Class<? extends T> clazz) {
        return redisHashGetOrSetList(key, hashKey, callback, expiredTime, GET_OR_SET_EXPIRED_TIME, clazz);
    }

    /**
     * <p>
     * get or set 防缓存击穿 hash,如果释放锁时恰好redis异常 导致锁释放失败,可能引起StackOverFlow
     * </p>
     *
     * @param key redis key
     * @param hashKey redis hashKey
     * @param callback 加载数据
     * @param expiredTime key过期时间ms
     * @param waitLockTime 分布式锁的超时时间ms
     * @param clazz T
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> redisHashGetOrSetList(String key, String hashKey, LoadCallback<List<T>> callback,
                                             long expiredTime, long waitLockTime, Class<? extends T> clazz) {
        List<T> values = (List<T>) redisHashGetToList(key, hashKey, clazz);
        if (null != values) {
            return values;
        }
        String keyMutex = key + hashKey + "_hash_mutex_list";
        Object lockValue = getAtomLock(keyMutex, waitLockTime / 1000);
        if (!StringUtils.isBlank(lockValue)) {
            //获取锁成功
            List<T> loadValue;
            try {
                List<T> checkValue = (List<T>) redisHashGetToList(key, hashKey, clazz);
                if (null != checkValue) {
                    return checkValue;
                }
                loadValue = callback.load();
                if (null == loadValue) {
                    loadValue = Collections.emptyList();
                }
                redisHashSet(key, hashKey, loadValue);
                setExpire(key, expiredTime, TimeUnit.MILLISECONDS);
            } finally {
                //可能失败
                releaseLock(keyMutex, String.valueOf(lockValue));
            }
            return loadValue;
        } else {
            //其他线程休息50毫秒后重试
            try {
                //可能失败
                Thread.sleep(GET_OR_SET_SLEEP_TIME);
            } catch (InterruptedException e) {
                logger.info("redisGetOrSet等待中断", e);
            }
            return redisHashGetOrSetList(key, hashKey, callback, expiredTime, waitLockTime, clazz);
        }
    }

    /**
     * 以key -> 获取完整key
     *
     * @param key
     */
    public Set<String> redisGetMatchKey(String key) {

        if (logger.isDebugEnabled()) {
            logger.debug("开始从redis取得完整key:key={}", key);
        }

        if (StringUtils.isBlank(key)) {
            return null;
        }

        Set<String> hashkey = null;

        try {
            hashkey = redis.keys(key);
            //如果为空,直接返回null
            if (StringUtils.isBlank(hashkey)) {
                return null;
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("从redis取得完整key失败:key={}", key, e);
            }
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("从redis取得完整key结束:key={},hashKey={}", key, hashkey);
        }

        return hashkey;
    }

    /**
     * @return the redisPrefix
     */
    public String getRedisPrefix() {
        return redisPrefix;
    }

    /**
     * @param redisPrefix the redisPrefix to set
     */
    public void setRedisPrefix(String redisPrefix) {
        this.redisPrefix = redisPrefix;
    }

    public void setExpire(String key, long timeout, TimeUnit unit) {
        redis.expire(key, timeout, unit);
    }

    /**
     * 自增长
     *
     * @param key
     * @return
     */
    public Long increment(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自增长:key={}", key);
        }
        Long increment;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory());
            increment = redisAtomicLong.incrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自增长失败:key={}", key, e);
            }

            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("redis自增长 返回:key={},result={}", key, increment);
        }
        return increment;
    }

    /**
     * 自增长
     *
     * @param key key
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 加1后的值
     */
    public Long increment(String key, long timeout, TimeUnit unit) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自增长:key={}", key);
        }
        Long increment;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory());
            redisAtomicLong.expire(timeout, unit);
            increment = redisAtomicLong.incrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自增长失败:key={}", key, e);
            }

            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("redis自增长 返回:key={},result={}", key, increment);
        }
        return increment;
    }

    /**
     * 自增长
     *
     * @param key
     * @param value 初始值
     * @return
     */
    public Long increment(String key, Long value) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自增长:key={},value={}", key, value);
        }
        Long increment;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory(), value);
            increment = redisAtomicLong.incrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自增长失败:key={},value={}", key, value, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis自增长 返回:key={},value={},result={}", key, value, increment);
        }
        return increment;
    }

    /**
     * 自增长
     *
     * @param key
     * @param value 初始值
     * @param timeout 过期时间
     * @param unit 过期时间单位
     * @return
     */
    public Long increment(String key, Long value, long timeout, TimeUnit unit) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自增长:key={},value={},timeout={},unit={}", key, value, timeout, unit);
        }
        Long increment;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory(), value);
            redisAtomicLong.expire(timeout, unit);
            increment = redisAtomicLong.incrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自增长失败:key={},value={},timeout={},unit={}", key, value, timeout, unit, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis自增长 返回:key={},value={},,timeout={},unit={},result={}", key, value, timeout, unit,
                    increment);
        }
        return increment;
    }

    /**
     * 自减
     *
     * @param key
     * @return
     */
    public Long dncrement(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自减:key={}", key);
        }
        Long dncrement;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory());
            dncrement = redisAtomicLong.decrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自减失败:key={}", key, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis自减 返回:key={},result={}", key, dncrement);
        }
        return dncrement;
    }

    /**
     * 自减
     *
     * @param key key
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 减1后的值
     */
    public Long dncrement(String key, long timeout, TimeUnit unit) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自减:key={}", key);
        }
        Long dncrement;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory());
            redisAtomicLong.expire(timeout, unit);
            dncrement = redisAtomicLong.decrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自减失败:key={}", key, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis自减 返回:key={},result={}", key, dncrement);
        }
        return dncrement;
    }

    /**
     * 自减
     *
     * @param key
     * @param value 初始值
     * @param timeout 过期时间
     * @param unit 过期时间单位
     * @return
     */
    public Long dncrement(String key, Long value, long timeout, TimeUnit unit) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis自减:key={},value={}", key, value);
        }
        Long dncrement;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory(), value);
            redisAtomicLong.expire(timeout, unit);
            dncrement = redisAtomicLong.decrementAndGet();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis自减失败:key={},value={},timeout={},unit={}", key, value, timeout, unit, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis自增长 返回:key={},value={},,timeout={},unit={},result={}", key, value, timeout, unit,
                    dncrement);
        }
        return dncrement;
    }

    /**
     * 获取自增长的值
     *
     * @param key
     * @return
     */
    public Long getForConn(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始redis获取自增长的值:key={}", key);
        }
        Long num;
        try {
            RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redis.getConnectionFactory());
            num = redisAtomicLong.get();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("redis获取自增长的值失败:key={}", key, e);
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("redis获取自增长的值返回:key={},result={}", key, num);
        }
        return num;
    }

    /**
     * redis加锁
     *
     * @param key 锁的key值
     * @param expiredTime 超时时间
     */
    public Boolean getLock(final String key, final long expiredTime) {
        return redis.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redis.getStringSerializer();
                // 返回是否获取到锁
                boolean result = true;
                // 循环获取
                while (true) {
                    // 时间戳
                    long value = System.currentTimeMillis() + expiredTime;
                    // 序列化
                    byte[] byteKeys = serializer.serialize(key.toString());
                    byte[] byteValues = serializer.serialize(value + "");
                    // 获取锁 true代表获取到锁，false代表没有获取到
                    boolean isLock = connection.setNX(byteKeys, byteValues);
                    // 如果没有获取到锁
                    if (!isLock) {
                        // 获取锁的过期时间
                        byte[] byteTime = connection.get(byteKeys);
                        String time = serializer.deserialize(byteTime);
                        // 如果锁不存在
                        if (null == time) {
                            result = false;
                            continue;
                        }
                        // 如果锁超时
                        if (Long.parseLong(time) < System.currentTimeMillis()) {
                            // 时间戳
                            value = System.currentTimeMillis() + expiredTime;
                            // 序列化
                            byteValues = serializer.serialize(value + "");
                            // 设置新的过期时间并返回旧的过期时间
                            byte[] oldByteTime = connection.getSet(byteKeys, byteValues);
                            String oldTime = serializer.deserialize(oldByteTime);
                            // 如果锁不存在
                            if (null == oldTime) {
                                result = false;
                                continue;
                            }
                            // 如果time=oldTime，代表获取到时间戳
                            if (Long.parseLong(time) == Long.parseLong(oldTime)) {
                                result = true;
                                break;
                            } else {
                                // 如果time!=oldTime，代表没有获取到时间戳，继续循环
                                try {
                                    // 休眠
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    logger.warn("redis锁被中断 ", e);
                                }
                                result = false;
                                continue;
                            }
                        }
                    } else {
                        // 获取到锁
                        result = true;
                        break;
                    }
                }

                return result;
            }
        });
    }

    /**
     * redis加锁
     *
     * @param key 锁的key值
     * @param expiredTime 超时时间
     * @deprecated 请使用getAtomLock
     */
    @Deprecated
    public String getLockSimple(final String key, final long expiredTime) {
        return redis.execute((RedisCallback<String>) connection -> {

            RedisSerializer<String> serializer = redis.getStringSerializer();

            // 时间戳
            long value = System.currentTimeMillis() + expiredTime;
            // 序列化
            byte[] byteKeys = serializer.serialize(key);
            byte[] byteValues = serializer.serialize(value + "");
            // 获取锁 true代表获取到锁，false代表没有获取到
            Boolean isLock = connection.setNX(byteKeys, byteValues);
            logger.debug("key={} setNX结果:{}", key, isLock);
            if (null == isLock || !isLock) {
                return null;
            }
            connection.pExpire(byteKeys, expiredTime);
            //把value返回，释放锁时用于判断是否为当前线程的锁
            String val = serializer.deserialize(byteValues);
            logger.debug("redis加锁getLockSimple 获取锁成功:key={},expiredTime={},value={}", key, expiredTime, val);
            return val;
        });
    }

    /**
     * 释放锁
     *
     * @param key key
     * @param value setNX的值
     * @return 成功返回true,失败返回false
     * @deprecated 请使用releaseLock
     */
    @Deprecated
    public Boolean unLock(final String key, final String value) {

        boolean flag = false;
        try {
            flag = value.equals(redis.opsForValue().get(key));
            if (!flag) {
                return false;
            }
            redis.delete(key);
        } catch (Exception e) {
            logger.info("从redis释放锁异常:key={},value={}", key, value, e);
            return false;
        }

        return true;
    }

    /**
     * 获取计数信号量，用于处理限制并发访问同一资源的客户端数量
     *
     * @param key
     * @param limit 允许并发访问总量
     * @return
     */
    public boolean getSemaphore(final String key, final int limit) {
        return getSemaphore(key, limit, 2000);
    }

    /**
     * 获取计数信号量，用于处理限制并发访问同一资源的客户端数量
     *
     * @param key
     * @param limit 允许并发访问总量
     * @return
     */
    public Boolean getSemaphore(final String key, final int limit, final long expiredTime) {
        return redis.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redis.getStringSerializer();
            // 返回是否获取到计数量
            boolean result = false;

            // 获取锁
            String lock = "semaphore_lock";
            if (getLock(lock, expiredTime)) {
                // 序列化
                byte[] byteKeys = serializer.serialize(key.toString());
                byte[] byteValues = serializer.serialize(UUID.randomUUID().toString());

                // 计时器
                byte[] countByteKeys = serializer.serialize(key + "_count");
                long count = connection.incr(countByteKeys);

                logger.debug(System.currentTimeMillis() + "_" + count);
                // 添加有序集合元素
                connection.zAdd(byteKeys, count, byteValues);
                long rank = connection.zRank(byteKeys, byteValues);
                // zset排名是从0开始
                if (rank <= limit - 1) {
                    result = true;
                } else {
                    // 如果排名超过总计数量，删除添加元素
                    connection.zRem(byteKeys, byteValues);
                }
                logger.debug(result + "_" + count);

                // 释放锁
                redisDelete(lock);
            }

            return result;
        });
    }

    /**
     * 添加对象并同时设置失效时间
     */
    public boolean redisSetWithExpires(final String key, final Long expires, final String value) {
        redis.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(redis.getStringSerializer().serialize(key), expires,
                        redis.getStringSerializer().serialize(value));
                return true;
            }
        });
        return false;
    }

    /**
     * 发布消息
     *
     * @param channel
     * @param message
     */
    public boolean publisher(String channel, String message) {

        try {

            redis.convertAndSend(channel, message);

            return true;

        } catch (Exception e) {
            logger.info("消息发布失败,channel={},message={}", channel, message, e);
        }

        return false;
    }

    /**
     * redisFireWall
     *
     * @param key key
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return
     */
    public boolean redisFireWall(String key, long timeout, TimeUnit unit) {
        String value = redisGet(key);
        if (StringUtils.isNotBlank(value)) {
            return true;
        } else {
            redisSet(key, "1", timeout, unit);
        }
        return false;
    }

    /**
     * 分布式锁
     *
     * @param key 锁key
     * @param timeoutSeconds 超时时间，单位s
     * @return 如果获取锁成功，返回设置到该key的值(用于释放锁)，获取锁失败返回<code>null</code>
     */
    public Object getAtomLock(String key, long timeoutSeconds) {

        try {
            String value = UUID.randomUUID().toString();
            boolean lockStat = redis.execute((RedisCallback<Boolean>) connection -> {
                connection.set(key.getBytes(Charset.forName("UTF-8")), value.getBytes(Charset.forName("UTF-8")),
                        Expiration.milliseconds(timeoutSeconds * 1000), RedisStringCommands.SetOption.SET_IF_ABSENT);
                return true;
            });

            if (lockStat) {
                return value;
            }

        } catch (Exception ex) {
            logger.error("获取锁失败:key={}", key, ex);
        }
        return null;
    }

    /**
     * 分布式锁，可能引起cpu升高
     *
     * @param key 锁key
     * @param timeoutSeconds 超时时间，单位s
     * @param waitTime 等待获取锁时间，单位s，默认5s
     * @return 如果获取锁成功，返回设置到该key的值(用于释放锁)，获取锁失败返回<code>null</code>
     */
    public String getAtomLock(String key, long timeoutSeconds, long waitTime) {
        if (waitTime < 1) {
            waitTime = DEFAULT_WAIT_TIME;
        }
        long waitTimeSecond = waitTime * 1000;
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < waitTimeSecond) {
            Object lockValue = getAtomLock(key, timeoutSeconds);
            if (null != lockValue) {
                // 上锁成功结束请求
                return lockValue.toString();
            }
            /* 随机延迟 */
            try {
                Thread.sleep(random.nextInt(GET_LOCK_RANDOM_SLEEP_TIME));
            } catch (InterruptedException e) {
                logger.warn("获取锁等待中断:key={}", key, e);
            }
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param key 锁key
     * @param value 锁value 来自getAtomLock()
     */
    public void releaseLock(String key, String value) {
        // 在releaseLock前，存在由于锁自动过期，而被其他线程获取同一个锁的可能
        try {
            boolean unLockStat = redis.execute((RedisCallback<Boolean>) connection -> {
                //结果为1释放成功
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                return connection.eval(script.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(Charset.forName("UTF-8")),
                        value.getBytes(Charset.forName("UTF-8")));
            });

            if (!unLockStat) {
                logger.error("释放锁失败,msg={}", key + "已自动超时,可能已被其他线程重新获取锁");
            }

        } catch (Exception ex) {
            logger.error("释放锁失败,key={}", key, ex);
        }
    }

    //Zset 根据 socre 排序   不重复 每个元素附加一个 socre  double类型的属性(double 可以重复)
    /**
     * 添加 ZSet 元素
     *
     * @param key redis key
     * @param value redis value
     * @param score 打分
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Boolean zSetAdd(String key, Object value, double score) {
        return zSetAdd(key, JSON.toJSONString(value), score);
    }

    /**
     * 添加 ZSet 元素
     *
     * @param key redis key
     * @param value redis value
     * @param score 打分
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Boolean zSetAdd(String key, String value, double score) {
        return redis.opsForZSet().add(key, value, score);
    }

    /**
     * 批量添加 Zset <br>
     * Set<TypedTuple<String>> tuples = new HashSet<>();<br>
     * TypedTuple<String> objectTypedTuple1 = new
     * DefaultTypedTuple<String>("zset-5",9.6);<br>
     * tuples.add(objectTypedTuple1);
     *
     * @param key
     * @param tuples
     * @return
     */
    public Long batchAddZset(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return redis.opsForZSet().add(key, tuples);
    }

    /**
     * Zset 删除一个或多个元素
     *
     * @param key
     * @param values
     * @return
     */
    public Long removeZset(String key, String... values) {
        return redis.opsForZSet().remove(key, values);
    }

    /**
     * 对指定的 zset 的 value 值 , socre 属性做增减操作
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Double incrementScore(String key, Object value, double score) {
        return incrementScore(key, JSON.toJSONString(value), score);
    }

    /**
     * 对指定的 zset 的 value 值 , socre 属性做增减操作
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Double incrementScore(String key, String value, double score) {
        return redis.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 获取 key 中指定 value 的排名(从0开始,从小到大排序)
     *
     * @param key
     * @param value
     * @return
     */
    public Long zSetRank(String key, Object value) {
        return zSetRank(key, JSON.toJSONString(value));
    }

    /**
     * 获取 key 中指定 value 的排名(从0开始,从小到大排序)
     *
     * @param key
     * @param value
     * @return
     */
    public Long zSetRank(String key, String value) {
        return redis.opsForZSet().rank(key, value);
    }

    /**
     * 获取 key 中指定 value 的排名(从0开始,从大到小排序)
     *
     * @param key
     * @param value
     * @return
     */
    public Long reverseRank(String key, Object value) {
        return reverseRank(key, JSON.toJSONString(value));
    }

    /**
     * 获取 key 中指定 value 的排名(从0开始,从大到小排序)
     *
     * @param key
     * @param value
     * @return
     */
    public Long reverseRank(String key, String value) {
        return redis.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取索引区间内的排序结果集合(从0开始,从小到大,带上分数)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeWithScores(String key, long start, long end) {
        return redis.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 获取索引区间内的排序结果集合(从0开始,从小到大,只有列名)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> range(String key, long start, long end) {
        return redis.opsForZSet().range(key, start, end);
    }

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,只有列名)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> rangeByScore(String key, double min, double max) {
        return redis.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,集合带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, double min, double max) {
        return redis.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从小到大,不带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return
     */
    public Set<String> rangeByScore(String key, double min, double max, long offset, long count) {
        return redis.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从小到大,带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, double min, double max,
                                                                         long offset, long count) {
        return redis.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 获取索引区间内的排序结果集合(从0开始,从大到小,只有列名)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> reverseRange(String key, long start, long end) {
        return redis.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取索引区间内的排序结果集合(从0开始,从大到小,带上分数)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String key, long start, long end) {
        return redis.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合不带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> reverseRangeByScore(String key, double min, double max) {
        return redis.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return redis.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从大到小,不带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return
     */
    public Set<String> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return redis.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从大到小,带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(String key, double min, double max,
                                                                                long offset, long count) {
        return redis.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 返回指定分数区间 [min,max] 的元素个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long countZSet(String key, double min, double max) {
        return redis.opsForZSet().count(key, min, max);
    }

    /**
     * 返回 zset 集合数量
     *
     * @param key
     * @return
     */
    public Long sizeZset(String key) {
        return redis.opsForZSet().size(key);
    }

    /**
     * 获取指定成员的 score 值
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, Object value) {
        return score(key, JSON.toJSONString(value));
    }

    /**
     * 获取指定成员的 score 值
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, String value) {
        return redis.opsForZSet().score(key, value);
    }

    /**
     * 删除指定索引位置的成员,其中成员分数按( 从小到大 )
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long removeRange(String key, long start, long end) {
        return redis.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 删除指定 分数范围 内的成员 [main,max],其中成员分数按( 从小到大 )
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long removeRangeByScore(String key, double min, double max) {
        return redis.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * key 和 other 两个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long unionAndStoreZset(String key, String otherKey, String destKey) {
        return redis.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key 和 otherKeys 多个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long unionAndStoreZset(String key, Collection<String> otherKeys, String destKey) {
        return redis.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * key 和 otherKey 两个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return redis.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * key 和 otherKeys 多个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redis.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }
}
