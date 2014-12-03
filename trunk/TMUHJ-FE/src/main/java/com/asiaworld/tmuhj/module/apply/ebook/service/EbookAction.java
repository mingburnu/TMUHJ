package com.asiaworld.tmuhj.module.apply.ebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericCRUDActionFull<Ebook> {

	@Autowired
	private Ebook ebook;

	@Autowired
	private EbookService ebookService;

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
		getRequest().setAttribute("query", "apply.ebook.query.action");
		DataSet<Ebook> ds = ebookService.getBySql(initDataSet());
		setDs(ds);
		return "ebook";
	}

	@Override
	public String list() throws Exception {
		ebook = ebookService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("serNo")));
		getRequest().setAttribute("ebook", ebook);
		return "e-detail";
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

	public String owner() throws Exception {
		getRequest().setAttribute("cusSerNo",
				getRequest().getParameter("cusSerNo"));
		getRequest().setAttribute("owner", "apply.ebook.owner.action");
		DataSet<Ebook> ds = ebookService.getByCusSerNo(initDataSet());
		setDs(ds);

		return "ebook";
	}

	public String focus() throws Exception {
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		getRequest().setAttribute("focus", "apply.ebook.focus.action");
		DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());
		setDs(ds);
		return "ebook";
	}
}
