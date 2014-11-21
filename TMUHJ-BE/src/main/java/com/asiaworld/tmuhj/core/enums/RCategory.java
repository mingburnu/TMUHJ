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
	租貸("租貸");

	private String rCategory;

	private RCategory() {
	}

	private RCategory(String rCategory) {
		this.rCategory = rCategory;
	}

	public String getrCategory() {
		return rCategory;
	}

}
