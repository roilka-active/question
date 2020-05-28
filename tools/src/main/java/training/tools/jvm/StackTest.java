package training.tools.jvm;/**
 * Package: training.tools.jvm
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/4/12 16:40
 * <p>
 * Company: roilka
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description 方法栈测试
 * @date 2020/4/12
 */
public class StackTest {
    private static int count = 0;
    public static void recursion(){
        count++;
    }

    public static void main(String[] args) {
        try {
            recursion();
        }catch (Exception ex){
            System.out.println("deep of celling ="+ count);
            ex.printStackTrace();
        }
    }
}
