<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
	var beforeMaxRows = "${beforeMaxRows}";
	var beforeRow = "${beforeRow}";
	var maxRows = 10;
	var cPrev = $('a#prev');
	var cNext = $('a#next');

	$('.list-table.queue').each(
			function() {
				var cTable = $(this);
				var cRows = cTable.find('tr:gt(0)');
				var cRowCount = cRows.size();

				if (cRowCount <= maxRows) {
					cPrev.addClass('disabled');
					cPrev.hide();
					cNext.addClass('disabled');
					cNext.hide();
				}

				if (cRowCount / maxRows > Math.floor(cRowCount / maxRows)) {
					$("span.totalNum.queue").html(
							Math.floor(cRowCount / maxRows) + 1);
					$("input#listForm_currentRowHeader").attr("max",
							Math.floor(cRowCount / maxRows) + 1);
				} else {
					$("span.totalNum.queue").html(
							Math.floor(cRowCount / maxRows));
				}

				cRows.filter(':gt(' + (maxRows - 1) + ')').hide();

				cPrev.addClass('disabled');
				cPrev.hide();

				cPrev.click(function() {
					if (cPrev.hasClass('disabled')) {
						cPrev.hide();
						return false;
					}

					allRow(0);

					var cFirstVisible = cRows.index(cRows.filter(':visible'));
					var currentPage = $("input#listForm_currentRowHeader")
							.val();
					$("input#listForm_currentRowHeader").val(
							parseInt(currentPage) - 1);

					cRows.hide();
					if (cFirstVisible - maxRows - 1 > 0) {
						cRows.filter(
								':lt(' + cFirstVisible + '):gt('
										+ (cFirstVisible - maxRows - 1) + ')')
								.show();
					} else {
						cRows.filter(':lt(' + cFirstVisible + ')').show();
					}

					if (cFirstVisible - maxRows <= 0) {
						cPrev.addClass('disabled');
						cPrev.hide();
					}

					cNext.removeClass('disabled');
					cNext.show();
					var recordPoint = ($("input#listForm_currentRowHeader")
							.val() - 1)
							* maxRows;
					$('#recordPoint').val(recordPoint);
				});

				cNext.click(function() {
					if (cNext.hasClass('disabled')) {
						cNext.hide();
						return false;
					}
					allRow(0);

					var cFirstVisible = cRows.index(cRows.filter(':visible'));
					var currentPage = $("input#listForm_currentRowHeader")
							.val();
					$("input#listForm_currentRowHeader").val(
							parseInt(currentPage) + 1);

					cRows.hide();
					cRows.filter(
							':lt(' + (cFirstVisible + 2 * maxRows) + '):gt('
									+ (cFirstVisible + maxRows - 1) + ')')
							.show();

					if (cFirstVisible + 2 * maxRows >= cRows.size()) {
						cNext.addClass('disabled');
						cNext.hide();
					}

					cPrev.removeClass('disabled');
					cPrev.show();
					var recordPoint = ($("input#listForm_currentRowHeader")
							.val() - 1)
							* maxRows;
					$('#recordPoint').val(recordPoint);
				});

				if (beforeMaxRows != null && beforeMaxRows != "") {
					maxRows = parseInt(beforeMaxRows);
					var row = parseInt(beforeRow);
					changeRowSize(maxRows, 0);
					gotoRow(row);
					$("input#listForm_currentRowHeader").val(row);
				}

			});

	function changeRowSize(row, recordRow) {
		allRow(0);
		clearCheckedItem();
		maxRows = parseInt(row);
		var cTable = $('.list-table.queue tbody').parent();
		var cRows = cTable.find('tr:gt(0)');
		var cRowCount = cRows.size();

		if (cRowCount / maxRows > Math.floor(cRowCount / maxRows)) {
			$("span.totalNum.queue").html(Math.floor(cRowCount / maxRows) + 1);
			$("input#listForm_currentRowHeader").attr("max",
					Math.floor(cRowCount / maxRows) + 1);
		} else {
			$("span.totalNum.queue").html(Math.floor(cRowCount / maxRows));
		}

		$("select#listForm_rowSize option:eq(0)").val(maxRows);
		$("select#listForm_rowSize option:eq(0)").html(maxRows);

		cRows.hide();

		var newRowPage = Math.floor(parseInt(recordRow) / maxRows + 1);
		$("input#listForm_currentRowHeader").val(newRowPage);
		$("input#listForm_currentRowHeader").attr("max",
				Math.floor(cRowCount / maxRows) + 1);
		var startOffset = maxRows * (newRowPage - 1);
		var endOffset = startOffset + maxRows + 1 - 1;
		cRows.filter(':eq(' + startOffset + ')').show();
		cRows.filter(':lt(' + endOffset + '):gt(' + startOffset + ')').show();

		if (startOffset == 0) {
			cPrev.addClass('disabled');
			cPrev.hide();
		} else {
			cPrev.removeClass('disabled');
			cPrev.show();
		}

		if (endOffset > cRows.length - 1) {
			cNext.addClass('disabled');
			cNext.hide();
		} else {
			cNext.removeClass('disabled');
			cNext.show();
		}
	}

	function gotoRow(row) {
		allRow(0);

		var max = $("input#listForm_currentRowHeader").attr("max");
		if (row<1 || row>parseInt(max)) {
			row = 1;
			$("input#listForm_currentRowHeader").val(row);
		}

		if (row > parseInt(max)) {
			row = parseInt(max);
			$("input#listForm_currentRowHeader").val(row);
		}

		var cTable = $('.list-table.queue tbody').parent();
		var cRows = cTable.find('tr:gt(0)');

		var cPrev = $('a#prev');
		var cNext = $('a#next');

		cRows.hide();
		var startOffset = (row - 1) * maxRows;
		var endOffset = (row - 1) * maxRows + maxRows + 1;
		cRows.filter(':eq(' + startOffset + ')').show();
		cRows.filter(':lt(' + endOffset + '):gt(' + startOffset + ')').show();

		if (startOffset == 0) {
			cPrev.addClass('disabled');
			cPrev.hide();
		} else {
			cPrev.removeClass('disabled');
			cPrev.show();
		}

		if (endOffset > cRows.length - 1) {
			cNext.addClass('disabled');
			cNext.hide();
		} else {
			cNext.removeClass('disabled');
			cNext.show();
		}

		var recordPoint = ($("input#listForm_currentRowHeader").val() - 1)
				* maxRows;
		$('#recordPoint').val(recordPoint);
	}

	function allRow(action) {
		if (action == 1) {
			checkedValues = new Array($(".checkbox.queue:visible").length);
			var importSerNos = "";
			$(".checkbox.queue:visible").each(
					function() {
						$(this).prop("checked", "checked");
						importSerNos = importSerNos + "importSerNos="
								+ $(this).val() + "&";
					});

			$
					.ajax({
						type : "POST",
						url : "<c:url value = '/'/>crud/apply.accountNumber.allCheckedItem.action",
						dataType : "html",
						data : importSerNos.slice(0, importSerNos.length - 1),
						success : function(message) {

						}
					});
		} else {
			clearCheckedItem();
			$(".checkbox.queue:visible").each(function() {
				$(this).removeAttr("checked");
			});
		}
	}

	function getCheckedItem(index) {
		$
				.ajax({
					type : "POST",
					url : "<c:url value = '/'/>crud/apply.accountNumber.getCheckedItem.action",
					dataType : "html",
					data : "importSerNo=" + index,
					success : function(message) {

					}
				});
	}

	function checkData() {
		//檢查資料是否已被勾選
		//進行動作
		if ($("input.checkbox.queue:checked").length > 0) {
			var nowRow = $("input#listForm_currentRowHeader").val();
			goDetail(
					"<c:url value = '/'/>crud/apply.accountNumber.importData.action?beforeMaxRows="
							+ maxRows + "&beforeRow=" + nowRow, '客戶-匯入', '');
		} else {
			goAlert("訊息", "請選擇一筆或一筆以上的資料");
		}
	}

	function clearCheckedItem() {
		$
				.ajax({
					type : "POST",
					url : "<c:url value = '/'/>crud/apply.accountNumber.clearCheckedItem.action",
					dataType : "html",
					success : function(message) {

					}
				});
	}
