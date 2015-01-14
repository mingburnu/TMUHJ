package com.asiaworld.tmuhj.core.security.accountNumber.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.core.enums.Role;
import com.asiaworld.tmuhj.core.enums.Status;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;

/**
 * 使用者
 * 
 * @author Roderick
 * @version 2014/9/30
 */
@Entity
@Table(name = "accountNumber")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountNumber extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804311089729989927L;

	/**
	 * 用戶流水號
	 */
	@Column(name = "cus_serNo")
	private long cusSerNo;

	/**
	 * 使用者代號
	 */
	@Column(name = "userID", unique = true)
	private String userId;

	/**
	 * 使用者密碼
	 */
	@Column(name = "userPW")
	private String userPw;

	/**
	 * 使用者姓名
	 */
	@Column(name = "userName")
	private String userName;

	/**
	 * user角色
	 */
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * 狀態
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Transient
	private Customer customer;
	
	@Transient
	private String existStatus;

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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userPw
	 */
	@JSON(serialize = false)
	public String getUserPw() {
		return userPw;
	}

	/**
	 * @param userPw
	 *            the userPw to set
	 */
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the existStatus
	 */
	public String getExistStatus() {
		return existStatus;
	}

	/**
	 * @param existStatus the existStatus to set
	 */
	public void setExistStatus(String existStatus) {
		this.existStatus = existStatus;
	}

	public AccountNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountNumber(long cusSerNo, String userId, String userPw,
			String userName, Role role, Status status, Customer customer,
			String existStatus) {
		super();
		this.cusSerNo = cusSerNo;
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.role = role;
		this.status = status;
		this.customer = customer;
		this.existStatus = existStatus;
	}

}
