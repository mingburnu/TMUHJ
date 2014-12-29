package com.asiaworld.tmuhj.core.enums;

/**
 * 資源類別
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public enum RCategory {
	/** 買斷. */
	買斷("買斷"),

	/** 租貸. */
	租貸("租貸"),
	
	/** 未註明. */
	未註明("未註明");

	private String category;

	private RCategory() {
	}

	private RCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

}
