package com.yueyang.rabbitmq.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
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
        String jsonString = jsonObject.toJSONString();
        //发送消息

        //生产者发送消息的时候设置消息Id
        Message message = MessageBuilder
                .withBody(jsonString.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setMessageId(UUID.randomUUID() + "").build();
        amqpTemplate.convertAndSend(queueName, message);

    }


}