package com.asiaworld.tmuhj.core.apply.customer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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

@Service
public class CustomerService extends GenericServiceFull<Customer> {

	@Autowired
	private CustomerDao dao;

	@Override
	public DataSet<Customer> getByRestrictions(DataSet<Customer> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Customer entity = ds.getEntity();
		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "０１２３４５６７８９", "0123456789");
		indexTerm = indexTerm.replaceAll(
				"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
		Set<String> keywordSet = new HashSet<String>(Arrays.asList(indexTerm
				.split(" ")));
		String[] wordArray = keywordSet.toArray(new String[keywordSet.size()]);

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction or = Restrictions.disjunction();
			Junction nameAnd = Restrictions.conjunction();
			Junction engNameAnd = Restrictions.conjunction();
			for (int i = 0; i < wordArray.length; i++) {
				nameAnd.add(Restrictions.ilike("name", wordArray[i],
						MatchMode.ANYWHERE));
				engNameAnd.add(Restrictions.ilike("engName", wordArray[i],
						MatchMode.ANYWHERE));
			}

			or.add(nameAnd).add(engNameAnd);
			restrictions.customCriterion(or);
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
