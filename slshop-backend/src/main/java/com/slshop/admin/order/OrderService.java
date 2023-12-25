package com.slshop.admin.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slshop.common.entity.order.Order;

@Service
public class OrderService {
	
	private final OrderMapper orderMapper;
	
	@Autowired
	public OrderService(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}
	
	public List<Order> findPerTen(Integer pageNum){
		List<Order> orders = this.orderMapper.findAll();
		List<Order> ordersPerTen = new ArrayList<Order>();
		for(int i = 0; i < 10; i++) {
			try {
				ordersPerTen.add(orders.get(i + (pageNum - 1) * 10));
			}catch(IndexOutOfBoundsException e) {
				break;
			}
		}
		return ordersPerTen;
	}
	
	public int getPageAmount() {
		List<Order> orders = this.orderMapper.findAll();
		if(orders.size() % 10 != 0) {
			return (orders.size() / 10) + 1;
		}
		return orders.size() / 10;
	}
}
