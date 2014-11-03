--accountNumber
insert into accountNumber(serNo, cUid, uUid, cDTime, uDTime, cus_serNo, userID, userName, userPW, role, status) values(1, 'admin', 'admin' , sysdate(), sysdate(), 1,'admin', 'administer', '8w5y4CYvLHP69kq5Wm2vHDVfPX1IOcrpskUugS/4KZN6budffcIYbfhpEL6HmNZ0', '系統管理員','生效');
insert into accountNumber(serNo, cUid, uUid, cDTime, uDTime, cus_serNo, userID, userName, userPW, role, status) values(2, 'admin', 'admin' , sysdate(), sysdate(), 5, 'user', 'Default User', '', '使用者','生效');

--journal
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(1, 'admin', 'admin' , sysdate(), sysdate(), '新英格蘭','New England', 123456, 1);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(2, 'admin', 'admin' , sysdate(), sysdate(), '自然','Nature', 654321, 3);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(3, 'admin', 'admin' , sysdate(), sysdate(), '科學','Science', 123321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(4, 'admin', 'admin' , sysdate(), sysdate(), '刺胳針','Lancet', 143325, 4);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(5, 'admin', 'admin' , sysdate(), sysdate(), '細胞','Cell', 124321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(6, 'admin', 'admin' , sysdate(), sysdate(), '美國國家科學院刊','Proceedings of the National Academy of Sciences, USA', 126321, 2);
insert into journal(serNo, cUid, uUid, cDTime, uDTime, chinesetitle, englishtitle, ISSN, version) values(7, 'admin', 'admin' , sysdate(), sysdate(), '生物化學期刊','Journal of Biological Chemistry', 129321, 2);

--database
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(1, 'admin', 'admin' , sysdate(), sysdate(), '歷史', 'History');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(2, 'admin', 'admin' , sysdate(), sysdate(), '醫學', 'Medcine');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(3, 'admin', 'admin' , sysdate(), sysdate(), '物理', 'Physics');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(4, 'admin', 'admin' , sysdate(), sysdate(), '數學', 'Math');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(5, 'admin', 'admin' , sysdate(), sysdate(), '化學', 'Chemistry');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(6, 'admin', 'admin' , sysdate(), sysdate(), '地理', 'Geography');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(7, 'admin', 'admin' , sysdate(), sysdate(), '法學', 'Law');
insert into db(serNo, cUid, uUid, cDTime, uDTime, DBchttitle, DBengtitle) values(8, 'admin', 'admin' , sysdate(), sysdate(), '生物', 'Biology');

--ebook
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(1, 'admin', 'admin' , sysdate(), sysdate(), 'Saxons',  777777, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(2, 'admin', 'admin' , sysdate(), sysdate(), 'Surgery', 888888, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(3, 'admin', 'admin' , sysdate(), sysdate(), 'Metal', 999999, 1, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(4, 'admin', 'admin' , sysdate(), sysdate(), 'Hiltler',  111111, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(5, 'admin', 'admin' , sysdate(), sysdate(), 'Edison', 888999, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(6, 'admin', 'admin' , sysdate(), sysdate(), 'Normans', 777999, 1, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(7, 'admin', 'admin' , sysdate(), sysdate(), 'Hans',  777444, 3, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(8, 'admin', 'admin' , sysdate(), sysdate(), 'Racist', 555888, 2, 'Cathy' , 'Arthur, Rodney');
insert into ebook(serNo, cUid, uUid, cDTime, uDTime, bookname, ISBN ,version, authername, authers) values(9, 'admin', 'admin' , sysdate(), sysdate(), 'Indians', 999000, 1, 'Cathy' , 'Arthur, Rodney');
--customer
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(1, 'admin', 'admin' , sysdate(), sysdate(), '碩睿資訊公司', 'SRISC', 'Roderick');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(2, 'admin', 'admin' , sysdate(), sysdate(), '國防醫學中心', 'NDMC', '陳小美');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(3, 'admin', 'admin' , sysdate(), sysdate(), '成功大學附設醫院', 'NCKUH', '陳桔根');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(4, 'admin', 'admin' , sysdate(), sysdate(), '台北醫學院附屬醫院', 'TMUH', '曾慧君');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(5, 'admin', 'admin' , sysdate(), sysdate(), '台北醫學院附屬醫院', 'TMUH', '訪客');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(6, 'admin', 'admin' , sysdate(), sysdate(), '中國醫藥大學附設醫院', 'CMUH', '董世勳');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(7, 'admin', 'admin' , sysdate(), sysdate(), '宏恩醫院', 'HEH', '郭啟文');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(8, 'admin', 'admin' , sysdate(), sysdate(), '馬偕紀念醫院', 'MMH', '鄞玉娟');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(9, 'admin', 'admin' , sysdate(), sysdate(), '長庚紀念醫院', 'CGMH', '林麗雯');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(10, 'admin', 'admin' , sysdate(), sysdate(), '佛教慈濟綜合醫院', 'BTCGH', '廖振智');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(11, 'admin', 'admin' , sysdate(), sysdate(), '高雄醫學院附設醫院', 'KMUH', '張大功');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(12, 'admin', 'admin' , sysdate(), sysdate(), '天主教耕莘醫療財團法人耕莘醫院', 'CTH', '許蕎麟');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(13, 'admin', 'admin' , sysdate(), sysdate(), '為恭紀念醫院', 'WGMH', '韓菱紗');
insert into customer(serNo, cUid, uUid, cDTime, uDTime, name, engName, contactUserName) values(14, 'admin', 'admin' , sysdate(), sysdate(), '台灣大學附設醫院', 'NTUH', '王大明');

--resourcesBuyers
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(1, 'admin', 'admin' , sysdate(), sysdate(),'1980', '2020', '買斷', '資料庫', '醫學', 'Medcine');
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(2, 'admin', 'admin' , sysdate(), sysdate(),'1980', '2020', '買斷', '資料庫', '醫學', 'Medcine');
insert into resourcesBuyers(serNo, cUid, uUid, cDTime, uDTime, startdate, maturitydate, Rcategory, Rtype, DBchttitle, DBengtitle) values(3, 'admin', 'admin' , sysdate(), sysdate(),'1980', '2020', 'RC1', 'RT3', '醫學', 'Medcine');
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