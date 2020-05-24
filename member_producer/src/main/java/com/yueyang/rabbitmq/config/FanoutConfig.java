package com.yueyang.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:32
 **/
@Component
public class FanoutConfig {
    /**
     * 定义死信队列相关信息
     */
    public final static String deadQueueName = "dead_queue";
    public final static String deadRoutingKey = "dead_routing_key";
    public final static String deadExchangeName = "dead_exchange";
    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

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
        //邮件队列绑定死信交换机，死信队列
        Map<String, Object> args = new HashMap<>(2);
        //绑定私信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, deadExchangeName);
        args.put(DEAD_LETTER_ROUTING_KEY, deadRoutingKey);
        Queue queue = new Queue(FANOUT_EMAIL_QUEUE, true, false, false, args);
        return queue;

        //return new Queue(FANOUT_EMAIL_QUEUE);
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
     * 配置死信队列
     *
     * @return
     */
    @Bean
    public Queue deadQueue() {
        Queue queue = new Queue(deadQueueName, true);
        return queue;
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(deadExchangeName);
    }

    @Bean
    public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(deadRoutingKey);
    }

    /**
     *     //定义topic交换机
     *    public TopicExchange fanoutExchange() {
     *        return new TopicExchange(EXCHANGE_NAME);
     *     }
     */


}