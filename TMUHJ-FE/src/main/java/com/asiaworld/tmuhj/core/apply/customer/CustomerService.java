package com.asiaworld.tmuhj.core.apply.customer;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDao;
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
		Customer entity = ds.getEntity();
		String keywords = entity.getKeywords();

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
		keywords = keywords.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5]", " ");
		String[] wordArray = keywords.split(" ");

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < wordArray.length; i++) {
				orGroup.add(Restrictions.ilike("name", wordArray[i],
						MatchMode.ANYWHERE));
				orGroup.add(Restrictions.ilike("engName", wordArray[i],
						MatchMode.ANYWHERE));
			}

			restrictions.customCriterion(orGroup);

		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Customer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
