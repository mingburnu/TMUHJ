package com.asiaworld.tmuhj.core.enums;

/**
 * 資源種類
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public enum RType {
	/** 電子書. */
	電子書("電子書"),

	/** 期刊. */
	期刊("期刊"),

	/** 資料庫. */
	資料庫("資料庫");

	private String type;

	private RType() {
	}

	private RType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
