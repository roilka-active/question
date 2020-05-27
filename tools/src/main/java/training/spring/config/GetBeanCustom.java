package training.spring.config;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @ClassName GetBeanCustom
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/16 11:06
 **/
public class GetBeanCustom {
    public static void main(String[] args) {
        GenericApplicationContext ac = new GenericApplicationContext();
        Object o = new Object();
        o.hashCode();
        o.equals(null);
    }
}
