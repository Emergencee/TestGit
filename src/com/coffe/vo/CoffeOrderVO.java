package com.coffe.vo;

import java.util.List;

public class CoffeOrderVO {
	private int coffeId;
	private String flag;
	private int orderCnt;



	public CoffeOrderVO(int coffeId, String flag, int orderCnt) {
		this.coffeId = coffeId;
		this.flag = flag;
		this.orderCnt = orderCnt;
	}

	public int getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(int orderCnt) {
		this.orderCnt = orderCnt;
	}

	public int getCoffeId() {
		return coffeId;
	}

	public void setCoffeId(int coffeId) {
		this.coffeId = coffeId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}