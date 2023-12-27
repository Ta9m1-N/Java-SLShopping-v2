package com.slshop.admin.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slshop.common.entity.order.Order;
import com.slshop.common.entity.order.OrderForm;
import com.slshop.common.entity.order.OrderStatus;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	private Map<String,String> getCheckboxItems(){
		Map<String,String> statuses = new LinkedHashMap<>();
		for(OrderStatus s : OrderStatus.values()) {
			statuses.put(s.toString(), s.getJpText());
		}
		return statuses;
	}
	
	/**
	 * 処理が下のメソッドと同一だったためコメントアウト
	@GetMapping
    public String viewOrdersPage(Model model, @ModelAttribute("form") OrderForm orderForm) {
		List<Order> orders = this.orderService.findPerTen(1);
		int pageAmount = this.orderService.getPageAmount();
		model.addAttribute("orders", orders);
		model.addAttribute("checkboxItems", this.getCheckboxItems());
		model.addAttribute("pageNum", 1);
		model.addAttribute("pageAmount", pageAmount);
    	return "orders";
    }
	*/
	
	@GetMapping("/{pageNum}")
	public String viewOrdersPageByPageNum(@PathVariable("pageNum") Integer pageNum, Model model, @ModelAttribute("form") OrderForm orderForm) {
		List<Order> orders = this.orderService.findPerTen(pageNum);
		int pageAmount = this.orderService.getPageAmount();
		model.addAttribute("orders", orders);
		model.addAttribute("checkboxItems", this.getCheckboxItems());
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageAmount", pageAmount);
		return "orders";
	}
	
	@PostMapping("/search/{pageNum}")
	public String searchOrders(Model model, @ModelAttribute("form") OrderForm orderForm, @PathVariable("pageNum") Integer pageNum) {
		boolean isOrdered = false;
		String text = orderForm.getConditions();
		if(text.length() != 0) {
			isOrdered = true;
		}
		String[] status = orderForm.getCheck();
		
		String[] conditions;
		if(text.contains(" ")) {
			conditions = text.split(" ");
		}else {
			conditions = new String[1];
			conditions[0] = text;
		}
		List<String> statuses = new ArrayList<>();
		for(int i = 0; i < status.length; i++) {
			if(status[i] != "on") {
				statuses.add(status[i]);
				isOrdered = true;
			}
		}
		List<Order> searchedAllOrders = this.orderService.findByConditions(conditions, statuses);
		List<Order> searchedOrders = this.orderService.getOrdersPerTen(searchedAllOrders, pageNum);
		int pageAmount = this.orderService.getPageAmount(searchedAllOrders);
		model.addAttribute("orders", searchedOrders);
		model.addAttribute("checkboxItems", this.getCheckboxItems());
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageAmount", pageAmount);
		model.addAttribute("isOrdered", isOrdered);
		return "orders";
	}
}
