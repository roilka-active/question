package com.roilka.roilka.question.api.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhanghui1
 * @ClassName RedisLockable
 * TODO
 * @date 2020/9/10 18:53
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLockable {

    /**
     *
     *  lock switch
     * @author zhanghui1
     * @date 2020/9/10 18:56
     * @return boolean
     */
    boolean enable() default true;

    /**
     *
     * lock key
     * @author zhanghui1
     * @date 2020/9/10 18:59
     * @return java.lang.String
     */
    @AliasFor("key")
    String value() default "";
    @AliasFor("value")
    String key() default "";

    long timeout() default 2;

    String  keyPrefix() default "";
}
