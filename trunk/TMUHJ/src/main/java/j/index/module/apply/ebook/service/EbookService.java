package j.index.module.apply.ebook.service;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import j.index.core.dao.GenericDao;
import j.index.core.dao.IiiRestrictions;
import j.index.core.model.DataSet;
import j.index.core.model.Pager;
import j.index.core.service.GenericService;
import j.index.core.util.IiiBeanFactory;
import j.index.module.apply.ebook.entity.Ebook;
import j.index.module.apply.ebook.entity.EbookDao;
import j.index.module.apply.resourcesUnion.entity.ResourcesUnion;
import j.index.module.apply.resourcesUnion.service.ResourcesUnionService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class EbookService extends GenericService<Ebook> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();
		String keywords = request.getParameter("keywords");

		String option = request.getParameter("option");

		if (option.equals("書名")) {
			option = "bookname";
		} else if (option.equals("ISBN")) {
			option = "ISBN";
		} else if (option.equals("出版社")) {
			option = "publishname";
		} else if (option.equals("作者")) {
			option = "autherName";
		}

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}
		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			keywords = "";
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywords += cArray[i];
			}

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]", " ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (option.equals("ISBN")) {
					if (NumberUtils.isDigits(wordArray[i])) {
						sql = sql + "ISBN=" + wordArray[i] + " or ";
					}
				} else {
					if (wordArray[i].isEmpty() == false) {
						sql = sql + "LOWER(" + option + ") like LOWER('%"
								+ wordArray[i] + "%') or ";
					}
				}
			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		}
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Ebook> getBySql(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String keywords = request.getParameter("keywords");

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}
		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			keywords = "";
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywords += cArray[i];
			}

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]", " ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (wordArray[i].isEmpty() == false) {
					sql = sql + "LOWER(bookname) like LOWER('%" + wordArray[i]
							+ "%') or  LOWER(publishname) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(authername) like LOWER('%"
							+ wordArray[i] + "%') or ";
				}

				if (NumberUtils.isDigits(wordArray[i])) {
					sql = sql + "ISBN=" + wordArray[i] + " or ";
				}
			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Ebook> getByCusSerNo(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		HttpServletRequest request = ServletActionContext.getRequest();
		String cusSerNo = request.getParameter("cusSerNo");

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}

		ArrayList<ResourcesUnion> resourcesUnionList = null;
		if (NumberUtils.isDigits(cusSerNo)) {
			resourcesUnionList = resourcesUnionService.totalEbook(Long
					.parseLong(cusSerNo));
		}

		String sql = "";
		for (int i = 0; i < resourcesUnionList.size(); i++) {
			sql = sql + "serNo=" + resourcesUnionList.get(i).getEbkSerNo()
					+ " or ";
		}

		restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		return dao.findByRestrictions(restrictions, ds);
	}
}
