package com.asiaworld.tmuhj.core.web;

import java.io.InputStream;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;

/**
 * GenericCRUDAction
 * 
 * @author Roderick
 * @version 2014/11/21
 */
public abstract class GenericCRUDActionFull<T extends GenericEntityFull>
		extends GenericCRUDAction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2483625816501457663L;

	private InputStream inputStream;

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
