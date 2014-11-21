package com.asiaworld.tmuhj.module.commons.web;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.security.accountNumber.service.AccountNumberService;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;

/**
 * CommonHomePageAction
 * 
 * @author Roderick
 * @version 2014/10/12
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SuppressWarnings("serial")
public class CommonHomePageAction extends GenericWebActionFull<AccountNumber> {
	@Autowired
	private AccountNumberService userService;

	public String entry() {
		HashSet<String> allcUid = userService.getAllcUid();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("allcUid", allcUid);
		return SUCCESS;
	}
}
