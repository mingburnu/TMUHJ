package j.index.module.apply.database.service;

import j.index.core.model.DataSet;
import j.index.core.web.GenericCRUDAction;
import j.index.module.apply.database.entity.Database;
import j.index.module.apply.resourcesUnion.service.ResourcesUnionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabaseAction extends GenericCRUDAction<Database> {

	@Autowired
	private Database database;

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

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
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));

		DataSet<Database> ds = databaseService.getBySql(initDataSet());
		setDs(ds);
		return "database";
	}

	@Override
	public String list() throws Exception {
		database = databaseService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("serNo")));
		getRequest().setAttribute("database", database);
		return "d-detail";
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

	public String ownerDb() throws Exception {
		getRequest().setAttribute("cusSerNo",
				getRequest().getParameter("cusSerNo"));
		DataSet<Database> ds = databaseService.getByCusSerNo(initDataSet());
		setDs(ds);

		return "database";
	}

	public String focus() throws Exception {
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Database> ds = databaseService.getByRestrictions(initDataSet());
		setDs(ds);
		return "database";
	}
}
