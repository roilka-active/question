package com.roilka.roilka.question.common.constant;

import com.roilka.roilka.question.common.utils.RedisUtils;

/**
 * @ClassName RedisFix
 * @Description TODO
 * @Author changyou
 * @Date 2019/12/6 20:21
 **/
public interface RedisFix {

    /**
     *  缓存全部
     */
    String REDIS_PREFIX = RedisUtils.initInstance().getRedisPrefix();

    String AREA = REDIS_PREFIX + "area:";

    String ACECDOTE = REDIS_PREFIX + "acecdote:";

    String HISTORYTODAY = ACECDOTE + "historytoday:";

}
