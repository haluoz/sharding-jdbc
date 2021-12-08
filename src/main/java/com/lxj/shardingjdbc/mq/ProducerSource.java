package com.lxj.shardingjdbc.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
@Component
@EnableBinding(value = {Producer.class})
public class ProducerSource {

}
