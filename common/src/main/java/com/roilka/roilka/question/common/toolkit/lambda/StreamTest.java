package com.roilka.roilka.question.common.toolkit.lambda;

import lombok.extern.slf4j.Slf4j;
import training.entity.GreetingService;
import training.entity.MathOperate;

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
        MathOperate addition = (int a, int b) -> a + b;
        GreetingService service =(s) -> System.out.println(s);
        service.sayMessage("哈哈");
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
