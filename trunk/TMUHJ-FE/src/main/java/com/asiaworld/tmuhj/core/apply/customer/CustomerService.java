package com.asiaworld.tmuhj.core.apply.customer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;

@Service
public class CustomerService extends GenericServiceFull<Customer> {

	@Autowired
	private CustomerDao dao;

	@Override
	public DataSet<Customer> getByRestrictions(DataSet<Customer> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Customer> getBySql(DataSet<Customer> ds, String keywords)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isBlank(keywords)) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		if (StringUtils.isNotBlank(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder();
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywordsBuilder.append(cArray[i]);
			}
			keywords = keywordsBuilder.toString();

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");

			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < wordArray.length; i++) {
				if (!wordArray[i].isEmpty()) {
					sqlBuilder.append("LOWER(name) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(engName) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(memo) like LOWER('%"
							+ wordArray[i] + "%') or ");
				}
			}

			String sql = sqlBuilder.toString();
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
	protected GenericDaoFull<Customer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
