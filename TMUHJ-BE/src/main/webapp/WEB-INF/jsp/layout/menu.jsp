<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="div-menu">
	<div id="div-menu-top"></div>
	<div id="div-menu-contain">

		<div id="menu-titles_1" class="menu-titles">
			<a onclick="showMenuItems('1');"><span
				class="menu-icon menu-icon-site">客戶管理</span></a>
		</div>
		<div id="menu_items_1" style="">
			<div class="menu-items">
				<a onclick="goURL('<%=request.getContextPath()%>/basic.action');">基本設定</a>
			</div>
		</div>


		<div id="menu-titles_2" class="menu-titles">
			<a onclick="showMenuItems('2');"><span class="menu-icon">帳戶管理</span></a>
		</div>
		<div id="menu_items_2" style="display: none;">
			<div class="menu-items">
				<a onclick="goURL('/TWBE/beAccount_init.action');">帳戶設定</a>
			</div>
		</div>


		<div id="menu-titles_3" class="menu-titles">
			<a onclick="showMenuItems('3');"><span class="menu-icon">書目資料管理</span></a>
		</div>
		<div id="menu_items_3" style="display: none;">

			<div class="menu-items">
				<a onclick="goURL('/TWBE/recommendedArticle_init.action');">推薦款目</a>
			</div>

			<div class="menu-items">
				<a onclick="goURL('/TWBE/news_init.action');">最新消息管理</a>
			</div>

			<div class="menu-items">
				<a onclick="goURL('/TWBE/advertisement_init.action');">廣告管理</a>
			</div>

		</div>

		<div id="menu-titles_4" class="menu-titles">
			<a onclick="showMenuItems('6');"><span class="menu-icon">統計資訊</span></a>
		</div>
		<div id="menu_items_6" style="display: none;">
			<div class="menu-items">
				<a onclick="goURL('/TWBE/viewcount_init.action');">款目點閱次數統計</a>
			</div>
			<div class="menu-items">
				<a onclick="goURL('/TWBE/loginCountStatics_init.action');">登入次數統計</a>
			</div>
			<div class="menu-items">
				<a onclick="goURL('/TWBE/keywordStatics_initQuery.action');">關鍵字檢索統計</a>
			</div>
			<div class="menu-items">
				<a onclick="goURL('/TWBE/mainSubjectStatics_init.action');">主題點閱次數統計</a>
			</div>

			<div class="menu-items">
				<a onclick="goURL('/TWBE/articleCollectStatics_init.action');">款目收藏次數統計</a>
			</div>

		</div>

	</div>
</div>