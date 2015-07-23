package com.asiaworld.tmuhj.core.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.asiaworld.tmuhj.core.dao.DsQueryLanguage;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.dao.HibernateQueryLanguage;
import com.asiaworld.tmuhj.core.dao.HibernateRestrictions;
import com.asiaworld.tmuhj.core.entity.Entity;

/**
 * ServiceFactory
 * 
 * @author Roderick
 * @version 2014/11/27
 */
public abstract class ServiceFactory<T extends Entity> implements Service<T> {

	/**
	 * Get the DsRestrictions
	 * 
	 * @return DsRestrictions
	 */
	public static DsRestrictions getDsRestrictions() {
		return new HibernateRestrictions();
	}

	/**
	 * Get the DsQueryLanguage
	 * 
	 * @return
	 */
	public static DsQueryLanguage getDsQueryLanguage() {
		return new HibernateQueryLanguage();
	}

	/**
	 * get all null property's name
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
