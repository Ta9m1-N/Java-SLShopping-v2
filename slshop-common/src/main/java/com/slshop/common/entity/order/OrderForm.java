package com.slshop.common.entity.order;

public class OrderForm {
	
	private String conditions;
	
	private String[] check;
	
	public String getConditions() {
		return this.conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	public String[] getCheck() {
		return this.check;
	}
	public void setCheck(String[] check) {
		this.check = check;
	}
}
