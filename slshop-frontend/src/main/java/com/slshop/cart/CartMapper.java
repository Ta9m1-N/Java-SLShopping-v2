package com.slshop.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.slshop.common.entity.CartItem;

@Mapper
public interface CartMapper {
	
	List<CartItem> findByCustomerId(Long customerId);
}
