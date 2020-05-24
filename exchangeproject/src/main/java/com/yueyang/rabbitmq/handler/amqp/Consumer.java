package com.yueyang.rabbitmq.handler.amqp;

import com.rabbitmq.client.*;
import com.yueyang.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @program: RabbitMQStudy
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 06:44
 **/
public class Consumer {


    private final static String QUEUE_NAME = "qinxiangyang_123456";

    public static void main(String[] args) throws Throwable {
        System.out.println("消费者1---开始消费消息");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");
                System.out.println("消费者消费消息:" + msg);

               // channel.basicAck(envelope.getDeliveryTag(), false);

            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);


    }
}