</script>
</head>
<body>
	<input type="hidden" value="0" id="recordPoint" />
	<s:form namespace="/crud" action="apply.accountNumber.importData">
		<table cellspacing="1" class="list-table queue">
			<tbody>
				<tr>
					<th></th>
					<c:forEach var="item" items="${excelWorkSheet.columns}"
						varStatus="status">
						<c:if test="${1 ne status.index }">
							<th>${item}</th>
						</c:if>
					</c:forEach>
					<th></th>
				</tr>
				<c:forEach var="item" items="${excelWorkSheet.data}"
					varStatus="status">
					<tr>
						<td><c:choose>
								<c:when test="${item.existStatus=='正常'}">
									<input type="checkbox" class="checkbox queue" name="checkItem"
										value="${status.index }"
										onclick="getCheckedItem('${status.index }')">
								</c:when>
								<c:otherwise>
									<input type="checkbox" disabled="disabled">
								</c:otherwise>
							</c:choose></td>
						<td>${item.userId }</td>
						<td>${item.userName }</td>
						<td>${item.customer.name }</td>
						<td>${item.role.role }</td>
						<td align="center">${item.existStatus }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</s:form>
	<div class="page-box" align="right">
		<table border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td><a class="state-default" id="prev">上一頁</a> &nbsp;&nbsp;<a
						class="state-default" id="next">下一頁</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>每頁顯示 <select name="recordPerPage" id="listForm_rowSize"
						onchange="changeRowSize(this.value,$('#recordPoint').val());">
							<option value="10">10</option>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
					</select> 筆紀錄, 第 <input id="listForm_currentRowHeader" value="1"
						type="number" min="1" max="" onchange="gotoRow(this.value)">
						頁, 共<span class="totalNum queue"></span>頁
					</td>
				</tr>

			</tbody>
		</table>
	</div>
	<div class="button_box">
		<div class="detail-func-button">
			<a class="state-default" onclick="allRow(1)">全選</a> <a
				class="state-default" onclick="allRow(0)">重置</a> <a
				class="state-default" onclick="closeDetail();">關閉</a> <a
				class="state-default" onclick="checkData()">確認</a>
		</div>
	</div>
	<div class="detail_note">
		<div class="detail_note_title">Note</div>
		<div class="detail_note_content">共${total }筆記錄(正常筆數 :${normal }
			;異常筆數 :${abnormal })</div>
	</div>
	<s:if test="hasActionErrors()">
		<script language="javascript" type="text/javascript">
			var msg = "";
			<s:iterator value="actionErrors">msg += '<s:property escape="false"/><br>';
			</s:iterator>;
			goAlert('訊息', msg);
		</script>
	</s:if>
</body>
</html>