package com.asiaworld.tmuhj.core.web;

import java.io.InputStream;

import com.asiaworld.tmuhj.core.entity.GenericEntityLog;

/**
 * GenericCRUDAction
 * 
 * @author Roderick
 * @version 2015/01/19
 */
public abstract class GenericCRUDActionLog<T extends GenericEntityLog> extends
		GenericCRUDAction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8788188595174025515L;

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
