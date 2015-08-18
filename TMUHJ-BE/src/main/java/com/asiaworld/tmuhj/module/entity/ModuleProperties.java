package com.asiaworld.tmuhj.module.entity;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;

@MappedSuperclass
public abstract class ModuleProperties extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011709439415119645L;

	@Transient
	@Autowired
	private ResourcesBuyers resourcesBuyers;

	@Transient
	private List<Customer> customers;

	@Transient
	private Long[] cusSerNo;

	/**
	 * @return the resourcesBuyers
	 */
	public ResourcesBuyers getResourcesBuyers() {
		return resourcesBuyers;
	}

	/**
	 * @param resourcesBuyers
	 *            the resourcesBuyers to set
	 */
	public void setResourcesBuyers(ResourcesBuyers resourcesBuyers) {
		this.resourcesBuyers = resourcesBuyers;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @return the cusSerNo
	 */
	public Long[] getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo
	 *            the cusSerNo to set
	 */
	public void setCusSerNo(Long[] cusSerNo) {
		this.cusSerNo = cusSerNo;
	}
}
