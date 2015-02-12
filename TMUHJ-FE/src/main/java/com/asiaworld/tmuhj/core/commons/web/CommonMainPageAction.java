package com.asiaworld.tmuhj.core.commons.web;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
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
public class CommonMainPageAction extends GenericWebActionFull<AccountNumber> {
	
}
