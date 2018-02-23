package com.cypher.activiti.activiti.bean;

import java.io.Serializable;
import java.util.Date;

public class ApproveForm implements Serializable {

	private static final long serialVersionUID = 852034958459182905L;

	private String reason;
	private Integer money;
	private Date date;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
