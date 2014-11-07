/**
 * @Author: Dante
 * @Dependency: jquery-1.4.2.js
 */
// JS處理
$(document).ready(function(){
    // loading 的區塊定義
    var elmt = document.createElement("div");
    elmt.innerHTML = '<div id="div_Loading" style="display:none;"><div class="overlay"></div></div>';
    document.body.appendChild(elmt.firstChild);
});

/**
 * 最主要用來進行 ajax 的函式
 * 將系統等級的錯誤與執行成功、失敗的情況切割開來
 */
function ajaxPost(args)
{
    showLoading();

    $.ajax({
        type: "POST",
        url: args.url,
        data: args.data,
        timeout: 60000, // 60秒
        
        error: function (msg, status) {
            try
            {
                if( status == "timeout")
                    myAlert("伺服器連線逾時，請稍候再嘗試。");
                else if( msg.responseText.indexOf("拒絕存取資源") != -1)
                    myAlert("您沒有權限使用此功能。");
                else if( msg.responseText.length == 0)
                    myAlert("伺服器無回應，請稍候再嘗試。"); // IE within no response
                else
                    myAlert("系統錯誤：\n" + msg.responseText);
            }
            catch(e)
            {
                myAlert("系統錯誤：\n" + e);
            }
        },

        success: function (msg) {
            
            if (msg.length == 0)
            {
                myAlert("伺服器無回應，請稍候再嘗試。"); // Firefox within no response
            }
            else if (msg.nodeName) // XML
            {
                if (msg.documentElement.nodeName != 'msg' && args.failure)
                    args.failure(msg.documentElement.firstChild.data);
                else
                    args.success(msg.documentElement.firstChild.data);
            }
            else if (msg.indexOf("<title>登入</title>") != -1)
            {
                myAlert("連線逾時，請重新登入。");
                location.reload();
            }
            else
            {
                args.success(msg);
            }
        },

        complete: function () {
            // Running when the request is finished (after success and error functions).
            closeLoading();
        }
    });
}

/**
 * 延遲呼叫函式
 * 後來呼叫的會蓋過前面呼叫的
 */
var refer_delay = null;
function delayLaunch (millisec, code)
{
    if (refer_delay != null)
    {
        // 已有在等待的
        clearTimeout(refer_delay);
        refer_delay = null;
    }

    // 傳遞字串函數時的運作
    if (typeof code == "string")
    {
        refer_delay = setTimeout (function() {
            eval(code);
            refer_delay = null;
        }
        ,millisec);
    }
    else if (typeof code == "function")
    {
        // 傳遞函數物件時的運作
        // 將 code 之後的參數轉為提供給 code 的參數
        var argArray = [];
        for( var c = 2; c < arguments.length; c++)
            argArray.push(arguments[c]);

        refer_delay = setTimeout (function() {
            code.apply (this, argArray);
            refer_delay = null;
        }
        ,millisec);
    }
}

/* 子視窗公用程式
------------------------------------------------------*/
var SUB_WINDOW = "AMS_SUB_WINDOW";
var PROBLEM_WINDOW = "AMS_PROBLEM_WINDOW";
var COMPARISON_WINDOW = "AMS_COMPARISON_WINDOW";

// 開啟修改密碼視窗
function openModifyPasswdForm()
{
    var param = "width=300,height=200,status=no,location=no,menubar=no,toolbar=no,resizable=no";
    openWindow("passwd_modifyForm.action", param);
}

function management(entity, tjcdCode)
{
    var action = null;
    var param;
    
    if (entity == 'author')
        action = 'author_list.action';
    else if (entity == 'keyword')
        action = 'keyword_list.action';
    else if (entity == 'reference')
    {
        action = 'reference_list.action';
        param = "width=750,height=765,status=no,location=no,menubar=no,toolbar=no,scrollbars=yes,resizable=yes";
    }
    else if (entity == 'paper')
        action = 'paperfbUpdateForm.action';

    if (action != null)
    {
        var argData = "?tjcdCode=" + encodeURIComponent(tjcdCode);
        openWindow(action + argData, param);
    }
    else
    {
        alert("未定義的功能");
    }
}

// 另開新視窗
function openWindow( url, winParam)
{
    var param = "width=750,height=480,status=no,location=no,menubar=no,toolbar=no,scrollbars=yes,resizable=yes";

    if ( winParam)
        param = winParam;

    window.open( url, SUB_WINDOW, param).focus();
}

