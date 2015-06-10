package com.asiaworld.tmuhj.core.apply.ipRange;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IpRangeAction extends GenericCRUDActionFull<IpRange> {

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

	@Autowired
	private CustomerService customerService;

	@Override
	protected void validateSave() throws Exception {
		String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if (StringUtils.isNotBlank(getEntity().getIpRangeStart())
				&& StringUtils.isNotBlank(getEntity().getIpRangeEnd())) {
			if (!Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeStart()).matches()) {
				errorMessages.add("IP起值輸入格式錯誤");
			}
			if (!Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeEnd()).matches()) {
				errorMessages.add("IP迄值輸入格式錯誤");
			}

			if (Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeStart()).matches()
					&& Pattern.compile(ipPattern)
							.matcher(getEntity().getIpRangeEnd()).matches()) {
				String[] ipStartNum = getEntity().getIpRangeStart()
						.split("\\.");
				String[] ipEndNum = getEntity().getIpRangeEnd().split("\\.");

				int i = 0;
				while (i < 2) {
					if (!ipStartNum[i].equals(ipEndNum[i])) {
						errorMessages.add("IP起迄值第" + (i + 1) + "位數字必須相同");
					}
					i++;
				}

				if (ipStartNum[0].equals(ipEndNum[0])
						&& ipStartNum[1].equals(ipEndNum[1])) {
					if (Integer.parseInt(ipStartNum[2]) * 1000
							+ Integer.parseInt(ipStartNum[3]) > Integer
							.parseInt(ipEndNum[2])
							* 1000
							+ Integer.parseInt(ipEndNum[3])) {
						errorMessages.add("IP起值不可大於IP迄值");
					} else {
						if (isPrivateIp(getEntity().getIpRangeStart(),
								getEntity().getIpRangeEnd())) {
							errorMessages.add("此IP區間為私有Ip");
						} else {
							IpRange repeatIpRange = checkRepeatIpRange(
									getEntity().getIpRangeStart(), getEntity()
											.getIpRangeEnd(),
									ipRangeService.getAllIpList(0));
							if (repeatIpRange != null) {
								errorMessages.add("此IP區間"
										+ repeatIpRange.getCustomer().getName()
										+ "正在使用");
							}
						}
					}
				}
			}
		} else {
			errorMessages.add("IP起迄值不可空白");
		}
	}

	@Override
	protected void validateUpdate() throws Exception {
		String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
				+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		if (!ipRangeService.isLegalEntity(initDataSet())) {
			errorMessages.add("Target must not be null");

			if (getEntity().getSerNo() == null) {
				getEntity().setSerNo(-1L);
			}
		}

		if (StringUtils.isNotBlank(getEntity().getIpRangeStart())
				&& StringUtils.isNotBlank(getEntity().getIpRangeEnd())) {
			if (!Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeStart()).matches()) {
				errorMessages.add("IP起值輸入格式錯誤");
			}
			if (!Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeEnd()).matches()) {
				errorMessages.add("IP迄值輸入格式錯誤");
			}

			if (Pattern.compile(ipPattern)
					.matcher(getEntity().getIpRangeStart()).matches()
					&& Pattern.compile(ipPattern)
							.matcher(getEntity().getIpRangeEnd()).matches()) {
				String[] ipStartNum = getEntity().getIpRangeStart()
						.split("\\.");
				String[] ipEndNum = getEntity().getIpRangeEnd().split("\\.");

				int i = 0;
				while (i < 2) {
					if (!ipStartNum[i].equals(ipEndNum[i])) {
						errorMessages.add("IP起迄值第" + (i + 1) + "位數字必須相同");
					}
					i++;
				}

				if (ipStartNum[0].equals(ipEndNum[0])
						&& ipStartNum[1].equals(ipEndNum[1])) {
					if (Integer.parseInt(ipStartNum[2]) * 1000
							+ Integer.parseInt(ipStartNum[3]) > Integer
							.parseInt(ipEndNum[2])
							* 1000
							+ Integer.parseInt(ipEndNum[3])) {
						errorMessages.add("IP起值不可大於IP迄值");
					} else {
						if (isPrivateIp(getEntity().getIpRangeStart(),
								getEntity().getIpRangeEnd())) {
							errorMessages.add("此IP區間為私有Ip");
						} else {
							IpRange repeatIpRange = checkRepeatIpRange(
									getEntity().getIpRangeStart(), getEntity()
											.getIpRangeEnd(),
									ipRangeService.getAllIpList(getEntity()
											.getSerNo()));

							if (repeatIpRange != null) {
								String name = repeatIpRange.getCustomer()
										.getName();
								errorMessages.add("此IP區間" + name + "正在使用");
							}
						}
					}
				}
			}
		} else {
			errorMessages.add("IP起迄值不可空白");
		}
	}

	@Override
	protected void validateDelete() throws Exception {
		if (getEntity().getSerNo() <= 0) {
			errorMessages.add("流水號不正確");
		} else if (!ipRangeService.isLegalEntity(initDataSet())) {
			errorMessages.add("沒有這個物件");
		}
	}

	@Override
	public String edit() throws Exception {
		if (getEntity().getSerNo() != null) {
			if (ipRangeService.isLegalEntity(initDataSet())) {
				ipRange.setListNo(getEntity().getListNo());
				setEntity(ipRangeService.getBySerNo(getEntity().getSerNo()));
			}
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<IpRange> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = ipRangeService.getByRestrictions(ds);

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		validateSave();
		setActionErrors(errorMessages);

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
		validateUpdate();
		setActionErrors(errorMessages);

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
		validateDelete();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			ipRangeService.deleteBySerNo(getEntity().getSerNo());
			DataSet<IpRange> ds = ipRangeService
					.getByRestrictions(initDataSet());
			setDs(ds);
			return LIST;
		} else {
			DataSet<IpRange> ds = ipRangeService
					.getByRestrictions(initDataSet());
			setDs(ds);
			return LIST;
		}
	}

	public boolean isRepeat(String ipStart, String ipEnd,
			List<IpRange> allIpList) {

		String[] ipStartNum = ipStart.split("\\.");
		String[] ipEndNum = ipEnd.split("\\.");
		List<Integer> entityIpRange = new ArrayList<Integer>();
		for (int i = Integer.parseInt(ipStartNum[2]) * 1000
				+ Integer.parseInt(ipStartNum[3]); i < Integer
				.parseInt(ipEndNum[2]) * 1000 + Integer.parseInt(ipEndNum[3]); i++) {
			entityIpRange.add(i);
		}

		for (int i = 0; i < allIpList.size(); i++) {
			String[] existIpStartNum = allIpList.get(i).getIpRangeStart()
					.split("\\.");
			String[] existIpEndNum = allIpList.get(i).getIpRangeStart()
					.split("\\.");
			if (ipStartNum[0].equals(existIpStartNum[0])
					&& ipStartNum[1].equals(existIpStartNum[1])) {

				for (int j = 0; j < entityIpRange.size(); j++) {
					if (entityIpRange.get(j) > Integer
							.parseInt(existIpStartNum[2])
							* 1000
							+ Integer.parseInt(existIpStartNum[3])
							&& entityIpRange.get(j) > Integer
									.parseInt(existIpEndNum[2])
									* 1000
									+ Integer.parseInt(existIpEndNum[3])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public IpRange checkRepeatIpRange(String ipStart, String ipEnd,
			List<IpRange> allIpList) {

		String[] ipStartNum = ipStart.split("\\.");
		String[] ipEndNum = ipEnd.split("\\.");
		List<Integer> entityIpRange = new ArrayList<Integer>();
		for (int i = Integer.parseInt(ipStartNum[2]) * 1000
				+ Integer.parseInt(ipStartNum[3]); i <= Integer
				.parseInt(ipEndNum[2]) * 1000 + Integer.parseInt(ipEndNum[3]); i++) {
			entityIpRange.add(i);
			if (i % 1000 == 255) {
				i = i - 255 + 999;
			}
		}

		for (int i = 0; i < allIpList.size(); i++) {
			String[] existIpStartNum = allIpList.get(i).getIpRangeStart()
					.split("\\.");
			String[] existIpEndNum = allIpList.get(i).getIpRangeEnd()
					.split("\\.");

			if (ipStartNum[0].equals(existIpStartNum[0])
					&& ipStartNum[1].equals(existIpStartNum[1])) {

				for (int j = 0; j < entityIpRange.size(); j++) {
					if (entityIpRange.get(j) >= Integer
							.parseInt(existIpStartNum[2])
							* 1000
							+ Integer.parseInt(existIpStartNum[3])
							&& entityIpRange.get(j) <= Integer
									.parseInt(existIpEndNum[2])
									* 1000
									+ Integer.parseInt(existIpEndNum[3])) {
						return allIpList.get(i);
					}

				}
			}
		}
		return null;
	}

	public boolean isPrivateIp(String ipStart, String ipEnd) {
		String[] ipStartNum = ipStart.split("\\.");
		String[] ipEndNum = ipEnd.split("\\.");

		// 10.0.0.0~10.255.255.255
		if (ipStartNum[0].equals("10") && ipEndNum[0].equals("10")) {
			return true;
		}

		// 172.16.0.0~172.31.255.255
		if (ipStartNum[0].equals("172") && ipEndNum[0].equals("172")) {
			if (Integer.parseInt(ipStartNum[1]) >= 16
					&& Integer.parseInt(ipStartNum[1]) <= 31) {
				return true;
			}
			if (Integer.parseInt(ipEndNum[1]) >= 16
					&& Integer.parseInt(ipEndNum[1]) <= 31) {
				return true;
			}
		}

		// 192.168.0.0~192.168.255.255
		if (ipStartNum[0].equals("192") && ipEndNum[0].equals("192")
				&& ipStartNum[0].equals("168") && ipEndNum[0].equals("168")) {
			return true;
		}

		return false;
	}
}