package com.asiaworld.tmuhj.core.service;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumberDao;
import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.entity.GenericEntityFull;

/**
 * GenericService
 * 
 * @author Roderick
 * @version 2014/9/29
 */
public abstract class GenericServiceFull<T extends GenericEntityFull>
		implements Service<T> {

	protected final transient Logger log = Logger.getLogger(getClass());

	protected abstract GenericDaoFull<T> getDao();

	@Autowired
	private AccountNumberDao userDao;

	@SuppressWarnings("unchecked")
	@Override
	public T save(T entity, AccountNumber user) throws Exception {
		Assert.notNull(entity);

		entity.initInsert(user);

		T dbEntity = getDao().save(entity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getBySerNo(Long serNo) throws Exception {
		Assert.notNull(serNo);

		T entity = getDao().findBySerNo(serNo);
		makeUserInfo(Arrays.asList(entity));

		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T update(T entity, AccountNumber user) throws Exception {
		Assert.notNull(entity);

		entity.initUpdate(user);

		T dbEntity = update(entity, user, new String[0]);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	@SuppressWarnings("unchecked")
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
			if (entity.getcUid() != null && entity.getuUid() != null) {
				AccountNumber user = getUserInfo(entity.getcUid());
				entity.setCreatedUser(user);

				if (entity.getcUid().equals(entity.getuUid())) {
					entity.setLastModifiedUser(user);
				} else {
					entity.setLastModifiedUser(getUserInfo(entity.getuUid()));
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
	public AccountNumber getUserInfo(String userId) throws Exception {
		Assert.notNull(userId);
		return userDao.findByUserId(userId);
	}

}
