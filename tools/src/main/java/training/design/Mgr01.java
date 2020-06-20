package training.design;

/**
 * @author zhanghui
 * @description 单例模式
 * @date 2020/6/14
 */
public class Mgr01 {

    private static volatile Mgr01 INSTANCE = null;

    public static Mgr01 getInstance() {
        if (INSTANCE == null) {
            synchronized (Mgr01.class) {
            }
            if (INSTANCE == null) {
                INSTANCE = new Mgr01();
            }
        }
        return INSTANCE;
    }
}
