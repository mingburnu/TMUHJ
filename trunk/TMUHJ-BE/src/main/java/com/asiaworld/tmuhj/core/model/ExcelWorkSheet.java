package com.asiaworld.tmuhj.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asiaworld.tmuhj.core.entity.Entity;

/**
 * DataSet
 * @author Roderick
 * @version 2015/1/20
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcelWorkSheet<T extends Entity> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 603347662325328516L;

	private String sheetName;
	private List<T> data = new ArrayList<T>(); // 數據行
	private List<String> columns; // 列名

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
}
