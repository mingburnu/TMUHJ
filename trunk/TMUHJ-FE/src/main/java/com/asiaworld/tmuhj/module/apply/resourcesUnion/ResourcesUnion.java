package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntitySerNo;

@Entity
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Table(name = "resourcesUnion")
public class ResourcesUnion extends GenericEntitySerNo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4506632636578324717L;

	@Column(name = "cus_SerNo")
	private long cusSerNo;

	@Column(name = "res_SerNo")
	private long resSerNo;

	@Column(name = "ebk_SerNo")
	private long ebkSerNo;

	@Column(name = "dat_SerNo")
	private long datSerNo;

	@Column(name = "jou_SerNo")
	private long jouSerNo;

	/**
	 * @return the cusSerNo
	 */
	public long getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo the cusSerNo to set
	 */
	public void setCusSerNo(long cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the resSerNo
	 */
	public long getResSerNo() {
		return resSerNo;
	}

	/**
	 * @param resSerNo the resSerNo to set
	 */
	public void setResSerNo(long resSerNo) {
		this.resSerNo = resSerNo;
	}

	/**
	 * @return the ebkSerNo
	 */
	public long getEbkSerNo() {
		return ebkSerNo;
	}

	/**
	 * @param ebkSerNo the ebkSerNo to set
	 */
	public void setEbkSerNo(long ebkSerNo) {
		this.ebkSerNo = ebkSerNo;
	}

	/**
	 * @return the datSerNo
	 */
	public long getDatSerNo() {
		return datSerNo;
	}

	/**
	 * @param datSerNo the datSerNo to set
	 */
	public void setDatSerNo(long datSerNo) {
		this.datSerNo = datSerNo;
	}

	/**
	 * @return the jouSerNo
	 */
	public long getJouSerNo() {
		return jouSerNo;
	}

	/**
	 * @param jouSerNo the jouSerNo to set
	 */
	public void setJouSerNo(long jouSerNo) {
		this.jouSerNo = jouSerNo;
	}
	
}
