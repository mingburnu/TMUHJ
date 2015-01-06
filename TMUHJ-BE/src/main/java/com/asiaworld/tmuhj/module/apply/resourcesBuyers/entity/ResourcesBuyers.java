package com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.core.enums.RCategory;
import com.asiaworld.tmuhj.core.enums.RType;

@Entity
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Table(name = "resourcesBuyers")
public class ResourcesBuyers extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6786606719444916598L;
	@Column(name = "startdate")
	private String startDate;

	@Column(name = "maturitydate")
	private String maturityDate;

	@Column(name = "Rcategory")
	@Enumerated(EnumType.STRING)
	private RCategory rCategory;

	@Column(name = "Rtype")
	@Enumerated(EnumType.STRING)
	private RType rType;

	@Column(name = "DBchttitle")
	private String dbChtTitle;

	@Column(name = "DBengtitle")
	private String dbEngTitle;

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the maturityDate
	 */
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate
	 *            the maturityDate to set
	 */
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the rCategory
	 */
	public RCategory getrCategory() {
		return rCategory;
	}

	/**
	 * @param rCategory
	 *            the rCategory to set
	 */
	public void setrCategory(RCategory rCategory) {
		this.rCategory = rCategory;
	}

	/**
	 * @return the rType
	 */
	public RType getrType() {
		return rType;
	}

	/**
	 * @param rType
	 *            the rType to set
	 */
	public void setrType(RType rType) {
		this.rType = rType;
	}

	/**
	 * @return the dbChtTitle
	 */
	public String getDbChtTitle() {
		return dbChtTitle;
	}

	/**
	 * @param dbChtTitle
	 *            the dbChtTitle to set
	 */
	public void setDbChtTitle(String dbChtTitle) {
		this.dbChtTitle = dbChtTitle;
	}

	/**
	 * @return the dbEngTitle
	 */
	public String getDbEngTitle() {
		return dbEngTitle;
	}

	/**
	 * @param dbEngTitle
	 *            the dbEngTitle to set
	 */
	public void setDbEngTitle(String dbEngTitle) {
		this.dbEngTitle = dbEngTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourcesBuyers [startDate=" + startDate + ", maturityDate="
				+ maturityDate + ", rCategory=" + rCategory + ", rType="
				+ rType + ", dbChtTitle=" + dbChtTitle + ", dbEngTitle="
				+ dbEngTitle + "]";
	}

	public ResourcesBuyers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourcesBuyers(String startDate, String maturityDate,
			RCategory rCategory, RType rType, String dbChtTitle,
			String dbEngTitle) {
		super();
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.rCategory = rCategory;
		this.rType = rType;
		this.dbChtTitle = dbChtTitle;
		this.dbEngTitle = dbEngTitle;
	}

}
