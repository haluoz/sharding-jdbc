package com.lxj.shardingjdbc.controller;

import com.lxj.shardingjdbc.dao.OrderMapper;
import com.lxj.shardingjdbc.log.LogRecord;
import com.lxj.shardingjdbc.log.LogRecordContext;
import com.lxj.shardingjdbc.model.Order;
import com.lxj.shardingjdbc.mq.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Xingjing.Li
 * @since 2021/11/30
 */
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Producer producer;

    @LogRecord(bizNo = "", success = "'修改成功，用户：' + #operator + '修改订单id：' + #reqOrder.id +'，修改订单金额' + #reqOrder.orderAmount")
    @GetMapping("/modifyAmount")
    public String modifyOrderAmount(Order reqOrder){
        String orderId = restTemplate.getForEntity("http://localhost:8080/exception?id=3", String.class).getBody();
        Order order = new Order();
        order.setId(Integer.parseInt(orderId));
        order.setOrderAmount(559L);
        order.setOrderStatus(1);
        orderMapper.updateByPrimaryKeySelective(order);
        LogRecordContext.putVariable("operator", "wdnmd");
//        Order order = orderMapper.selectByPrimaryKey(reqOrder.getId());
//        String id = exception(reqOrder);
        return order.getId().toString();
    }

    @LogRecord(bizNo = "", success = "'修改成功，用户：' + #operator + '查询订单id：' + #reqOrder.id")
    @GetMapping("/exception")
    public String exception(Order reqOrder){
        LogRecordContext.putVariable("operator", "zzc");
        Order order = orderMapper.selectByPrimaryKey(reqOrder.getId());
//        throw new RuntimeException("123");
        return order.getId().toString();
    }

    @PostMapping("/startExam")
    public Response startExam(@RequestBody Task task){
        log.info(task.getTaskStatus()+"----"+task.getTaskUserId());
        return Response.success();
    }

    @GetMapping("send")
    public void send() {
        log.info("sending message");
        producer.outputStartExamCallBack().send(MessageBuilder.withPayload("Hello World...").build());
    }
}

class Task{
    private String taskStatus;
    private String taskUserId;

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId;
    }
}


