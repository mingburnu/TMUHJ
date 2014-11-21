package j.index.module.commons.web;

import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.web.GenericWebActionFull;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * CommonQueryPageAction
 * 
 * @author Roderick
 * @version 2014/10/12
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@SuppressWarnings("serial")
public class CommonQueryPageAction extends GenericWebActionFull<AccountNumber> {

}
