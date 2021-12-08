package com.lxj.shardingjdbc.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
@Component
@EnableBinding(value = {Consumer.class})
public class ConsumerSource {
    private Logger logger = LoggerFactory.getLogger(ConsumerSource.class);

    @StreamListener(Consumer.INPUT_START_EXAM_CALLBACK)
    public void receive(String message) {
        logger.info("ConsumerSource: {}", message);
    }
}
