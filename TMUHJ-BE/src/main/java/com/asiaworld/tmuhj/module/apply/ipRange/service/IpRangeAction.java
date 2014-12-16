package com.asiaworld.tmuhj.module.apply.ipRange.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRange;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IpRangeAction extends GenericCRUDActionFull<IpRange> {

	@Autowired
	IpRange ipRange;

	@Autowired
	IpRangeService ipRangeService;

	@Override
	public void validateSave() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String query() throws Exception {
		if (getEntity().getSerNo() != null) {
			ipRange = ipRangeService.getBySerNo(getEntity().getSerNo());
			ipRange.setListNo(getEntity().getListNo());
			setEntity(ipRange);
		} else {
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<IpRange> ds = ipRangeService.getByRestrictions(initDataSet());
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if (!Pattern.compile(ipPattern).matcher(getEntity().getIpRangeStart())
				.matches()) {
			addActionError("IP起值輸入格式錯誤");
		}
		if (!Pattern.compile(ipPattern).matcher(getEntity().getIpRangeEnd())
				.matches()) {
			addActionError("IP迄值輸入格式錯誤");
		}

		String[] ipStartNum = getEntity().getIpRangeStart().split("\\.");
		String[] ipEndNum = getEntity().getIpRangeEnd().split("\\.");

		int i = 0;
		while (i < 3) {
			if (!ipStartNum[i].equals(ipEndNum[i])) {
				addActionError("IP起迄值第" + (i + 1) + "位數字必須相同");
			}
			i++;
		}

		if (Integer.parseInt(ipStartNum[3]) > Integer.parseInt(ipEndNum[3])) {
			addActionError("IP起值第4位數字不可大於IP迄值第4位數字");
		}

		if (!hasActionErrors()) {
			ipRange = ipRangeService.save(getEntity(), getLoginUser());
			addActionMessage("新增成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if (!Pattern.compile(ipPattern).matcher(getEntity().getIpRangeStart())
				.matches()) {
			addActionError("IP起值輸入格式錯誤");
		}
		if (!Pattern.compile(ipPattern).matcher(getEntity().getIpRangeEnd())
				.matches()) {
			addActionError("IP迄值輸入格式錯誤");
		}

		String[] ipStartNum = getEntity().getIpRangeStart().split("\\.");
		String[] ipEndNum = getEntity().getIpRangeEnd().split("\\.");

		int i = 0;
		while (i < 3) {
			if (!ipStartNum[i].equals(ipEndNum[i])) {
				addActionError("IP起迄值第" + (i + 1) + "個數字必須相同");
			}
			i++;
		}

		if (Integer.parseInt(ipStartNum[3]) > Integer.parseInt(ipEndNum[3])) {
			addActionError("IP起值第4位數字不可大於IP迄值第4位數字");
		}

		if (!hasActionErrors()) {
			ipRange = ipRangeService.update(getEntity(), getLoginUser());
			ipRange.setListNo(getEntity().getListNo());
			setEntity(ipRange);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		ipRangeService.deleteBySerNo(getEntity().getSerNo());
		DataSet<IpRange> ds = ipRangeService.getByRestrictions(initDataSet());
		setDs(ds);
		return LIST;
	}

}
