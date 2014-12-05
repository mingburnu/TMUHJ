--accountNumber
insert into accountNumber(serNo, cUid, uUid, cDTime, uDTime, cus_serNo, userID, userName, userPW, role, status) values(1, 'admin', 'admin', sysdate(), sysdate(), 1,'admin', 'administer', '8w5y4CYvLHP69kq5Wm2vHDVfPX1IOcrpskUugS/4KZN6budffcIYbfhpEL6HmNZ0', '系統管理員','生效');
insert into accountNumber(serNo, cUid, uUid, cDTime, uDTime, cus_serNo, userID, userName, userPW, role, status) values(2, 'admin', 'admin', sysdate(), sysdate(), 5, 'user', 'Default User', '8w5y4CYvLHP69kq5Wm2vHDVfPX1IOcrpskUugS/4KZN6budffcIYbfhpEL6HmNZ0', '使用者','生效');

--journal
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(1, 'admin', 'admin', sysdate(), sysdate(), '新英格蘭','New England', 123456, 1);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(2, 'admin', 'admin', sysdate(), sysdate(), '自然','Nature', 654321, 3);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(3, 'admin', 'admin', sysdate(), sysdate(), '科學','Science', 123321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(4, 'admin', 'admin', sysdate(), sysdate(), '刺胳針','Lancet', 143325, 4);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(5, 'admin', 'admin', sysdate(), sysdate(), '細胞','Cell', 124321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(6, 'admin', 'admin', sysdate(), sysdate(), '美國國家科學院刊','Proceedings of the National Academy of Sciences, USA', 126321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(7, 'admin', 'admin', sysdate(), sysdate(), '生物化學期刊','Journal of Biological Chemistry', 129321, 2);

--database
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(1, 'admin', 'admin', sysdate(), sysdate(), '歷史', 'History');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(2, 'admin', 'admin', sysdate(), sysdate(), '醫學', 'Medcine');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(3, 'admin', 'admin', sysdate(), sysdate(), '物理', 'Physics');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(4, 'admin', 'admin', sysdate(), sysdate(), '數學', 'Math');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(5, 'admin', 'admin', sysdate(), sysdate(), '化學', 'Chemistry');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(6, 'admin', 'admin', sysdate(), sysdate(), '地理', 'Geography');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(7, 'admin', 'admin', sysdate(), sysdate(), '法學', 'Law');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(8, 'admin', 'admin', sysdate(), sysdate(), '生物', 'Biology');

--ebook
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(1, 'admin', 'admin', sysdate(), sysdate(), 'Saxons',  777777, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(2, 'admin', 'admin', sysdate(), sysdate(), 'Surgery', 888888, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(3, 'admin', 'admin', sysdate(), sysdate(), 'Metal', 999999, 1, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(4, 'admin', 'admin', sysdate(), sysdate(), 'Hiltler',  111111, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(5, 'admin', 'admin', sysdate(), sysdate(), 'Edison', 888999, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(6, 'admin', 'admin', sysdate(), sysdate(), 'Normans', 777999, 1, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(7, 'admin', 'admin', sysdate(), sysdate(), 'Hans',  777444, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(8, 'admin', 'admin', sysdate(), sysdate(), 'Racist', 555888, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(9, 'admin', 'admin', sysdate(), sysdate(), 'Indians', 999000, 1, 'Cathy' , 'Arthur, Rodney');
--customer
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(1, 'admin', 'admin', sysdate(), sysdate(), '碩睿資訊公司', 'SRISC', 'Roderick');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(2, 'admin', 'admin', sysdate(), sysdate(), '國防醫學中心', 'NDMC', '陳小美');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(3, 'admin', 'admin', sysdate(), sysdate(), '成功大學附設醫院', 'NCKUH', '陳桔根');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(4, 'admin', 'admin', sysdate(), sysdate(), '台北醫學院附屬醫院', 'TMUH', '曾慧君');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(5, 'admin', 'admin', sysdate(), sysdate(), '台北醫學院附屬醫院', 'TMUH', '訪客');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(6, 'admin', 'admin', sysdate(), sysdate(), '中國醫藥大學附設醫院', 'CMUH', '董世勳');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(7, 'admin', 'admin', sysdate(), sysdate(), '宏恩醫院', 'HEH', '郭啟文');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(8, 'admin', 'admin', sysdate(), sysdate(), '馬偕紀念醫院', 'MMH', '鄞玉娟');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(9, 'admin', 'admin', sysdate(), sysdate(), '長庚紀念醫院', 'CGMH', '林麗雯');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(10, 'admin', 'admin', sysdate(), sysdate(), '佛教慈濟綜合醫院', 'BTCGH', '廖振智');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(11, 'admin', 'admin', sysdate(), sysdate(), '高雄醫學院附設醫院', 'KMUH', '張大功');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(12, 'admin', 'admin', sysdate(), sysdate(), '天主教耕莘醫療財團法人耕莘醫院', 'CTH', '許蕎麟');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(13, 'admin', 'admin', sysdate(), sysdate(), '為恭紀念醫院', 'WGMH', '韓菱紗');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(14, 'admin', 'admin', sysdate(), sysdate(), '台灣大學附設醫院', 'NTUH', '王大明');

--resourcesBuyers
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(1, 'admin', 'admin', sysdate(), sysdate(),'1980', '2020', '買斷', '資料庫', '醫學', 'Medcine');
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(2, 'admin', 'admin', sysdate(), sysdate(),'1980', '2020', '買斷', '資料庫', '醫學', 'Medcine');
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(3, 'admin', 'admin', sysdate(), sysdate(),'1980', '2020', 'RC1', 'RT3', '醫學', 'Medcine');
--resourcesUnion
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(1, 3, 1, 0, 0, 1);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(2, 3, 1, 0, 0, 2);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(3, 3, 1, 0, 0, 3);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(4, 3, 1, 0, 0, 4);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(5, 3, 1, 0, 0, 5);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(6, 3, 1, 0, 0, 6);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(7, 3, 1, 0, 0, 7);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(8, 2, 2, 0, 1, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(9, 2, 2, 0, 2, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(10, 2, 2, 0, 3, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(11, 2, 2, 0, 4, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(12, 2, 2, 0, 5, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(13, 2, 2, 0, 6, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(14, 2, 2, 0, 7, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(15, 4, 3, 1, 1, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(16, 4, 3, 2, 2, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(17, 4, 3, 3, 3, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(18, 4, 3, 4, 4, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(19, 4, 3, 5, 5, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(20, 4, 3, 6, 6, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(21, 4, 3, 7, 7, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(22, 4, 3, 8, 7, 0);
insert into resourcesUnion(serNo, cus_serNo, res_serNo, ebk_serNo, dat_serNo, jou_serNo) values(23, 4, 3, 9, 7, 0);

--ip_range
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('1',sysdate(),'admin',sysdate(),'admin','9','59.120.245.198','59.120.245.193');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('2',sysdate(),'admin',sysdate(),'admin','9','61.219.77.42','61.219.77.37');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('3',sysdate(),'admin',sysdate(),'admin','9','59.125.8.189','59.125.8.187');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('4',sysdate(),'admin',sysdate(),'admin','9','59.125.8.198','59.125.8.196');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('5',sysdate(),'admin',sysdate(),'admin','9','60.250.74.159','60.250.74.157');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('6',sysdate(),'admin',sysdate(),'admin','9','60.250.74.198','60.250.74.196');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('7',sysdate(),'admin',sysdate(),'admin','9','60.250.74.210','60.250.74.208');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('8',sysdate(),'admin',sysdate(),'admin','9','60.250.74.240','60.250.74.238');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('9',sysdate(),'admin',sysdate(),'admin','9','122.147.148.255','122.147.148.128');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('10',sysdate(),'admin',sysdate(),'admin','10','163.29.19.210','163.29.19.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('11',sysdate(),'admin',sysdate(),'admin','1','117.56.254','117.56.2.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('12',sysdate(),'admin',sysdate(),'admin','2','163.29.9.210','163.29.9.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('13',sysdate(),'admin',sysdate(),'admin','3','163.29.8.210','163.29.8.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('14',sysdate(),'admin',sysdate(),'admin','4','163.29.80.210','163.29.80.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('15',sysdate(),'admin',sysdate(),'admin','9','163.29.10.210','163.29.10.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('16',sysdate(),'admin',sysdate(),'admin','9','163.29.10.247','163.29.10.247');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('17',sysdate(),'admin',sysdate(),'admin','3','117.56.1.254','117.56.1.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('18',sysdate(),'admin',sysdate(),'admin','3','220.133.250.208','220.133.250.208');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('19',sysdate(),'admin',sysdate(),'admin','10','163.29.14.210','163.29.14.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('20',sysdate(),'admin',sysdate(),'admin','7','163.29.106.210','163.29.106.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('21',sysdate(),'admin',sysdate(),'admin','10','163.29.81.254','163.29.81.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('22',sysdate(),'admin',sysdate(),'admin','10','203.65.149.254','203.65.149.193');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('23',sysdate(),'admin',sysdate(),'admin','6','163.29.75.254','163.29.75.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('24',sysdate(),'admin',sysdate(),'admin','12','163.29.73.160','163.29.73.150');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('25',sysdate(),'admin',sysdate(),'admin','8','163.29.85.254','163.29.85.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('26',sysdate(),'admin',sysdate(),'admin','1','163.29.112.210 ','163.29.112.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('27',sysdate(),'admin',sysdate(),'admin','2','163.29.108.151','163.29.108.110');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('28',sysdate(),'admin',sysdate(),'admin','4','163.29.114.254','163.29.114.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('29',sysdate(),'admin',sysdate(),'admin','6','163.29.107.254','163.29.107.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('30',sysdate(),'admin',sysdate(),'admin','8','163.29.111.200','163.29.111.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('31',sysdate(),'admin',sysdate(),'admin','10','163.29.99.99','163.29.99.89');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('32',sysdate(),'admin',sysdate(),'admin','11','163.29.11.254','163.29.11.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('33',sysdate(),'admin',sysdate(),'admin','11','163.29.7.210','163.29.7.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('34',sysdate(),'admin',sysdate(),'admin','13','210.69.125.20','210.69.125.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('35',sysdate(),'admin',sysdate(),'admin','1','163.29.77.210','163.29.77.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('36',sysdate(),'admin',sysdate(),'admin','2','163.29.113.200','163.29.113.200');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('37',sysdate(),'admin',sysdate(),'admin','6','163.29.116.254','163.29.116.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('38',sysdate(),'admin',sysdate(),'admin','9','163.29.109.254','163.29.109.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('39',sysdate(),'admin',sysdate(),'admin','9','59.125.248.253','59.125.248.250');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('40',sysdate(),'admin',sysdate(),'admin','5','210.241.120.69','210.241.120.60');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('41',sysdate(),'admin',sysdate(),'admin','9','210.241.100.254','210.241.100.254');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('42',sysdate(),'admin',sysdate(),'admin','8','211.79.180.127','211.79.180.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('43',sysdate(),'admin',sysdate(),'admin','8','210.241.14.158','210.241.14.128');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('44',sysdate(),'admin',sysdate(),'admin','1','203.65.72.254','203.65.71.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('45',sysdate(),'admin',sysdate(),'admin','1','59.120.22.254','59.120.22.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('46',sysdate(),'admin',sysdate(),'admin','2','201.69.111.249','201.69.111.249');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('47',sysdate(),'admin',sysdate(),'admin','7','210.69.214.41','210.69.214.41');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('48',sysdate(),'admin',sysdate(),'admin','5','203.65.114.33','203.65.114.33');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('49',sysdate(),'admin',sysdate(),'admin','5','117.56.56.130','117.56.56.130');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('50',sysdate(),'admin',sysdate(),'admin','8','203.65.102.254','203.65.102.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('51',sysdate(),'admin',sysdate(),'admin','8','203.65.103.254','203.65.103.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('52',sysdate(),'admin',sysdate(),'admin','8','203.65.104.254','203.65.1041');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('53',sysdate(),'admin',sysdate(),'admin','8','203.65.106.254','203.65.106.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('54',sysdate(),'admin',sysdate(),'admin','8','203.65.109.254','203.65.109.1');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('55',sysdate(),'admin',sysdate(),'admin','8','203.65.100.13','203.65.100.13');
insert into ip_range (serNo, cDTime, cUid, uDTime, uUid, cusSerNo, ipRangeEnd, ipRangeStart) values('56',sysdate(),'admin',sysdate(),'admin','8','210.241.104.254','210.241.104.128');