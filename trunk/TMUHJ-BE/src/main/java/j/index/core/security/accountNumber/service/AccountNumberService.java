package j.index.core.security.accountNumber.service;

import j.index.core.dao.GenericDaoFull;
import j.index.core.dao.IiiRestrictions;
import j.index.core.enums.Role;
import j.index.core.model.DataSet;
import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.security.accountNumber.entity.AccountNumberDao;
import j.index.core.service.GenericServiceFull;
import j.index.core.util.EncryptorUtil;
import j.index.core.util.IiiBeanFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 使用者 Service
 * 
 * @author Roderick
 * @version 2014/9/30
 */
@Service
public class AccountNumberService extends GenericServiceFull<AccountNumber> {

	@Autowired
	private AccountNumberDao dao;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected GenericDaoFull<AccountNumber> getDao() {
		return dao;
	}

	@Override
	public DataSet<AccountNumber> getByRestrictions(DataSet<AccountNumber> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		AccountNumber entity = ds.getEntity();
		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		if (StringUtils.isNotEmpty(entity.getUserId())) {
			restrictions.eq("userId", entity.getUserId());
		}
		if (StringUtils.isNotEmpty(entity.getUserName())) {
			restrictions.likeIgnoreCase("userName", entity.getUserName(),
					MatchMode.ANYWHERE);
		}
		if (entity != null && entity.getRole() != null) {
			restrictions.eq("role", entity.getRole());
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	public AccountNumber save(AccountNumber entity, AccountNumber user)
			throws Exception {
		Assert.notNull(entity);
		// entity.setTimeToSystime();
		entity.initInsert(user);
		if (StringUtils.isNotEmpty(entity.getUserPw())) { // 密碼非空則進行加密
			final String encryptedPassword = EncryptorUtil.encrypt(entity
					.getUserPw());
			entity.setUserPw(encryptedPassword);
		}

		AccountNumber dbEntity = dao.save(entity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	/**
	 * 檢查登入帳密
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Boolean checkUser(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		restrictions.eq("userId", entity.getUserId());
		restrictions.ne("role", Role.使用者);
		List<AccountNumber> secUsers = dao.findByRestrictions(restrictions);
		if (secUsers == null || secUsers.isEmpty()) {
			return false;
		}
		AccountNumber secUser = secUsers.get(0);

		return EncryptorUtil.checkPassword(entity.getUserPw(),
				secUser.getUserPw());
	}
	
	public HashSet<String> getAllcUid() {
		Session session = sessionFactory.getCurrentSession();
		HashSet<String> allcUid=new LinkedHashSet<String>();
		Criteria criteria = session.createCriteria(AccountNumber.class);
		Iterator<?> iterator = criteria.list().iterator();
		while (iterator.hasNext()) {
			AccountNumber accountNumber=(AccountNumber) iterator.next();
			allcUid.add(accountNumber.getcUid());
		}
		return allcUid;
	}
}
