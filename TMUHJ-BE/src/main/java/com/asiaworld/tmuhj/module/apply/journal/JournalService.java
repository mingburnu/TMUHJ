package com.asiaworld.tmuhj.module.apply.journal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;

@Service
public class JournalService extends GenericServiceFull<Journal> {

	@Autowired
	private JournalDao dao;

	@Override
	public DataSet<Journal> getByRestrictions(DataSet<Journal> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Journal entity = ds.getEntity();
		DsRestrictions restrictions = getDsRestrictions();
		if (entity.getOption().equals("entity.chineseTitle")) {
			if (StringUtils.isNotBlank(entity.getChineseTitle())) {
				restrictions.likeIgnoreCase("chineseTitle",
						entity.getChineseTitle(), MatchMode.ANYWHERE);
			}
		}

		if (entity.getOption().equals("entity.englishTitle")) {
			if (StringUtils.isNotBlank(entity.getEnglishTitle())) {
				restrictions.likeIgnoreCase("englishTitle",
						entity.getEnglishTitle(), MatchMode.ANYWHERE);
			}
		}

		if (entity.getOption().equals("entity.issn")) {
			if (StringUtils.isNotBlank(entity.getIssn())) {
				String issn = entity.getIssn().trim();
				Pattern pattern = Pattern.compile("(\\d{4})(\\-?)(\\d{3})[\\dX]");
				Matcher matcher = pattern.matcher(issn.toUpperCase());
				if(matcher.matches()){
					issn = issn.replace("-", "");
				}
				
				restrictions.likeIgnoreCase("issn", issn.toString());
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public long getJouSerNoByIssn(String issn) throws Exception {
		DsRestrictions restrictions = getDsRestrictions();
		restrictions.eq("issn", issn);

		if (dao.findByRestrictions(restrictions).size() > 0) {
			return dao.findByRestrictions(restrictions).get(0).getSerNo();
		} else {
			return 0;
		}
	}

}
