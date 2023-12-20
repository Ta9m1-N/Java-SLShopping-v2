package com.slshop.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slshop.common.entity.CartItem;
import com.slshop.security.CustomerUserDetails;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	private final CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping
	public String viewCart(Model model, @AuthenticationPrincipal CustomerUserDetails user) {
		Long customerId = user.getCustomer().getId();
		List<CartItem> cartItems = this.cartService.getHisItems(customerId);
		int total = 0;
		for(int i = 0; i < cartItems.size(); i++) {
			total += cartItems.get(i).getSubtotal();
		}
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", total);
		return "cart/cart";
	}
}
