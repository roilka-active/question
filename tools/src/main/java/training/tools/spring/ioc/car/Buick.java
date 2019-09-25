package training.tools.spring.ioc.car;/**
 * Package: training.tools.spring.ioc.car
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:54
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description
 * @date 2019/9/25
 */
public class Buick implements Car {
    @Override
    public void start() {
        System.out.println("Buick.start");
    }

    @Override
    public void turnLeft() {
        System.out.println("Buick.turnLeft");
    }

    @Override
    public void turnRight() {
        System.out.println("Buick.turnRight");
    }

    @Override
    public void stop() {
        System.out.println("Buick.stop");
    }
}
