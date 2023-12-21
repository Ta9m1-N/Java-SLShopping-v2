package com.slshop.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.slshop.common.entity.CartItem;

@Mapper
public interface CartMapper {
	
	public List<CartItem> findByCustomerId(Long customerId);
	
	public List<CartItem> findByProductId(Integer productId);
	
	public CartItem findById(Long id);
	
	public void insert(Long customerId, Long itemId, int value);
	
	public void save(Long itemId, int value);
}