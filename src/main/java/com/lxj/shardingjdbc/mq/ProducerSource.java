package com.lxj.shardingjdbc.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
@Component
@EnableBinding(value = {Producer.class})
@Slf4j
public class ProducerSource {
    @Autowired
    private Producer producer;

    public void outputStartExamCallBack(String message) {
        log.info("sending message");
        producer.outputStartExamCallBack().send(MessageBuilder.withPayload("Hello World...").build());
    }
}
