package training.tools.javase;/**
 * Package: training.tools.algorithm
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/9/20 23:11
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description 管理员
 * @date 2019/9/20
 */
public class Manager extends Employee {

    private int code;

    public Manager(int code, double salary) {
        super(salary);
        System.out.println(" Manager is Init !");
        code = 12;
    }

    public void setSalary(double salary) {
        System.out.println("我是子类");
    }

    public Manager getCurrent() {
        return this;
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (!(getClass() == otherObject.getClass())) {
            return false;
        }
        if (!(otherObject instanceof Employee)) {
            return false;
        }
        if (!(otherObject instanceof Manager)) {
            return false;
        }

        Manager manager = (Manager) otherObject;
        return this.code == manager.code ;
    }
}
