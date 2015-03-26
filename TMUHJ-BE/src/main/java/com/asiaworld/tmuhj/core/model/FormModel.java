package com.asiaworld.tmuhj.core.model;

import java.io.Serializable;

/**
 * 頁面控制
 * @author Roderick
 * @version 2014/3/25
 */
public class FormModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2041123225048513337L;

	private boolean disableAllInput;
	
	private String[] ignoreProperties;
	
	public FormModel(boolean disableAllInput) {
		this.disableAllInput = disableAllInput;
	}
	
	public FormModel(boolean disableAllInput, String... ignoreProperties) {
		this.disableAllInput = disableAllInput;
		this.ignoreProperties = ignoreProperties;
	}

	public boolean isDisableAllInput() {
		return disableAllInput;
	}

	public void setDisableAllInput(boolean disableAllInput) {
		this.disableAllInput = disableAllInput;
	}

	public String[] getIgnoreProperties() {
		return ignoreProperties;
	}

	public void setIgnoreProperties(String[] ignoreProperties) {
		this.ignoreProperties = ignoreProperties;
	}
	
}
