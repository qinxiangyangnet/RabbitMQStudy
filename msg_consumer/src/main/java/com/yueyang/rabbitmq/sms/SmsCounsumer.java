package com.yueyang.rabbitmq.sms;

import com.alibaba.fastjson.JSONObject;
import com.yueyang.rabbitmq.utils.HttpClientUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:58
 **/
@Component
@RabbitListener(queues = "new_fanout_sms_queue")
public class SmsCounsumer {

    /**
     * 如果消费端，消费者业务逻辑出现异常，消息会消费成功吗
     *
     * @param msg
     */
    @RabbitHandler
    public void process(String msg) throws Exception {


        JSONObject jsonObject = JSONObject.parseObject(msg);
        System.out.println("短信消费者获取生产者生成的消息:" + jsonObject.getString("msg"));

        String email = jsonObject.getString("email");
        String emailUrl = "http://localhost:8082/sendEmail?email=" + email;
        JSONObject retult = HttpClientUtils.httpGet(emailUrl);
        //如果调用第三方接口，怎么补偿
        if (retult == null) {
            throw new Exception("调用第三方接口异常");
        }


        System.out.println("调用第三方成功，result" + retult + "程序结束");
    }
}