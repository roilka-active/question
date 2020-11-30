package training.jdk.lambda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * @ClassName AsyncTest
 * @Author Roilka
 * @Description ss
 * @Date 2020/2/19
 */
@Slf4j
public class AsyncTest {
    @Async("taskExecutor")
    public void sendRedpacket() {
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
