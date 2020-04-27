### 高并发场景下System.currentTimeMillis()的性能问题的优化

 - System.currentTimeMillis()的调用比new一个普通对象要耗时的多（具体耗时高出多少我还没测试过，有人说是100倍左右）<br>
 - System.currentTimeMillis()之所以慢是因为去跟系统打了一次交道<br>
 - 后台定时更新时钟，JVM退出时，线程自动回收<br>
 - 10亿：43410,206,210.72815533980582%<br>
 - 1亿：4699,29,162.0344827586207%<br>
 - 1000万：480,12,40.0%<br>
 - 100万：50,10,5.0%<br>

 具体代码如下
 ~~~
 public class SystemClock {
     private final long period;
     private final AtomicLong now;
 
     private SystemClock(long period) {
         this.period = period;
         this.now = new AtomicLong(System.currentTimeMillis());
         scheduleClockUpdating();
     }
 
     private static SystemClock instance(){
         return InstanceHolder.INSTANCE;
     }
     public static String nowDate() {
         return new Timestamp(instance().currentTimeMillis()).toString();
     }
     /*
       开启一个定时线程，
     */
     private void scheduleClockUpdating() {
         ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
             Thread thread = new Thread(runnable, "System Clock");
             thread.setDaemon(true);
             return thread;
         });
         scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), period, period, TimeUnit.MILLISECONDS);
     }
 
     private long currentTimeMillis() {
         return now.get();
     }
     
     private static class InstanceHolder {
 
         public static final SystemClock INSTANCE = new SystemClock(1);
     }
 }
 ~~~
 
 - 总结：原理类似于缓存机制，就是用一个守护线程不断的获取系统当前时间，存入原子性Long对象中，获取的时候直接取，减少与OS的交互，
 适应于高并发，大数据量场景。