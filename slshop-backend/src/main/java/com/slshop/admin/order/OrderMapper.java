package com.slshop.admin.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slshop.common.entity.order.Order;

@Mapper
public interface OrderMapper {
	
	List<Order> findAll();
	
	List<Order> findByConditions(@Param("ids") List<Integer> ids, @Param("emails") List<String> emails, @Param("phoneNumbers") List<String> phoneNumbers, @Param("others") List<String> others, @Param("statuses") List<String> statuses);
	
	List<Order> findByStatuses(List<String> statuses);
}
