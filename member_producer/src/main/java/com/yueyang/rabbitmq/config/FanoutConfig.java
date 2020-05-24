package com.yueyang.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:32
 **/
@Component
public class FanoutConfig {

    private String FANOUT_EMAIL_QUEUE = "new_fanout_email_queue";
    private String FANOUT_SMS_QUEUE = "new_fanout_sms_queue";
    private String EXCHANGE_NAME = "fanout_exchange";

    //定义队列


    /**
     * //邮件队列
     *
     * @return
     */
    @Bean
    public Queue fanoutEmailQueue() {
        return new Queue(FANOUT_EMAIL_QUEUE);
    }


    /**
     * //sms队列
     *
     * @return
     */
    @Bean
    public Queue fanoutSmsQueue() {
        return new Queue(FANOUT_SMS_QUEUE);
    }


    //定义交换机

    /**
     * 定义Fanout交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    /**
     * 队列和交换机进行绑定
     */
    @Bean
    public Binding bindingSms(Queue fanoutSmsQueue, FanoutExchange fanoutExchange) {

        return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingEmail(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {

        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }
    /**
     *     //定义topic交换机
     *    public TopicExchange fanoutExchange() {
     *        return new TopicExchange(EXCHANGE_NAME);
     *     }
     */


}