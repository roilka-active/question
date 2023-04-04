package training.design;

/**
 * @author zhanghui
 * @description 单例模式
 * @date 2020/6/14
 */
public class Mgr01 {

    private static volatile Mgr01 INSTANCELAZY;

    private static volatile Mgr01 INSTANCEEARLY = new Mgr01();

    /**
     *  饿汉式
     * @return
     */
    public static Mgr01 getInstanceEarly(){
        return INSTANCEEARLY;
    }

    /**
     * 简单懒汉式
     * @return
     */
    public static synchronized Mgr01 getInstanceLazyEasy(){
        if (INSTANCELAZY == null){
            INSTANCELAZY = new Mgr01();
        }
        return INSTANCELAZY;
    }

    /**
     *  双重检验锁
     * @return
     */
    public static Mgr01 getInstanceLazy() {
        if (INSTANCELAZY == null) {
            synchronized (Mgr01.class) {
            }
            if (INSTANCELAZY == null) {
                INSTANCELAZY = new Mgr01();
            }
        }
        return INSTANCELAZY;
    }


}
