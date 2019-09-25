package training.tools.test;/**
 * Package: training.tools.spring
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:50
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import training.tools.spring.ioc.IocContainer;
import training.tools.spring.ioc.car.Audi;
import training.tools.spring.ioc.car.Buick;
import training.tools.spring.ioc.humen.Humen;
import training.tools.spring.ioc.humen.LiSi;
import training.tools.spring.ioc.humen.ZhangSan;

/**
 * @author zhanghui
 * @description
 * @date 2019/9/25
 */
public class IocTest {
    private IocContainer iocContainer = new IocContainer();

    /*@Before
    public  void before(){

        iocContainer.setBean(Audi.class,"audi");
        iocContainer.setBean(Buick.class,"buick");
        iocContainer.setBean(ZhangSan.class,"zhangSan","audi");
        iocContainer.setBean(LiSi.class,"liSi","buick");
    }*/

    /*@Test
    public void test(){
        Humen zhangSan = (Humen) iocContainer.getBean("zhangSan");
        zhangSan.goHome();
        Humen liSi = (Humen) iocContainer.getBean("liSi");
        liSi.goHome();
    }*/
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Bean bean = context.getBean("bean", Bean.class);
        System.out.println("bean=" + bean);
    }

}
