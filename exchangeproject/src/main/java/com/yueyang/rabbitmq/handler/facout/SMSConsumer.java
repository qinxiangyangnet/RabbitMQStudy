package com.yueyang.rabbitmq.handler.facout;

import com.rabbitmq.client.*;
import com.yueyang.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @program: queueproject
 * @description: 短信消费者
 * @author: qinxiangyang
 * @create: 2020-05-24 07:23
 **/
public class SMSConsumer {
    private final static String EXCHANGE_NAME = "qinxiangyang_123456";

    private final static String QUEUE_NAME = "sms_queue";

    public static void main(String[] args) throws Throwable {
        System.out.println("sms开始消费消息");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body, "utf-8");
                System.out.println(QUEUE_NAME + "  " + s);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}