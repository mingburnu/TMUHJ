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
	var maxRows = 10;
	$('.list-table.queue').each(
			function() {
				var cTable = $(this);
				var cRows = cTable.find('tr:gt(0)');
				var cRowCount = cRows.size();

				if (cRowCount / maxRows > Math.floor(cRowCount / maxRows)) {
					$("span.totalNum.queue").html(
							Math.floor(cRowCount / maxRows) + 1);
					$("input#listForm_currentRowHeader").attr("max",
							Math.floor(cRowCount / maxRows) + 1);
				} else {
					$("span.totalNum.queue").html(
							Math.floor(cRowCount / maxRows));
				}

				if (cRowCount < maxRows) {
					return;
				}

				cRows.filter(':gt(' + (maxRows - 1) + ')').hide();

				var cPrev = $('a#prev');
				var cNext = $('a#next');

				cPrev.addClass('disabled');
				cPrev.hide();

				cPrev.click(function() {
					var cFirstVisible = cRows.index(cRows.filter(':visible'));
					var currentPage = $("input#listForm_currentRowHeader")
							.val();
					$("input#listForm_currentRowHeader").val(
							parseInt(currentPage) - 1);
					if (cPrev.hasClass('disabled')) {
						return false;
					}

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
					return false;
				});

				cNext.click(function() {
					var cFirstVisible = cRows.index(cRows.filter(':visible'));
					var currentPage = $("input#listForm_currentRowHeader")
							.val();
					$("input#listForm_currentRowHeader").val(
							parseInt(currentPage) + 1);
					if (cNext.hasClass('disabled')) {
						cNext.hide();
						return false;
					}

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
					return false;
				});

			});

	function changeRowSize(row, recordRow) {
		maxRows = parseInt(row);
		var cTable = $('.list-table.queue tbody').parent();
		var cRows = cTable.find('tr:gt(0)');
		var cRowCount = cRows.size();

		var cPrev = $('a#prev');
		var cNext = $('a#next');

		if (cRowCount / maxRows > Math.floor(cRowCount / maxRows)) {
			$("span.totalNum.queue").html(Math.floor(cRowCount / maxRows) + 1);
			$("input#listForm_currentRowHeader").attr("max",
					Math.floor(cRowCount / maxRows) + 1);
		} else {
			$("span.totalNum.queue").html(Math.floor(cRowCount / maxRows));
		}

		$("select#listForm_rowSize option:eq(0)").html(row);
		$("select#listForm_rowSize option:eq(0)").val(row);

		cRows.hide();

		var newRowPage = Math.floor(parseInt(recordRow) / maxRows + 1);
		$("input#listForm_currentRowHeader").val(newRowPage);
		$("input#listForm_currentRowHeader").attr("max",
				Math.floor(cRowCount / maxRows) + 1);
		var startOffset = maxRows * (newRowPage - 1);
		var endOffset = startOffset + maxRows + 1;
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
		var max = $("input#listForm_currentRowHeader").attr("max");
		if (row<1 || row>parseInt(max)) {
			return false;
		}

		var cTable = $('.list-table.queue tbody').parent();
		var cRows = cTable.find('tr:gt(0)');

		var cPrev = $('a#prev');
		var cNext = $('a#next');

		cRows.hide();
		var startOffset = (row - 1) * maxRows;
		var endOffset = (row - 1) * maxRows + maxRows + 1;
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
</script>
</head>
<body>
	<input type="hidden" value="0" id="recordPoint" />
	<table cellspacing="1" class="list-table queue">
		<tbody>
			<tr>
				<th></th>
				<c:forEach var="item" items="${excelWorkSheet.columns}"
					varStatus="status">
					<c:if
						test="${(1 eq status.index) || (3 eq status.index)||(11 eq status.index)||(15 eq status.index)}">
						<th>${item}</th>
					</c:if>
				</c:forEach>
				<th></th>
			</tr>
			<c:forEach var="item" items="${excelWorkSheet.data}"
				varStatus="status">
				<tr>
					<!--<td align="center" class="td_first" nowrap><input
						type="checkbox" class="checkbox" name="checkItem"
						value="${item.serNo}"></td>-->
					<td><input type="checkbox" class="checkbox" name="checkItem"
						value=""></td>
					<td>${item.englishTitle }</td>
					<td>${item.issn }</td>
					<td>${item.resourcesBuyers.rCategory.category }</td>
					<td align="center"><c:forEach var="customer"
							items="${item.customers}" varStatus="status">
				${customer.name }
				</c:forEach></td>
					<td align="center">${item.existStatus }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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
			<a class="state-default" onclick="clearDetail_2();closeDetail();">取消</a>
			&nbsp;<a class="state-default" onclick="resetData();">重設</a>&nbsp; <a
				class="state-default" onclick="clearDetail_2();submitData();">確認</a>
		</div>
	</div>
	<div class="detail_note">
		<div class="detail_note_title">Note</div>
		<div class="detail_note_content">
			<span class="required">(•)</span>為必填欄位
		</div>
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