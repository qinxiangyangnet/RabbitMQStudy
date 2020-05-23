package com.yueyang.rabbitmq.handler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yueyang.rabbitmq.utils.RabbitMQUtils;

/**
 * @program: RabbitMQStudy
 * @description: 生产者
 * @author: qinxiangyang
 * @create: 2020-05-24 06:26
 **/
public class Producer {

    private final static String QUEUE_NAME = "qinxiangyang_123456";

    public static void main(String[] args) throws Throwable {
        System.out.println("生产者开始创建消息");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        for (int i = 0; i < 50; i++) {
            String msg = "msg:  " + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("生产者生成发送消息：" + msg);
        }


        //关闭连接
        channel.close();
        connection.close();
    }
}