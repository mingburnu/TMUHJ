package com.asiaworld.tmuhj.module.enums;

/**
 * 資源類別
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public enum Category {
	/** 買斷. */
	買斷("買斷"),

	/** 租貸. */
	租貸("租貸"),

	/** 未註明. */
	未註明("未註明"),

	/** 不明. */
	不明("不明");

	private String category;

	private Category() {
	}

	private Category(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

}
