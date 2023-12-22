package com.slshop.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.slshop.common.entity.CartItem;

@Mapper
public interface CartMapper {
	
	public List<CartItem> findByCustomerIdNotZero(Long customerId);
	
	public List<CartItem> findByProductId(Integer productId);
	
	public CartItem findByProductIdAndCustomerId(Long productId, Long customerId);
	
	public CartItem findById(Long id);
	
	public void insert(Long customerId, Long itemId, int value);
	
	public void save(Long itemId, int value);
	
	public void saveToZero(Long cartItemId);
}
