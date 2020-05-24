package com.yueyang.rabbitmq.sixin;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yueyang.rabbitmq.utils.HttpClientUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: queueproject
 * @description: email消息消费者
 * @author: qinxiangyang
 * @create: 2020-05-24 10:55
 **/
@Component
public class DeadConsumer {


    @RabbitListener(queues = "dead_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "utf-8");

        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);


        System.out.println("死信消息消费结束，result" + "程序结束");
    }
}