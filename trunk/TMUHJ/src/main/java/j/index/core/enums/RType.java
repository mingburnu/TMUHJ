package j.index.core.enums;

/**
 * 資源種類
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public enum RType {
	/** 電子書. */
	電子書("電子書"),

	/** 期刊. */
	期刊("期刊"),

	/** 資料庫. */
	資料庫("資料庫");

	private String rType;

	private RType() {
	}

	private RType(String rType) {
		this.rType = rType;
	}

	public String getrType() {
		return rType;
	}
}
