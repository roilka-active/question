package training.tools.spring.ioc.humen;/**
 * Package: training.tools.spring.ioc.humen
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 23:01
 * <p>
 * Company: roilka
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
public class ZhangSan extends HumenWithCar{
    public ZhangSan(Car car){
        super(car);
    }

    @Override
    public void goHome(){
        car.start();
        car.turnLeft();
        car.turnRight();
        car.stop();
    }
}
