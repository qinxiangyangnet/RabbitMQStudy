package com.yueyang.other.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: queueproject
 * @description:
 * @author: qinxiangyang
 * @create: 2020-05-24 11:49
 **/
@RestController
public class MailController {


    @RequestMapping("/sendEmail")
    public Map<String, Object> sendMessage(String email) {
        System.out.println("开始发邮件。。。。。" + email);
        Map<String, Object> map = new HashMap<>();

        map.put("code", "200");
        map.put("msg", "发送邮件成功");
        System.out.println("发送邮件成功");

        return map;
    }
}