package com.slshop.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@PostMapping("/add/{id}")
	public String addItem(@ModelAttribute("itemValue") Integer itemValue, @PathVariable("id") Integer productId, @AuthenticationPrincipal CustomerUserDetails user, RedirectAttributes redirectAttributes) {
		if(user == null) {
			redirectAttributes.addFlashAttribute("message", "ログインが必要です。");
			return "redirect:/products/detail/{id}";
		}
		Long customerId = user.getCustomer().getId();
		List<CartItem> cartItems = this.cartService.findByProductId(productId);
		if(cartItems.isEmpty() || cartItems.size() == 0) {
			this.cartService.insert((int) itemValue, productId.longValue(), customerId);
			redirectAttributes.addFlashAttribute("message", "商品をカートに追加しました(現在数量："+itemValue+")");
			return "redirect:/products/detail/{id}";
		}else {
			for(int i = 0; i < cartItems.size(); i++) {
				if(cartItems.get(i).getCustomer().getId() == customerId) {
					int finalItemValue = cartItems.get(i).getQuantity() + (int) itemValue;
					if(finalItemValue <= 10) {
						if(cartItems.get(i).getQuantity() == 0) {
							this.cartService.insert(finalItemValue, cartItems.get(i).getId(), customerId);
						}else {
							this.cartService.save(finalItemValue, cartItems.get(i).getId());
						}
						redirectAttributes.addFlashAttribute("message", "商品をカートに追加しました(現在数量："+finalItemValue+")");
						return "redirect:/products/detail/{id}";
					}else {
						redirectAttributes.addFlashAttribute("message", "商品を追加できませんでした。最大数量は10個です。(カート内："+cartItems.get(i).getQuantity()+")");
						return "redirect:/products/detail/{id}";
					}
				}else {
					this.cartService.insert((int) itemValue, productId.longValue(), customerId);
					redirectAttributes.addFlashAttribute("message", "商品をカートに追加しました(現在数量："+itemValue+")");
					return "redirect:/products/detail/{id}";
				}
			}
		}
		return "";
	}
}
