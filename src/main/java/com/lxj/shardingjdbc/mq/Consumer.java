package com.lxj.shardingjdbc.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
public interface Consumer {
    String INPUT_START_EXAM_CALLBACK = "inputStartExamCallBack";

    @Input(INPUT_START_EXAM_CALLBACK)
    SubscribableChannel inputStartExamCallBack();
}
