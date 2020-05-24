package com.yueyang.rabbitmq.email;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.yueyang.rabbitmq.utils.HttpClientUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
//@RabbitListener(queues = "new_fanout_email_queue")
public class EmailConsumer {


    //    @RabbitHandler()
    @RabbitListener(queues = "new_fanout_email_queue")
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "utf-8");
        System.out.println("messageId:" + messageId);

//        if(messageId has  consumer){
        //伪代码
//            return;
//        }
        JSONObject jsonObject = JSONObject.parseObject(msg);
        System.out.println("短信消费者获取生产者生成的消息:" + jsonObject.getString("msg"));

        String email = jsonObject.getString("email");
        String emailUrl = "http://localhost:8082/sendEmail?email=" + email;
        JSONObject retult = HttpClientUtils.httpGet(emailUrl);
        //如果调用第三方接口，怎么补偿
        if (retult == null) {
            throw new Exception("调用第三方接口异常");
        }

        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收
        channel.basicAck(deliveryTag, false);


        System.out.println("调用第三方成功，result" + retult + "程序结束");
    }
}