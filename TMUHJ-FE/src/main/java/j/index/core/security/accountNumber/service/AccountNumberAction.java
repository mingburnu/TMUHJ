package j.index.core.security.accountNumber.service;

import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.web.GenericCRUDAction;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 使用者
 * 
 * @author Roderick
 * @version 2014/9/29
 */
@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountNumberAction extends GenericCRUDAction<AccountNumber> {

	@Override
	public void validateSave() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateUpdate() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateDelete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String query() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}