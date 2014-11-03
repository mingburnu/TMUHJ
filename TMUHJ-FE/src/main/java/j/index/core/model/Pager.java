package j.index.core.model;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author David Hsu
 * @version 2014/3/27
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Pager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6769777808559296049L;
	
	/**
	 * 每頁顯示筆數
	 */
	private Integer recordPerPage = 10;
	
	/**
	 * 當前頁數
	 */
	private Integer currentPage = 1;
	
	/**
	 * 總筆數
	 */
	private Long totalRecord;
	
	private Integer offset = 0;
	
	public Integer getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(Integer recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