// 開啟問題單視窗(新增)
function openProblemForm( tjcdCode, status)
{
    // 先儲存
    send(function(e) {
        var param = "width=500,height=520,status=no,location=no,menubar=no,toolbar=no,resizable=yes";
        tjcdCode = encodeURIComponent(tjcdCode);
        var windowRefer = window.open( "problem_form.action?tjcdCode="+tjcdCode+"&status="+status, PROBLEM_WINDOW, param);
        windowRefer.focus();

        window.onunload = function(e) {
            if(!windowRefer.closed)
                windowRefer.close();
        };
    });
}
// 開啟問題單視窗(檢視)
function openProblemView( serno)
{
    var param = "width=500,height=250,status=no,location=no,menubar=no,toolbar=no,resizable=yes,scrollbars=yes";
    var windowRefer = window.open( "problem_view.action?fkProblemSerno="+serno, PROBLEM_WINDOW, param);
    windowRefer.focus();

    window.onunload = function(e) {
        if(!windowRefer.closed)
            windowRefer.close();
    };
}

/* 列表功能公用程式
------------------------------------------------------*/
var _listForm = null;
var registerListForm = function(id, initStyle)
{
    _listForm = $("#" + id);
    if (initStyle)
        initSortStyle();
}

// 設定排序樣式
function initSortStyle()
{
    var sortBy = _listForm.find("input[name='sortBy']").val();
    var asc = _listForm.find("input[name='asc']").val() == 'true'?true:false;

    if( sortBy.length > 0)
    {
        // var header = $(jms_list_form).find("#" + sortBy);
        var header = $(".table_browse").find("#" + sortBy);
        if( asc)
            header.addClass('h_sort_asc');
        else
            header.addClass('h_sort_desc');
    }
    
    // var sortableAnchor = $(jms_list_form).find("a[name='sortable']");
    var sortableAnchor = $(".table_browse a[name='sortable']");
    var sortName;
    
    for( var c = 0; c < sortableAnchor.length; c++)
    {
        //alert(sortableAnchor[c].id);
        sortName = sortableAnchor[c].id;
        sortableAnchor[c].href = "javascript:sort('"+sortName+"')";
    }
}
// 排序
function sort( name)
{
    showLoading();
    var sortByInput = _listForm.find("input[name='sortBy']");
    var ascInput = _listForm.find("input[name='asc']");

    // 升、降冪判斷
    if( sortByInput.val() == name)
    {
        if( ascInput.val() == 'false')
            ascInput.val('true');
        else
            ascInput.val('false');
    }
    else
        ascInput.val('false');

    sortByInput.val(name);
    _listForm.find("input[name='p']").val(1);
    _listForm.submit();
}
// 換頁
function goPage( p)
{
    showLoading();
    _listForm.find("input[name='p']").val(p);
    _listForm.submit();
}
// 變更每頁筆數
function pageSize( size)
{
    showLoading();
    _listForm.find("input[name='pageSize']").val(size);
    _listForm.submit();
}
// 篩選
function filterBy( sieve)
{
    showLoading();
    _listForm.find("input[name='sieve']").val(sieve);
    _listForm.find("input[name='p']").val(1);
    _listForm.submit();
}

/* 一般公用函式
------------------------------------------------------*/
// 權重說明
function weightDescription(arg1)
{
    var description = [] ;
    description["0"] = "停刊";
    description["1"] = "";
    description["2"] = "TSSCI*";
    description["3"] = "TSSCI";

    var jQueryObj = $(arg1);
    var weigth = jQueryObj.val();

    if( description[weigth])
        jQueryObj.next().html(description[weigth]);
    else
        jQueryObj.next().html("");
}

// 表單重置
function reset()
{
    var obj = $("form");
    for( var c = 0; c < obj.length; c++)
        $("form")[c].reset();
}
// 全選 (List table)
function selectAll(checked)
{
    var checkBox = $("input[name='cbx1']");
    for( var c = 0; c < checkBox.length; c++)
        checkBox[c].checked = checked; // 簡易寫法
}
// 非數字遮罩
function numberMask( jQueryObj)
{
    jQueryObj.keypress( function(e) {
        if( e.which < 48 || e.which > 57) {
            if( e.which != 8 && e.which != 0)
                e.preventDefault();
        }
    });
}

function arr2Str (arr)
{
    if (arr == null || arr.length == 0)
        return "";

    var str = "";
    for (var c = 0; c < arr.length; c++)
    {
        str += arr[c] + "；";
    }
    return str.substring(0, str.length -1);
}
//
function myAlert( msg)
{
    window.alert(msg);
}
// 字串去頭尾空白
function trim( str)
{
    if( str)
    {
        var reg1 =/^\s+/;
	var reg2 =/\s+$/;
	str = str.replace(reg1, '').replace(reg2, '');
    }

    return str;
}

// 移除字串中的html tag
function stripHtml (str)
{
    var tmp = document.createElement("DIV");
    tmp.innerHTML = str;
    str = tmp.textContent||tmp.innerText;
    tmp = null;

    // trim \n in head and end
    var reg1 =/^\n+/;
    var reg2 =/\n+$/;
    str = str.replace(reg1, '').replace(reg2, '');

    return str;
}

// loading 區塊功能
$(document).ready(function(){
    UI_Resize();
});

