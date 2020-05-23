package com.yueyang.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @program: RabbitMQStudy
 * @description: 连接创建
 * @author: qinxiangyang
 * @create: 2020-05-24 06:19
 **/
public class RabbitMQUtils {
    private RabbitMQUtils() {
    }


    public static Connection getConnection() throws Throwable {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setConnectionTimeout(1000);
        //设置用户名和密码
        connectionFactory.setUsername("qinxiangyangadmin");
        connectionFactory.setPassword("123456");

        //设置主机
        connectionFactory.setHost("127.0.0.1");
        //设置aqmp协议端口
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/admin_host");


        return connectionFactory.newConnection();

    }
}