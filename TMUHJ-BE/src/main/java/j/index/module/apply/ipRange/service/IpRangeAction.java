package j.index.module.apply.ipRange.service;

import j.index.core.web.GenericCRUDActionFull;
import j.index.module.apply.ipRange.entity.IpRange;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IpRangeAction extends GenericCRUDActionFull<IpRange>{

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