//UI_Resize
function UI_Resize(){
    $("#div_Loading > .overlay").css("width",$(window).width());
    $("#div_Loading > .overlay").css("height",$(window).height());
}
$(window).resize(function(){
    UI_Resize();
});
//UI_Scroll
function UI_Scroll(){
    $("#div_Loading > .overlay").css("top",$(window).scrollTop());
    $("#div_Loading > .overlay").css("left",$(window).scrollLeft());
}
$(window).scroll(function(){
    UI_Scroll();
});
function showLoading()
{
    $("#div_Loading > .overlay").css("opacity","0");
    $("#div_Loading > .overlay").animate({opacity:"0.5"},'fast','linear');
    $("#div_Loading").show();
}
function closeLoading(){
    $("#div_Loading > .overlay").animate({opacity:"0"},'fast','linear');
    $("#div_Loading").hide();
}

/* YUI2 Rich Text Editor
------------------------------------------------------*/
// 註冊自訂方法
function viewAttr (obj)
{
    var arr = obj.getAttributeKeys();
    var str = "";
    for (var c = 0; c < arr.length; c++)
        str += arr[c] + ":" + obj.get(arr[c]) + "|";
    alert( str);
}
var jms_rich_editor = [];
// 將Rich Edit的資料儲存到textarea
function saveHTML()
{
    for (var c = 0; c < jms_rich_editor.length; c++)
        jms_rich_editor[c].saveHTML();
}
// 取得 RichEditor
function getEditor(textareaId)
{
    return YAHOO.widget.EditorInfo.getEditorById(textareaId);
}
// 取得 RichEditor 中的 iframe
function getEditorFrame(textAreaId)
{
    return document.getElementById( getEditor( textAreaId).get("iframe").get("id"));
}
// 建立RichEditor
function initMyRichEditor( id)
{
    if( jms_rich_editor.length == 0)
        $("body").addClass("yui-skin-sam");

    var myEditor = new YAHOO.widget.Editor(id, {
        height: '150px',
        width: '99%',
        css: 'body {font-family:Arial;font-size:15px;}',
        
        dompath: false, //Turns on the bar at the bottom
        animate: true, //Animates the opening, closing and moving of Editor windows
        toolbar: {
            titlebar: null,
            buttons: [
                {group: 'textstyle',
                    buttons: [
                        {type: 'push', label: 'Bold CTRL + SHIFT + B', value: 'bold'},
                        {type: 'push', label: 'Italic CTRL + SHIFT + I', value: 'italic'},
                        {type: 'push', label: 'Underline CTRL + SHIFT + U', value: 'underline'},
                        {type: 'separator'},
                        {type: 'push', label: 'Subscript', value: 'subscript', disabled: true},
                        {type: 'push', label: 'Superscript', value: 'superscript', disabled: true}
                    ]
                }
                /*,
                { type: 'separator' },
                { group: 'insertitem', label: ' ',
                    buttons: [
                        { type: 'push', label: 'Insert Image', value: 'insertimage' }
                    ]
                }*/
            ]
        }
    });
    
    myEditor.addListener("afterRender", function(e){
        $("#" + id + "_toolbar").addClass("richedit_toolbar");
    });
    
    myEditor.render();

    jms_rich_editor.push(myEditor);
}

// 前往比對介面, 與下一個函式成對
function goComparison (id)
{
    var source = getEditor(id);

    if ( source != null)
    {
        source.saveHTML();
        window.comparisonSource = source.get("element").value;
        window.comparisonCallback = function(data){
            source.setEditorHTML(data);
            window.focus();
            source.focus();
        };
    }
    else
    {
        source = document.getElementById(id);
        window.comparisonSource = source.value;
        window.comparisonCallback = function(data){
            source.value = data;
            window.focus();
            source.focus();
        };
    }
    
    var url = "comparison_init.action";
    var param = "width=820,height=700,status=no,location=no,menubar=no,toolbar=no,scrollbars=yes,resizable=yes";
    var windowRefer = window.open( url, COMPARISON_WINDOW, param);
    windowRefer.focus();

    window.onbeforeunload = function(e) {
        if(!windowRefer.closed)
            return "比對介面的視窗將會被關閉";
    };

    window.onunload = function(e) {
        if(windowRefer && !windowRefer.closed)
            windowRefer.close();
    };
}

// 採用此值
function adopt(id)
{
    saveHTML();

    // 母視窗必須定義
    if (opener.comparisonCallback)
        opener.comparisonCallback($("#"+id).val());
}

// panel 
var arrPanel;
var currentPanel;
function setPanel(arg1){
    arrPanel = arg1;
}
function changePanel(arg1){
    currentPanel = arg1;
    for(var i=0;i<arrPanel.length;i++) {
        if(currentPanel == arrPanel[i]){
            $("#content_"+arrPanel[i]).show();
            $("#title_"+arrPanel[i]+" > a").addClass('a_hover');
        }else{
            $("#content_"+arrPanel[i]).hide();
            $("#title_"+arrPanel[i]+" > a").removeClass('a_hover');
        }
    }
}