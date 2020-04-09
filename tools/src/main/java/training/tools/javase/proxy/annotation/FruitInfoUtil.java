package training.tools.javase.proxy.annotation;

import java.lang.reflect.Field;

/**
 * @ClassName FruitInfoUtil
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/9 17:25
 **/
public class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz) {
        String strFruitProvicer = "上市公司信息：";
        Field[] fields = clazz.getDeclaredFields();// 通过反射获取处理注解
        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = (FruitProvider) field.getAnnotation(FruitProvider.class);
                // 注解信息的处理地方
                strFruitProvicer = "公司编号：" + fruitProvider.id() + "公司名称名称："
                        + fruitProvider.name() + " 公司上市地址：" + fruitProvider.address();
                System.out.println(strFruitProvicer);
            }
        }
    }
}
