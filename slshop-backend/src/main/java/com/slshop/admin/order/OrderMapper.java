package com.slshop.admin.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.slshop.common.entity.order.Order;

@Mapper
public interface OrderMapper {
	
	List<Order> findAll();
}
