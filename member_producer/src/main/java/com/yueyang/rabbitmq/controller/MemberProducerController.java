package com.yueyang.rabbitmq.controller;

import com.yueyang.rabbitmq.producer.FanoutProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 10:51
 **/
@RestController
public class MemberProducerController {

    @Autowired
    private FanoutProducer fanoutProducer;


    @RequestMapping("/sendMessage")
    public String sendMessage(String queueName) {
        fanoutProducer.send(queueName);

        return "Success";
    }
}