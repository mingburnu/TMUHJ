package j.index.module.commons.web;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.security.accountNumber.service.AccountNumberService;
import j.index.core.web.GenericWebActionFull;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
