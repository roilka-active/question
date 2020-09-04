package com.roilka.roilka.question.common.toolkit.lambda;/**
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @author zhanghui1
 * @date 2020/5/2920:24
 */

import com.roilka.roilka.question.common.demo.guava.entity.Person;
import com.roilka.roilka.question.common.toolkit.lambda.dataobject.*;

import java.util.*;
import java.util.function.*;

/**
 * @ClassName MainTest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/5/29 20:24
 **/
public class MainTest {
    public static void main(String[] args) {

    }

    /**
     * 测试函数函数式接口
     */
    public void testInterface() {
        HasParamHasReturn hasParamHasReturn = (i, j) -> {
            return i;
        };
        hasParamHasReturn.count(1, 2);
        Object o = new Object();
        HasParamNoReturn hasParamNoReturn = (m) -> System.out.println(m);
        hasParamNoReturn.sayMessage("hello everyone!");
        NoParamHashReturn noParamHashReturn = () -> "are you ok?";
        noParamHashReturn.get();
        NoParamNoReturn noParamNoReturn = () -> System.out.println("1,2,3 木头人");
        noParamNoReturn.publish();
        // Predicate : 参数：T，返回值：boolean
        Predicate<Integer> predicate = (i) -> i > 5;
        // 可以利用默认方法，进行组合连接
        predicate = predicate.and(i -> i % 2 == 0)
                .or(i -> i / 3 == 3)
                .negate();
        System.out.println(predicate.test(9));

        // Consumer : 参数：T，返回值：void
        Consumer<String> consumer = System.out::println;

        consumer = consumer.andThen(s -> System.out.println("after:" + s));
        consumer.accept("Java");
        // Supplier: 参数：无，返回值：T
        Supplier<Boolean> supplier = () -> true;

        UnaryOperator<Integer> unaryOperator = (s) -> s++;
        BiFunction biFunction = Object::equals;
        Predicate<Integer> predicate1 = (s) -> s > 1;
        Function<Integer, Boolean> function = (m) -> m < 0;
        Function<Boolean, Integer> function1 = m -> m ? 1 : 2;
        // andThen 是
        function.andThen(function1);
        function.compose(function1);
    }

    /**
     * 测试方法引用
     */
    public void testReferance() {
        // 静态方法引用
        Consumer consumer = System.out::println;
        // 等价于：  consumer = s -> System.out.println(s);

        // 对象调用
        Object o = new Object();
        Supplier<Integer> supplier = o::hashCode;
        // 等价于： supplier = () -> o.hashCode();
        //或者是
        supplier = new Object()::hashCode;
        // 等价于： supplier = () -> new Object().hashCode();

        // 父类或本类方法引用
        supplier = super::hashCode;
        //等价于： supplier = () -> super.hashCode();
        supplier = this::hashCode;
        // 等价于： supplier = () -> this.hashCode();

        // 通过类型上的实例方法引用
        String[] arr = new String[2];
        Arrays.sort(arr,String::compareTo);
        // 构造方法引用
        Supplier<Person> supplier1 = Person::new;
        // 等价于：supplier1 = () -> new Person();
        // 数组构造方法引用
        HasParamHasReturn1 supplier2 = String[]::new;
        // 等价于：supplier2 = i -> new String[i];

    }

    public interface HasParamHasReturn1 {
        String[] count(Integer t1);

         default int sizet(String s) {
            return 1;
        }
    }

    public static int change(int a) {
        return a + 2;
    }
}
