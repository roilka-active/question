package training.tools.spring.ioc;/**
 * Package: training.tools.spring.ioc
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:51
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import training.tools.spring.ioc.car.Audi;
import training.tools.spring.ioc.car.Car;
import training.tools.spring.ioc.humen.Humen;
import training.tools.spring.ioc.humen.HumenWithCar;

/**
 * @author zhanghui
 * @description
 * @date 2019/9/25
 */
public class Test {

    public static void main(String[] args) {
        Car car = new Audi();
        HumenWithCar humenWithCar = new HumenWithCar(car) {
            @Override
            public void goHome() {

            }
        };
    }
}
