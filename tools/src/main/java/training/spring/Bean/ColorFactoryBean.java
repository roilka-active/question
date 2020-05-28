package training.spring.Bean;/**
 * Package: training.spring.Bean
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/5/29 0:13
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhanghui
 * @description
 * @date 2020/5/29
 */
public class ColorFactoryBean  implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...getObject");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }
}
