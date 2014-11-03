package j.index.module.apply.ipRange.entity;

import j.index.core.entity.GenericEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "ip_range")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IpRange extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1606674601447344379L;

	// 用戶流水號
	private long cusSerNo;

	// IP開始
	private String ipRangeStart;

	// IP結束
	private String ipRangeEnd;

	/**
	 * @return the cusSerNo
	 */
	public long getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo
	 *            the cusSerNo to set
	 */
	public void setCusSerNo(long cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the ipRangeStart
	 */
	public String getIpRangeStart() {
		return ipRangeStart;
	}

	/**
	 * @param ipRangeStart
	 *            the ipRangeStart to set
	 */
	public void setIpRangeStart(String ipRangeStart) {
		this.ipRangeStart = ipRangeStart;
	}

	/**
	 * @return the ipRangeEnd
	 */
	public String getIpRangeEnd() {
		return ipRangeEnd;
	}

	/**
	 * @param ipRangeEnd
	 *            the ipRangeEnd to set
	 */
	public void setIpRangeEnd(String ipRangeEnd) {
		this.ipRangeEnd = ipRangeEnd;
	}

}
