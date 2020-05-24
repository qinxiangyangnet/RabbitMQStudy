package com.yueyang.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yueyang.base.BaseApiService;
import com.yueyang.base.ResponseBase;
import com.yueyang.entity.OrderEntity;
import com.yueyang.mapper.OrderMapper;

@Service
public class OrderService extends BaseApiService implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ResponseBase addOrderAndDispatch() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName("蚂蚁课堂永久会员充值");
        orderEntity.setOrderCreatetime(new Date());
        // 价格是300元
        orderEntity.setOrderMoney(300d);
        // 状态为 未支付
        orderEntity.setOrderState(0);
        Long commodityId = 30l;
        // 商品id
        orderEntity.setCommodityId(commodityId);
        String orderId = UUID.randomUUID().toString();
        orderEntity.setOrderId(orderId);

        // ##################################################
        // 1.先下单，创建订单 (往订单数据库中插入一条数据)
        int orderResult = orderMapper.addOrder(orderEntity);
        System.out.println("orderResult:" + orderResult);
        if (orderResult <= 0) {
            return setResultError("下单失败!");
        }
        // 2.使用消息中间件将参数存在派单队列中
        send(orderId);
        return setResultSuccess();
    }

    private void send(String orderId) {
        JSONObject jsonObect = new JSONObject();
        jsonObect.put("orderId", orderId);
        String msg = jsonObect.toJSONString();
        System.out.println("msg:" + msg);
        // 封装消息
        Message message = MessageBuilder.withBody(msg.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8").setMessageId(orderId).build();


        //构建回调返回的数据
        CorrelationData correlationData = new CorrelationData(orderId);

        // 发送消息
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);

        rabbitTemplate.convertAndSend("order_exchange_name", "orderRoutingKey", message, correlationData);

    }


    // 生产消息确认机制,生产者向服务器端发送消息，采用应答机制，correlationData 订单号全局Id
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String orderId = correlationData.getId();
        System.out.println("消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("消息发送确认成功");
        } else {
            //采用重试机制
            send(orderId);
            System.out.println("消息发送确认失败:" + cause);
        }

    }


}
