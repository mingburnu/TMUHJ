package com.asiaworld.tmuhj.module.apply.resourcesBuyers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.module.apply.enums.Category;
import com.asiaworld.tmuhj.module.apply.enums.Type;

@Entity
@Table(name = "resourcesBuyers")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
	private Category rCategory;

	@Column(name = "Rtype")
	@Enumerated(EnumType.STRING)
	private Type rType;

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
	public Category getrCategory() {
		return rCategory;
	}

	/**
	 * @param rCategory
	 *            the rCategory to set
	 */
	public void setrCategory(Category rCategory) {
		this.rCategory = rCategory;
	}

	/**
	 * @return the rType
	 */
	public Type getrType() {
		return rType;
	}

	/**
	 * @param rType
	 *            the rType to set
	 */
	public void setrType(Type rType) {
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

}
