package training.tools.javase.thread.subthread;

/**
 * @ClassName Profiler
 * @Author Roilka
 * @Description 测试当前线程
 * @Date 2020/2/28
 */
public class Profiler {
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        protected Long initValue(){
            return System.currentTimeMillis();
        }
    };
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final Long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
}
