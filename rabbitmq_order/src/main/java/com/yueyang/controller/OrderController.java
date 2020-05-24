package com.yueyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yueyang.base.BaseApiService;
import com.yueyang.base.ResponseBase;
import com.yueyang.service.OrderService;

@RestController
public class OrderController extends BaseApiService {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/addOrder")
	public ResponseBase addOrder() {
		return orderService.addOrderAndDispatch();
	}

}
