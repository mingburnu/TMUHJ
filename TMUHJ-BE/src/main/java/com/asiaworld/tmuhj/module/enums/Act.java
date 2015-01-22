package com.asiaworld.tmuhj.module.enums;

/**
 * Action
 * 
 * @author Roderick
 * @version 2015/01/19
 */
public enum Act {
	/**
	 * 使用者
	 */
	登入("登入"),

	登出("登出");

	private String action;

	private Act() {
	}

	private Act(String action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

}
