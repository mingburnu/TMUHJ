package com.asiaworld.tmuhj.core.apply.ipRange;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.apply.enums.Role;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IpRangeAction extends GenericWebActionFull<IpRange> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3274723922938216387L;

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Override
	protected void validateSave() throws Exception {
		if (hasCustomer()) {
			if (getEntity().getCustomer().getSerNo() == 9) {
				if (!getLoginUser().getRole().equals(Role.系統管理員)) {
					errorMessages.add("權限不符");
				}
			}

			if (!isIp(getEntity().getIpRangeStart())) {
				errorMessages.add("IP起值輸入格式錯誤");
			}

			if (!isIp(getEntity().getIpRangeEnd())) {
				errorMessages.add("IP起值輸入格式錯誤");
			}

			if (isIp(getEntity().getIpRangeStart())
					&& isIp(getEntity().getIpRangeEnd())) {
				String[] ipStartNum = getEntity().getIpRangeStart()
						.split("\\.");
				String[] ipEndNum = getEntity().getIpRangeEnd().split("\\.");

				int i = 0;
				while (i < 2) {
					if (Integer.parseInt(ipStartNum[i]) != Integer
							.parseInt(ipEndNum[i])) {
						errorMessages.add("IP起迄值第" + (i + 1) + "位數字必須相同");
					}
					i++;
				}

				if (Integer.parseInt(ipStartNum[0]) == Integer
						.parseInt(ipEndNum[0])
						&& Integer.parseInt(ipStartNum[1]) == Integer
								.parseInt(ipEndNum[1])) {
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
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void validateUpdate() throws Exception {
		if (hasCustomer()) {
			if (ipRangeService.getTargetEntity(initDataSet()) == null) {
				errorMessages.add("Target must not be null");
				getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				if (getEntity().getCustomer().getSerNo() == 9) {
					if (!getLoginUser().getRole().equals(Role.系統管理員)) {
						errorMessages.add("權限不符");
					}
				}

				if (!isIp(getEntity().getIpRangeStart())) {
					errorMessages.add("IP起值輸入格式錯誤");
				}

				if (!isIp(getEntity().getIpRangeEnd())) {
					errorMessages.add("IP起值輸入格式錯誤");
				}

				if (isIp(getEntity().getIpRangeStart())
						&& isIp(getEntity().getIpRangeEnd())) {
					String[] ipStartNum = getEntity().getIpRangeStart().split(
							"\\.");
					String[] ipEndNum = getEntity().getIpRangeEnd()
							.split("\\.");

					int i = 0;
					while (i < 2) {
						if (Integer.parseInt(ipStartNum[i]) != Integer
								.parseInt(ipEndNum[i])) {
							errorMessages.add("IP起迄值第" + (i + 1) + "位數字必須相同");
						}
						i++;
					}

					if (Integer.parseInt(ipStartNum[0]) == Integer
							.parseInt(ipEndNum[0])
							&& Integer.parseInt(ipStartNum[1]) == Integer
									.parseInt(ipEndNum[1])) {
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
										getEntity().getIpRangeStart(),
										getEntity().getIpRangeEnd(),
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
			}
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void validateDelete() throws Exception {
		if (hasCustomer()) {
			if (ipRangeService.getTargetEntity(initDataSet()) == null) {
				errorMessages.add("沒有這個物件");
			} else {
				if (getEntity().getCustomer().getSerNo() == 9) {
					if (!getLoginUser().getRole().equals(Role.系統管理員)) {
						errorMessages.add("權限不符");
					}
				}
			}
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	public String add() throws Exception {
		if (!hasCustomer()) {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return ADD;
	}

	@Override
	public String edit() throws Exception {
		if (hasCustomer()) {
			ipRange = ipRangeService.getTargetEntity(initDataSet());
			if (ipRange != null) {
				ipRange.setListNo(getEntity().getListNo());
				setEntity(ipRange);
			} else {
				getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		if (hasCustomer()) {
			DataSet<IpRange> ds = ipRangeService
					.getByRestrictions(initDataSet());

			if (ds.getResults().size() == 0
					&& ds.getPager().getCurrentPage() > 1) {
				ds.getPager().setCurrentPage(
						(int) Math.ceil(ds.getPager().getTotalRecord()
								/ ds.getPager().getRecordPerPage()));
				ds = ipRangeService.getByRestrictions(ds);
			}

			setDs(ds);
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		return LIST;
	}

	@Override
	public String save() throws Exception {
		validateSave();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			String[] ipStartNums = getEntity().getIpRangeStart().split("\\.");
			String[] ipEndNums = getEntity().getIpRangeEnd().split("\\.");

			StringBuilder ipStartBuilder = new StringBuilder();
			StringBuilder ipEndBuilder = new StringBuilder();

			int i = 0;
			while (i < 4) {
				if (i < 3) {
					ipStartBuilder.append(Integer.parseInt(ipStartNums[i])
							+ ".");
					ipEndBuilder.append(Integer.parseInt(ipEndNums[i]) + ".");
				} else {
					ipStartBuilder.append(Integer.parseInt(ipStartNums[i]));
					ipEndBuilder.append(Integer.parseInt(ipEndNums[i]));
				}

				i++;
			}

			getEntity().setIpRangeStart(ipStartBuilder.toString());
			getEntity().setIpRangeEnd(ipEndBuilder.toString());

			ipRange = ipRangeService.save(getEntity(), getLoginUser());
			setEntity(ipRange);
			addActionMessage("新增成功");
			return VIEW;
		} else {
			return ADD;
		}
	}

	@Override
	public String update() throws Exception {
		validateUpdate();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			String[] ipStartNums = getEntity().getIpRangeStart().split("\\.");
			String[] ipEndNums = getEntity().getIpRangeEnd().split("\\.");

			StringBuilder ipStartBuilder = new StringBuilder();
			StringBuilder ipEndBuilder = new StringBuilder();

			int i = 0;
			while (i < 4) {
				if (i < 3) {
					ipStartBuilder.append(Integer.parseInt(ipStartNums[i])
							+ ".");
					ipEndBuilder.append(Integer.parseInt(ipEndNums[i]) + ".");
				} else {
					ipStartBuilder.append(Integer.parseInt(ipStartNums[i]));
					ipEndBuilder.append(Integer.parseInt(ipEndNums[i]));
				}

				i++;
			}

			getEntity().setIpRangeStart(ipStartBuilder.toString());
			getEntity().setIpRangeEnd(ipEndBuilder.toString());

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
			list();
			return LIST;
		} else {
			list();
			return LIST;
		}
	}

	protected boolean isIp(String ip) {
		return ESAPI.validator().isValidInput("IpRange ip", ip, "IPAddress",
				15, false);
	}

	protected boolean isPrivateIp(String ipStart, String ipEnd) {
		String[] ipStartNum = ipStart.split("\\.");
		String[] ipEndNum = ipEnd.split("\\.");

		// 10.0.0.0~10.255.255.255
		if (Integer.parseInt(ipStartNum[0]) == 10
				&& Integer.parseInt(ipEndNum[0]) == 10) {
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

	protected IpRange checkRepeatIpRange(String ipStart, String ipEnd,
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

	protected boolean hasCustomer() throws Exception {
		if (getEntity().getCustomer() == null
				|| !getEntity().getCustomer().hasSerNo()
				|| getEntity().getCustomer().getSerNo() <= 0) {
			return false;
		} else {
			customer = customerService.getBySerNo(getEntity().getCustomer()
					.getSerNo());
			if (customer == null) {
				return false;
			} else {
				return true;
			}
		}
	}
}
