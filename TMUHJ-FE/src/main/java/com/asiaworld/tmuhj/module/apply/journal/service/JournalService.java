package com.asiaworld.tmuhj.module.apply.journal.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.journal.entity.Journal;
import com.asiaworld.tmuhj.module.apply.journal.entity.JournalDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class JournalService extends GenericServiceFull<Journal> {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private JournalDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Journal> getByRestrictions(DataSet<Journal> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();
		String keywords = request.getParameter("keywords");
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String option = request.getParameter("option");

		if (option == null) {
			option = "";
		} else if (option.equals("中文刊名")) {
			option = "chinesetitle";
		} else if (option.equals("英文刊名")) {
			option = "englishtitle";
		} else if (option.equals("英文縮寫")) {
			option = "abbreviationtitle";
		} else if (option.equals("出版商")) {
			option = "publishname";
		} else if (option.equals("ISSN")) {
			option = "ISSN";
		} else {
			option = "";
		}

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0) {
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

			keywords = keywords
					.replaceAll(
							"[^0-9a-zA-Z\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]",
							" ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (option.equals("ISSN")) {
					if (NumberUtils.isDigits(wordArray[i].replace("-", "")
							.substring(0, 6))
							&& wordArray[i].replace("-", "").length() == 8) {

						if (wordArray[i].replace("-", "").substring(7)
								.equals("x")
								|| wordArray[i].replace("-", "").substring(7)
										.equals("X")
								|| NumberUtils.isDigits(wordArray[i].replace(
										"-", "").substring(7))) {
							sql = sql
									+ "ISSN='"
									+ wordArray[i].replace("-", "").replace(
											"x", "X") + "' or ";
						}
					}
				} else {
					if (!wordArray[i].isEmpty() && !option.isEmpty()) {
						sql = sql + "LOWER(" + option + ") like LOWER('%"
								+ wordArray[i] + "%') or ";
					}
				}
			}

			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Journal> getBySql(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String keywords = request.getParameter("keywords");
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0) {
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

			keywords = keywords
					.replaceAll(
							"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]",
							" ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (wordArray[i].isEmpty() == false) {
					sql = sql + "LOWER(chinesetitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(englishtitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(abbreviationtitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(publishname) like LOWER('%"
							+ wordArray[i] + "%') or ";
				}

				if (wordArray[i].replace("-", "").length() == 8
						&& NumberUtils.isDigits(wordArray[i].replace("-", "")
								.substring(0, 6))) {
					if (wordArray[i].replace("-", "").substring(7).equals("x")
							|| wordArray[i].replace("-", "").substring(7)
									.equals("X")
							|| NumberUtils.isDigits(wordArray[i].replace("-",
									"").substring(7))) {
						sql = sql
								+ "ISSN='"
								+ wordArray[i].replace("-", "").replace("x",
										"X") + "' or ";
					}

				}

			}

			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Journal> getByCusSerNo(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		HttpServletRequest request = ServletActionContext.getRequest();
		String cusSerNo = request.getParameter("cusSerNo");

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}

		ArrayList<ResourcesUnion> resourcesUnionList = null;
		if (NumberUtils.isDigits(cusSerNo) && Long.parseLong(cusSerNo) > 0) {
			resourcesUnionList = resourcesUnionService.totalJournal(Long
					.parseLong(cusSerNo));
		}

		String sql = "";
		if (resourcesUnionList != null && !resourcesUnionList.isEmpty()
				&& resourcesUnionList.size() > 0) {
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sql = sql + "serNo=" + resourcesUnionList.get(i).getJouSerNo()
						+ " or ";
			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}
		return dao.findByRestrictions(restrictions, ds);
	}
}
