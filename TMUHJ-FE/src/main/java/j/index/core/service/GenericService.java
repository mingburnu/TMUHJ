package j.index.core.service;

import j.index.core.dao.GenericDao;
import j.index.core.entity.GenericEntity;
import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.security.accountNumber.entity.AccountNumberDao;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * GenericService
 * 
 * @author Roderick
 * @version 2014/9/29
 */
public abstract class GenericService<T extends GenericEntity> implements
		Service<T> {

	protected final transient Logger log = Logger.getLogger(getClass());

	protected abstract GenericDao<T> getDao();

	@Autowired
	private AccountNumberDao userDao;

	@Override
	public T save(T entity, AccountNumber user) throws Exception {
		Assert.notNull(entity);

		entity.initInsert(user);

		T dbEntity = getDao().save(entity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	@Override
	public T getBySerNo(Long serNo) throws Exception {
		Assert.notNull(serNo);

		T entity = getDao().findBySerNo(serNo);
		makeUserInfo(Arrays.asList(entity));

		return entity;
	}

	@Override
	public T update(T entity, AccountNumber user) throws Exception {
		Assert.notNull(entity);

		entity.initUpdate(user);

		T dbEntity = update(entity, user, new String[0]);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	@Override
	public T update(T entity, AccountNumber user, String... ignoreProperties)
			throws Exception {
		Assert.notNull(entity);

		entity.initUpdate(user);

		T dbEntity = getDao().findBySerNo(entity.getSerNo());

		if (ignoreProperties.length == 0) {
			BeanUtils.copyProperties(entity, dbEntity);
		} else {
			BeanUtils.copyProperties(entity, dbEntity, ignoreProperties);
		}

		getDao().update(dbEntity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	@Override
	public void deleteBySerNo(Long serNo) throws Exception {
		Assert.notNull(serNo);

		getDao().deleteBySerNo(serNo);
	}

	/**
	 * 取得使用者資訊
	 * 
	 * @param serNo
	 * @return
	 * @throws Exception
	 */
	public void makeUserInfo(List<T> entitys) throws Exception {
		for (T entity : entitys) {
			if (entity.getCreatedBy() != null
					&& entity.getLastModifiedBy() != null) {
				AccountNumber user = getUserInfo(entity.getCreatedBy());
				entity.setCreatedUser(user);

				if (entity.getCreatedBy().equals(entity.getLastModifiedBy())) {
					entity.setLastModifiedUser(user);
				} else {
					entity.setLastModifiedUser(getUserInfo(entity
							.getLastModifiedBy()));
				}
			}
		}
	}

	/**
	 * 取得使用者資訊
	 * 
	 * @param serNo
	 * @return
	 * @throws Exception
	 */
	public AccountNumber getUserInfo(Long serNo) throws Exception {
		Assert.notNull(serNo);

		return userDao.findBySerNo(serNo);
	}

}
