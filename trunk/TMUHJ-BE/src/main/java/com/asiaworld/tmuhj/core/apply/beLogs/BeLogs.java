package com.asiaworld.tmuhj.core.apply.beLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.entity.GenericEntityLog;
import com.asiaworld.tmuhj.core.enums.Act;

/**
 * BE_Logs
 * 
 * @author Roderick
 * @version 2015/01/19
 */
@Entity
@Table(name = "BE_Logs")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BeLogs extends GenericEntityLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 493376977661815130L;

	// 類型
	@Column(name = "actionType")
	@Enumerated(EnumType.STRING)
	private Act actionType;

	// 帳戶流水號
	@Column(name = "fk_account_serNo")
	private long userSerNo;

	// 用戶流水號
	@Column(name = "fk_customer_serNo")
	private long cusSerNo;

	@Transient
	private AccountNumber accountNumber;

	@Transient
	private Customer customer;

	@Transient
	private LocalDateTime start;

	@Transient
	private LocalDateTime end;
	
	@Transient
	private int count;
	
	@Transient
	private int rank;

	/**
	 * @return the actionType
	 */
	public Act getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(Act actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the userSerNo
	 */
	public long getUserSerNo() {
		return userSerNo;
	}

	/**
	 * @param userSerNo
	 *            the userSerNo to set
	 */
	public void setUserSerNo(long userSerNo) {
		this.userSerNo = userSerNo;
	}

	/**
	 * @return the cusSerNo
	 */
	public long getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo
	 *            the cusSerNo to set
	 */
	public void setCusSerNo(long cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the accountNumber
	 */
	public AccountNumber getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(AccountNumber accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the start
	 */
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public LocalDateTime getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeLogs [actionType=" + actionType + ", userSerNo=" + userSerNo
				+ ", cusSerNo=" + cusSerNo + ", accountNumber=" + accountNumber
				+ ", customer=" + customer + ", start=" + start + ", end="
				+ end + ", count=" + count + ", rank=" + rank + "]";
	}

	public BeLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeLogs(Act actionType, long userSerNo, long cusSerNo) {
		super();
		this.actionType = actionType;
		this.userSerNo = userSerNo;
		this.cusSerNo = cusSerNo;
	}

}
