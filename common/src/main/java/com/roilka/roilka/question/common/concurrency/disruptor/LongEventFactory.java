package com.roilka.roilka.question.common.concurrency.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @ClassName LongEventFactory
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/11 20:19
 **/
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
