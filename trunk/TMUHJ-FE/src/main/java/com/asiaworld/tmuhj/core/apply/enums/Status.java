package com.asiaworld.tmuhj.core.apply.enums;

/**
 * 
 * @author Roderick
 * @version 2014/9/30
 */
public enum Status {

	/** 生效. */
	生效("生效"),

	/** 不生效. */
	審核中("審核中"),

	/** 不生效. */
	不生效("不生效");

	private String status;

	private Status() {

	}

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
