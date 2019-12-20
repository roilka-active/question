package com.roilka.roilka.question.common.concurrency.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName LongEventHandler
 * @Description 消费者
 * @Author changyou
 * @Date 2019/12/11 20:21
 **/
@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        log.info("event:{}", longEvent);
    }
}
