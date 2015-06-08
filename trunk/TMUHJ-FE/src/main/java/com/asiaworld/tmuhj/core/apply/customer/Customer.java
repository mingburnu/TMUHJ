package com.asiaworld.tmuhj.core.apply.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;

@Entity
@Table(name = "customer")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Customer extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5508975058661670537L;

	// 姓名
	@Column(name = "name", unique = true)
	private String name;

	// 英文姓名
	@Column(name = "engName")
	private String engName;

	// 地址
	@Column(name = "address")
	private String address;

	// email
	@Column(name = "email")
	private String email;

	// 電話
	@Column(name = "tel")
	private String tel;

	// 聯絡人
	@Column(name = "contactUserName")
	private String contactUserName;

	// 備註
	@Column(name = "memo")
	@Type(type = "text")
	private String memo;

	@Transient
	private long dbAmount;

	@Transient
	private long ebookAmount;

	@Transient
	private long journalAmount;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the engName
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * @param engName
	 *            the engName to set
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the contactUserName
	 */
	public String getContactUserName() {
		return contactUserName;
	}

	/**
	 * @param contactUserName
	 *            the contactUserName to set
	 */
	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dbAmount
	 */
	public long getDbAmount() {
		return dbAmount;
	}

	/**
	 * @param dbAmount
	 *            the dbAmount to set
	 */
	public void setDbAmount(long dbAmount) {
		this.dbAmount = dbAmount;
	}

	/**
	 * @return the ebookAmount
	 */
	public long getEbookAmount() {
		return ebookAmount;
	}

	/**
	 * @param ebookAmount
	 *            the ebookAmount to set
	 */
	public void setEbookAmount(long ebookAmount) {
		this.ebookAmount = ebookAmount;
	}

	/**
	 * @return the journalAount
	 */
	public long getJournalAmount() {
		return journalAmount;
	}

	/**
	 * @param journalAount
	 *            the journalAount to set
	 */
	public void setJournalAmount(long journalAmount) {
		this.journalAmount = journalAmount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Customer [name=" + name + ", engName=" + engName + ", address="
				+ address + ", email=" + email + ", tel=" + tel
				+ ", contactUserName=" + contactUserName + ", memo=" + memo
				+ ", dbAmount=" + dbAmount + ", ebookAmount=" + ebookAmount
				+ ", journalAmount=" + journalAmount + "]";
	}

}
