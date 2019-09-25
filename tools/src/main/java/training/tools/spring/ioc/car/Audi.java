package training.tools.spring.ioc.car;/**
 * Package: training.tools.spring.ioc.car
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/25 22:52
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
public class Audi implements Car{
    @Override
    public void start() {
        System.out.println("Audi.start");
    }

    @Override
    public void turnLeft() {
        System.out.println("Audi.turnLeft");
    }

    @Override
    public void turnRight() {
        System.out.println("Audi.turnRight");
    }

    @Override
    public void stop() {
        System.out.println("Audi.stop");
    }
}
