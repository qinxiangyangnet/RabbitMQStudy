package com.yueyang.rabbitmq.handler.facout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yueyang.rabbitmq.utils.RabbitMQUtils;

/**
 * @program: RabbitMQStudy
 * @description: 生产者
 * @author: qinxiangyang
 * @create: 2020-05-24 06:26
 **/
public class FanoutProducer {

    private final static String EXCHANGE_NAME = "qinxiangyang_123456";

    public static void main(String[] args) throws Throwable {
        System.out.println("生产者开始创建消息");
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //绑定交换机,参数1 为交换机的名称，参数2为交换机的类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


        String msg = "msg:  "+123456;
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("生产者发送消息" + msg);


        //关闭连接
        channel.close();
        connection.close();
    }
}