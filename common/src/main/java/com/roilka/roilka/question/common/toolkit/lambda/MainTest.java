package com.roilka.roilka.question.common.toolkit.lambda;/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2920:24
 */

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @ClassName MainTest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/29 20:24
 **/
public class MainTest {
    public static void main(String[] args) {
        // Predicate : 参数：T，返回值：boolean
        Predicate<Integer> predicate = (i) -> i > 5;
        // Consumer : 参数：T，返回值：void
        Consumer consumer = (s) -> System.out.println(s);
        // Supplier: 参数：无，返回值：T
        Supplier<Boolean> supplier = () -> {
            return true;
        };
    }
}
