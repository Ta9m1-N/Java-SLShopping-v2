package com.slshop.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slshop.common.entity.order.Order;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
    public String viewOrdersPage(Model model) {
		List<Order> orders = this.orderService.findPerTen(1);
		int pageAmount = this.orderService.getPageAmount();
		model.addAttribute("orders", orders);
		model.addAttribute("pageNum", 1);
		model.addAttribute("pageAmount", pageAmount);
    	return "orders";
    }
	
	@GetMapping("/{pageNum}")
	public String viewOrdersPageByPageNum(@PathVariable("pageNum") Integer pageNum, Model model) {
		List<Order> orders = this.orderService.findPerTen(pageNum);
		int pageAmount = this.orderService.getPageAmount();
		model.addAttribute("orders", orders);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageAmount", pageAmount);
		return "orders";
	}
}
