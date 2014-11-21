package j.index.module.apply.journal.service;

import j.index.core.model.DataSet;
import j.index.core.web.GenericCRUDActionFull;
import j.index.module.apply.journal.entity.Journal;
import j.index.module.apply.resourcesUnion.service.ResourcesUnionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JournalAction extends GenericCRUDActionFull<Journal> {

	@Autowired
	private Journal journal;

	@Autowired
	private JournalService journalService;

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

		DataSet<Journal> ds = journalService.getBySql(initDataSet());
		setDs(ds);
		return "journal";
	}

	@Override
	public String list() throws Exception {
		journal = journalService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("serNo")));
		getRequest().setAttribute("journal", journal);
		return "j-detail";
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

	public String ownerJournal() throws Exception {
		getRequest().setAttribute("cusSerNo",
				getRequest().getParameter("cusSerNo"));
		DataSet<Journal> ds = journalService.getByCusSerNo(initDataSet());
		setDs(ds);

		return "journal";
	}

	public String focus() throws Exception {
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Journal> ds = journalService.getByRestrictions(initDataSet());
		setDs(ds);
		return "journal";
	}
}
