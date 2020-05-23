package com.yueyang.rabbitmq.handler;

import com.rabbitmq.client.*;
import com.yueyang.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @program: RabbitMQStudy
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 06:44
 **/
public class Consumer2 {


    private final static String QUEUE_NAME = "qinxiangyang_123456";

    public static void main(String[] args) throws Throwable {
        System.out.println("消费者2---开始消费消息");
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
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);


    }
}