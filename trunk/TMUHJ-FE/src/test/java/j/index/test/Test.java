package j.index.test;

import j.index.core.util.EncryptorUtil;

import java.io.UnsupportedEncodingException;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {

		String[] sql = {
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	1	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.120.245.198	'	,	'	59.120.245.193	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	2	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	61.219.77.42	'	,	'	61.219.77.37	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	3	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.125.8.189	'	,	'	59.125.8.187	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	4	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.125.8.198	'	,	'	59.125.8.196	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	5	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.159	'	,	'	60.250.74.157	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	6	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.198	'	,	'	60.250.74.196	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	7	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.210	'	,	'	60.250.74.208	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	8	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.240	'	,	'	60.250.74.238	'	,	9	);	"	,
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	9	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	122.147.148.255	'	,	'	122.147.148.128	'	,	9	);	"	,
};
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sql[i].replace("' 	 ');", "'');");
			sql[i] = sql[i].replace("	', ", "', ");
			sql[i] = sql[i].replace(",   '	", ",   '");
			sql[i] = sql[i].replace("	 ',", "',");
			sql[i] = sql[i].replace(",	'	", ",	'");
			sql[i] = sql[i].replace("'	 ');", "'');");
			sql[i] = sql[i].replace(" ',   '", "',   '");
			sql[i] = sql[i].replace("', '	", "', '");
			sql[i] = sql[i].replace("	',	);", "',);");
			sql[i] = sql[i].replace("　	'	);", "'	);");
			sql[i] = sql[i].replace("	'	);", "');");
			sql[i] = sql[i].replace(")	,'	", "), '");
			sql[i] = sql[i].replace("	',", "',");
			sql[i] = sql[i].replace("	');", "');");
			sql[i] = sql[i].replace("',	", "', ");
			sql[i] = sql[i].replace("	'	,	'", "','");	
			sql[i] = sql[i].replace("	'	,", "'	,");
			System.out.println(sql[i]);
		}
		
		String a=EncryptorUtil.encrypt("(OL>!QAZ");
		String b=EncryptorUtil.encrypt("(OL>!QAZ");
		boolean c=EncryptorUtil.checkPassword("a", "a");
		
		System.out.println(c);
	}
}
