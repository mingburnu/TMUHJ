package com.asiaworld.tmuhj.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.util.EncryptorUtil;

public class Test {

	public static void main(String[] args) throws IOException {

		String[] sql = {
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	1	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.120.245.198	'	,	'	59.120.245.193	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	2	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	61.219.77.42	'	,	'	61.219.77.37	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	3	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.125.8.189	'	,	'	59.125.8.187	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	4	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.125.8.198	'	,	'	59.125.8.196	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	5	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.159	'	,	'	60.250.74.157	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	6	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.198	'	,	'	60.250.74.196	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	7	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.210	'	,	'	60.250.74.208	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	8	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	60.250.74.240	'	,	'	60.250.74.238	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	9	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	122.147.148.255	'	,	'	122.147.148.128	'	,	9	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	10	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.19.210	'	,	'	163.29.19.200	'	,	10	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	11	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	117.56.254	'	,	'	117.56.2.1	'	,	31	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	12	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.9.210	'	,	'	163.29.9.200	'	,	32	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	13	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.8.210	'	,	'	163.29.8.200	'	,	33	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	14	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.80.210	'	,	'	163.29.80.200	'	,	14	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	15	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.10.210	'	,	'	163.29.10.200	'	,	19	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	16	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.10.247	'	,	'	163.29.10.247	'	,	19	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	17	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	117.56.1.254	'	,	'	117.56.1.1	'	,	23	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	18	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	220.133.250.208	'	,	'	220.133.250.208	'	,	23	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	19	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.14.210	'	,	'	163.29.14.200	'	,	20	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	20	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.106.210	'	,	'	163.29.106.200	'	,	27	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	21	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.81.254	'	,	'	163.29.81.1	'	,	10	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	22	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.149.254	'	,	'	203.65.149.193	'	,	10	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	23	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.75.254	'	,	'	163.29.75.1	'	,	16	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	24	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.73.160	'	,	'	163.29.73.150	'	,	12	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	25	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.85.254	'	,	'	163.29.85.1	'	,	18	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	26	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.112.210 	'	,	'	163.29.112.200	'	,	21	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	27	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.108.151	'	,	'	163.29.108.110	'	,	22	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	28	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.114.254	'	,	'	163.29.114.1	'	,	24	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	29	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.107.254	'	,	'	163.29.107.1	'	,	36	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	30	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.111.200	'	,	'	163.29.111.200	'	,	28	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	31	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.99.99	'	,	'	163.29.99.89	'	,	30	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	32	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.11.254	'	,	'	163.29.11.1	'	,	11	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	33	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.7.210	'	,	'	163.29.7.200	'	,	11	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	34	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.69.125.20	'	,	'	210.69.125.1	'	,	13	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	35	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.77.210	'	,	'	163.29.77.200	'	,	17	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	36	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.113.200	'	,	'	163.29.113.200	'	,	25	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	37	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.116.254	'	,	'	163.29.116.1	'	,	26	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	38	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	163.29.109.254	'	,	'	163.29.109.1	'	,	29	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	39	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.125.248.253	'	,	'	59.125.248.250	'	,	29	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	40	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.241.120.69	'	,	'	210.241.120.60	'	,	35	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	41	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.241.100.254	'	,	'	210.241.100.254	'	,	39	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	42	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	211.79.180.127	'	,	'	211.79.180.1	'	,	8	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	43	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.241.14.158	'	,	'	210.241.14.128	'	,	8	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	44	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.72.254	'	,	'	203.65.71.1	'	,	1	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	45	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	59.120.22.254	'	,	'	59.120.22.1	'	,	1	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	46	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	201.69.111.249	'	,	'	201.69.111.249	'	,	2	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	47	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.69.214.41	'	,	'	210.69.214.41	'	,	37	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	48	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.114.33	'	,	'	203.65.114.33	'	,	5	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	49	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	117.56.56.130	'	,	'	117.56.56.130	'	,	5	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	50	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.102.254	'	,	'	203.65.102.1	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	51	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.103.254	'	,	'	203.65.103.1	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	52	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.104.254	'	,	'	203.65.1041	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	53	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.106.254	'	,	'	203.65.106.1	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	54	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.109.254	'	,	'	203.65.109.1	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	55	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	203.65.100.13	'	,	'	203.65.100.13	'	,	38	);	",
				"	INSERT INTO ip_range(serNo, cDTime, cUid, uDTime, uUid, ipRangeEnd, ipRangeStart, cusSerNo) VALUES	(	56	,	sysdate()	,	'admin'	,	sysdate()	,	'admin'	,	'	210.241.104.254	'	,	'	210.241.104.128	'	,	38	);	", };

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
			sql[i] = sql[i].replace(",   '		", ",   '");
			// System.out.println(sql[i]);
		}
		String str = "醫院";
		str = new String(str.getBytes("BIG5"), "UTF-8");
		AccountNumber a = new AccountNumber();
		Class<?> objClass = a.getClass();
		System.out.println(objClass.equals(AccountNumber.class));

		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");

		String encryptedPassword = EncryptorUtil.encrypt("MOHW");
		System.out.println(encryptedPassword);
		
		URL u = new URL ( "http://tw.ner.com");
		HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
		huc.setRequestMethod ("HEAD");  //OR  huc.setRequestMethod ("HEAD"); 
		huc.connect () ; 
		int code = huc.getResponseCode() ;
		System.out.println(code);
	}
}
