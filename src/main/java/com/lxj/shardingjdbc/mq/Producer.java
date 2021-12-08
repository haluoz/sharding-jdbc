package com.lxj.shardingjdbc.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Xingjing.Li
 * @since 2021/12/8
 */
public interface Producer {

    String OUTPUT_START_EXAM_CALLBACK = "outputStartExamCallBack";

    @Output(Producer.OUTPUT_START_EXAM_CALLBACK)
    MessageChannel outputStartExamCallBack();
}
