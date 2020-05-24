package com.yueyang.rabbitmq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:46
 **/
@Component
public class FanoutProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(String queueName) {
        System.out.println("queueName: " + queueName);
        String msg = "msg_" + UUID.randomUUID().toString();
        //发送消息
        amqpTemplate.convertAndSend(queueName, msg);

    }

    public void send(String queueName) {
        System.out.println("queueName: " + queueName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "springF2_qin@163.com");
        jsonObject.put("timstamp", System.currentTimeMillis());
        String msg = "msg_" + UUID.randomUUID().toString();
        jsonObject.put("msg", msg);
        //发送消息
        amqpTemplate.convertAndSend(queueName, jsonObject.toJSONString());

    }


}