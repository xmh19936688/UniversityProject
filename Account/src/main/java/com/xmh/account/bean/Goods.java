package com.xmh.account.bean;

import java.util.Date;


public class Goods {
	private Integer id;
	private String fallname;
	private Integer amount;
	private Date date;
	private Double in_price;
	private Double out_price;
	private Double money;
	private Double total;
	private String remarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFallname() {
		return fallname;
	}
	public void setFallname(String fallname) {
		this.fallname = fallname;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getIn_price() {
		return in_price;
	}
	public void setIn_price(Double in_price) {
		this.in_price = in_price;
	}
	public Double getOut_price() {
		return out_price;
	}
	public void setOut_price(Double out_price) {
		this.out_price = out_price;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
