package training.tools.spring.ioc.humen;/**
 * Package: training.tools.spring.ioc.humen
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:59
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import training.tools.spring.ioc.car.Car;

/**
 * @author zhanghui
 * @description
 * @date 2019/9/25
 */
public abstract class HumenWithCar implements Humen{

    public Car car;
    public  HumenWithCar(Car car){
        this.car = car;
    }

    @Override
    public abstract void goHome();
}
