package j.index.core.security.accountNumber.service;

import java.util.List;

import j.index.core.model.DataSet;
import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.web.GenericCRUDAction;
import j.index.module.apply.customer.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	AccountNumber accountNumber;

	@Autowired
	AccountNumberService userService;

	@Autowired
	CustomerService customerService;

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
		if (getEntity().getSerNo() != null) {
			AccountNumber user = userService.getBySerNo(getEntity().getSerNo());
			setEntity(user);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<AccountNumber> ds = userService
				.getByRestrictions(initDataSet());
		List<AccountNumber> results = ds.getResults();

		for (int i = 0; i < results.size(); i++) {
			results.get(i).setCustomer(
					customerService.getBySerNo(results.get(i).getCusSerNo()));
		}

		ds.setResults(results);

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		userService.save(getEntity(), getLoginUser());
		return LIST;
	}

	@Override
	public String update() throws Exception {

//		String uriReferer = new URI(getRequest().getHeader("referer"))
//				.getPath();

		
		accountNumber = userService.update(getEntity(), getLoginUser(),
				"userId","userPw");
		setEntity(accountNumber);
		
		getResponse().sendRedirect(getRequest().getHeader("referer"));
		return LIST;
	}

	@Override
	public String delete() throws Exception {
		userService.deleteBySerNo(getEntity().getSerNo());
		disableAllInput();
		addActionMessage("檔案已刪除");
		return DELETE;
	}
}
