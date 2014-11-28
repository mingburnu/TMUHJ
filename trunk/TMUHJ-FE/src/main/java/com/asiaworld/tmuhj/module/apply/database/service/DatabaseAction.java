package com.asiaworld.tmuhj.module.apply.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.service.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabaseAction extends GenericCRUDActionFull<Database> {

	@Autowired
	private Database database;

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private ResourcesUnion resourcesUnion;
	
	@Autowired
	private ResourcesUnionService resourcesUnionService;
	
	@Autowired
	private ResourcesBuyers resourcesBuyers;
	
	@Autowired
	private ResourcesBuyersService resourcesBuyersService;


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
		
		resourcesUnion=resourcesUnionService.getByObjSerNo(Long.parseLong(getRequest()
				.getParameter("serNo")), database.getClass());
		
//		resourcesBuyers=resourcesBuyersService.getBySerNo(resourcesUnion.getResSerNo());
		resourcesBuyers=resourcesBuyersService.getBySerNo(3212L);
		
		getRequest().setAttribute("database", database);
		getRequest().setAttribute("resourcesBuyers", resourcesBuyers);
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
