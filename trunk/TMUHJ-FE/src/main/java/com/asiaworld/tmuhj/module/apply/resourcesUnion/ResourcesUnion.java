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
	private Long cusSerNo;

	@Column(name = "res_SerNo")
	private Long resSerNo;

	@Column(name = "ebk_SerNo")
	private Long ebkSerNo;

	@Column(name = "dat_SerNo")
	private Long datSerNo;

	@Column(name = "jou_SerNo")
	private Long jouSerNo;

	/**
	 * @return the cusSerNo
	 */
	public Long getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo the cusSerNo to set
	 */
	public void setCusSerNo(Long cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the resSerNo
	 */
	public Long getResSerNo() {
		return resSerNo;
	}

	/**
	 * @param resSerNo the resSerNo to set
	 */
	public void setResSerNo(Long resSerNo) {
		this.resSerNo = resSerNo;
	}

	/**
	 * @return the ebkSerNo
	 */
	public Long getEbkSerNo() {
		return ebkSerNo;
	}

	/**
	 * @param ebkSerNo the ebkSerNo to set
	 */
	public void setEbkSerNo(Long ebkSerNo) {
		this.ebkSerNo = ebkSerNo;
	}

	/**
	 * @return the datSerNo
	 */
	public Long getDatSerNo() {
		return datSerNo;
	}

	/**
	 * @param datSerNo the datSerNo to set
	 */
	public void setDatSerNo(Long datSerNo) {
		this.datSerNo = datSerNo;
	}

	/**
	 * @return the jouSerNo
	 */
	public Long getJouSerNo() {
		return jouSerNo;
	}

	/**
	 * @param jouSerNo the jouSerNo to set
	 */
	public void setJouSerNo(Long jouSerNo) {
		this.jouSerNo = jouSerNo;
	}
	
}
