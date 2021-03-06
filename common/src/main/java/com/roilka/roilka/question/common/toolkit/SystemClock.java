package com.roilka.roilka.question.common.toolkit;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName SystemClock
 * @Description 高并发场景下System.currentTimeMillis()的性能问题的优化
 * @Author 75six
 * @Date 2020/4/27 18:31
 **/
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
