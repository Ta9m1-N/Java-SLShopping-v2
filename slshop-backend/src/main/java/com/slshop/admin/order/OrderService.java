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
	
	public int getPageAmount(List<Order> orders) {
		if(orders.size() % 10 != 0) {
			return (orders.size() / 10) + 1;
		}
		return orders.size() / 10;
	}
	
	public List<Order> findByConditions(String[] conditions, List<String> statuses){
		List<Order> takenAllOrders = new ArrayList<>();
		
		List<Integer> ids = new ArrayList<>();
		List<String> emails = new ArrayList<>();
		List<String> phoneNumbers = new ArrayList<>();
		List<String> others = new ArrayList<>();
		if(conditions.length <= 1 && conditions[0].length() == 0) {
			takenAllOrders = this.orderMapper.findByStatuses(statuses);
		}else {
			for(int i = 0; i < conditions.length; i++) {
				try {
					if(conditions[i].length() != 10) {
						ids.add(Integer.parseInt(conditions[i]));
						break;
					}else {
						if(conditions[i].matches("0[0-9]0[0-9]{7}")) {
							phoneNumbers.add(conditions[i]);
							break;
						}else {
							ids.add(Integer.parseInt(conditions[i]));
							break;
						}
					}
				}catch(NumberFormatException e){
					
				}
				if(conditions[i].contains("@")) {
					emails.add(conditions[i]);
					break;
				}else if(conditions[i].matches("0[0-9]0-?[0-9]{4}-?[0-9]{3}")) {
					phoneNumbers.add(conditions[i]);
					break;
				}else {
					others.add(conditions[i]);
				}
			}
		}
		takenAllOrders = this.orderMapper.findByConditions(ids,emails,phoneNumbers,others,statuses);
		return takenAllOrders;
	}
	
	public List<Order> getOrdersPerTen(List<Order> orders, Integer pageNum){
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
}
