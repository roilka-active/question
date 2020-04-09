package training.tools.javase.proxy.annotation;

/**
 * @ClassName Apple
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/9 17:23
 **/
public class Apple {

    @FruitProvider(id = 1,name = "瑞幸咖啡", address = "美国 纳斯达克")
    private String appleProvider;

    public String getAppleProvider() {
        return appleProvider;
    }

    public void setAppleProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }
}
