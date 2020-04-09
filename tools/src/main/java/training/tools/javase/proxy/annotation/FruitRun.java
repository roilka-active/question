package training.tools.javase.proxy.annotation;

/**
 * @ClassName FruitRun
 * @Description 注解测试类
 * @Author zhanghui1
 * @Date 2020/4/9 17:26
 **/
public class FruitRun {

    public static void main(String[] args) {
        FruitInfoUtil.getFruitInfo(Apple.class);
    }
}
