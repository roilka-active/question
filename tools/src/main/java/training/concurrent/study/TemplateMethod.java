package training.concurrent.study;
/**
 * Package: training.concurrent.study
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2020/6/15 23:29
 * <p>
 * Modified By:
 */

/**
 * @author zhanghui
 * @description
 * @date 2020/6/15
 */
public  class TemplateMethod {
    public final void print(){
        System.out.println("############################################# start");
        wrapPrint("hello");
        System.out.println("############################################# end");
    }
    protected void wrapPrint(String message){

    }

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        objectThreadLocal.set(9);


        TemplateMethod method = new TemplateMethod() {
            @Override
            protected void wrapPrint(String message) {
                System.out.println("*" + message + "*");
            }
        };
        method.print();
    }
}
