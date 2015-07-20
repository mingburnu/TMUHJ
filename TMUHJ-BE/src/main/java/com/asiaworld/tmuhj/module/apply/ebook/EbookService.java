package com.asiaworld.tmuhj.module.apply.ebook;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;

@Service
public class EbookService extends GenericServiceFull<Ebook> {

	@Autowired
	private EbookDao dao;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Ebook entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (entity.getOption().equals("entity.bookName")) {
			if (StringUtils.isNotBlank(entity.getBookName())) {
				restrictions.likeIgnoreCase("bookName", entity.getBookName(),
						MatchMode.ANYWHERE);
			}
		}

		if (entity.getOption().equals("entity.isbn")) {
			if (entity.getIsbn() != null) {
				restrictions.eq("isbn", entity.getIsbn());
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public long getEbkSerNoByIsbn(long isbn) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("isbn", isbn);

		if (dao.findByRestrictions(restrictions).size() > 0) {
			return dao.findByRestrictions(restrictions).get(0).getSerNo();
		} else {
			return 0;
		}
	}
}
