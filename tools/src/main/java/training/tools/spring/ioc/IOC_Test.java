package training.tools.spring.ioc;
/**
 * Package: training.tools.spring.ioc
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/17 23:34
 * <p>
 * Modified By:
 */

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import training.tools.spring.ioc.color.Blue;

/**
 * @author zhanghui
 * @description
 * @date 2020/6/17
 */
public class IOC_Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        Blue bean = applicationContext.getBean(Blue.class);
        applicationContext.refresh();

    }

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();


        applicationContext.refresh();
    }
}
