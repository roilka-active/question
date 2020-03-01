package training.tools.lambda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName StreamTest
 * @Author Roilka
 * @Description Stream 流的使用
 * @Date 2020/1/12
 */
@Slf4j
public class StreamTest {

    public static void main(String[] args) {
        StreamTest t = new StreamTest();
        t.sendRedpacket();
    }

    //    @Async("asyncServiceExecutor")
    private void sendRedpacket() {
        int count = 0;
        int total = 0;
        log.info("开始发送红包");

        synchronized (this) {
            try {
                for (int i = 0; i < 10; i++) {
                    total++;
                    log.info("开始发送红包{i}", i);
                    this.sys();
                }
            } catch (Exception e) {

                log.info("红包发放失败", e);

            }
        }

    }

    private void sys() throws Exception {
        while (true) {
            throw new Exception();
        }

    }

}
