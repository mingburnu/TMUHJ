package com.asiaworld.tmuhj.core.web;

import com.asiaworld.tmuhj.core.entity.GenericEntityLog;

/**
 * GenericCRUDAction
 * 
 * @author Roderick
 * @version 2015/01/19
 */
@SuppressWarnings("serial")
public abstract class GenericCRUDActionLog<T extends GenericEntityLog>
		extends GenericActionLog<T> {

	/**
	 * 儲存檢核
	 */
	protected abstract void validateSave() throws Exception;

	/**
	 * 修改檢核
	 */
	protected abstract void validateUpdate() throws Exception;

	/**
	 * 刪除檢核
	 */
	protected abstract void validateDelete() throws Exception;

	/**
	 * 編輯頁
	 * 
	 * @return
	 */
	public abstract String edit() throws Exception;

	/**
	 * List頁
	 * 
	 * @return
	 */
	public abstract String list() throws Exception;

	/**
	 * 儲存
	 * 
	 * @return
	 */
	public abstract String save() throws Exception;

	/**
	 * 修改
	 * 
	 * @return
	 */
	public abstract String update() throws Exception;

	/**
	 * 刪除
	 * 
	 * @return
	 */
	public abstract String delete() throws Exception;

}