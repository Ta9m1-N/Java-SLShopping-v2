package com.slshop.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slshop.common.entity.CartItem;
import com.slshop.security.CustomerUserDetails;

@Service
public class CartService {
	
	private final CartMapper cartMapper;
	
	@Autowired
	public CartService(CartMapper cartMapper) {
		this.cartMapper = cartMapper;
	}
	
	public List<CartItem> getHisItemsNotZero(Long customerId) {
		return cartMapper.findByCustomerIdNotZero(customerId);
	}
	
	public List<CartItem> findByProductId(Integer productId) {
		return this.cartMapper.findByProductId(productId);
	}
	
	public int getQuantityByProductIdAndCustomerId(Long productId, CustomerUserDetails user) {
		if(user == null) {
			return -1;
		}else {
			Long customerId = user.getCustomer().getId();
			return this.cartMapper.findByProductIdAndCustomerId(productId, customerId).getQuantity();
		}
	}
	
	public void insert(int value, Long itemId, Long customerId) {
		this.cartMapper.insert(customerId, itemId, value);
	}
	
	public void save(int value, Long itemId) {
		this.cartMapper.save(itemId, value);
	}
	
	public void delete(Integer cartItemId) {
		this.cartMapper.saveToZero(cartItemId.longValue());
	}
}
