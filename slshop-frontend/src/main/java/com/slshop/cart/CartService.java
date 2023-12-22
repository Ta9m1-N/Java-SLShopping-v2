package com.slshop.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slshop.common.entity.CartItem;

@Service
public class CartService {
	
	private final CartMapper cartMapper;
	
	@Autowired
	public CartService(CartMapper cartMapper) {
		this.cartMapper = cartMapper;
	}
	
	public List<CartItem> getHisItems(Long customerId) {
		return cartMapper.findByCustomerId(customerId);
	}
	
	public List<CartItem> findByProductId(Integer productId) {
		return this.cartMapper.findByProductId(productId);
	}
	
	public void insert(int value, Long itemId, Long customerId) {
		this.cartMapper.insert(customerId, itemId, value);
	}
	
	public void save(int value, Long itemId) {
		this.cartMapper.save(itemId, value);
	}
}
