<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="menu">
    <div id="menu_box">
        <ul>
            <li><a href="javascript:void(0);" id="Menu_A"><span>用戶管理</span></a>
                
            </li>
            <li><a href="article_queryForm.action"><span>文章維護</span></a></li>
            <li><a href="import_form.action"><span>資料匯入</span></a></li>
            <li><a href="onsell_form.action"><span>資料上架</span></a></li>
           
            
        </ul>
    </div>
</div>

<script type="text/javascript">
var _menus = [];

function init( id)
{
    var obj = new Object();

    obj.id = id;
    $(id).mouseover(function(e){
          obj.mouseIn = true;
    });
    $(id).mouseout(function(e){
          obj.mouseIn = false;
    });

    $(id+"_2").mouseover(function(e){
          obj.mouseIn = true;
    });
    $(id+"_2").mouseout(function(e){
          obj.mouseIn = false;
    });

    obj.menu = $(id+"_2");
    _menus.push( obj);
}

function Menu_timer()
{
    for( var c = 0; c < _menus.length; c++)
    {
        if( _menus[c].mouseIn)
            _menus[c].menu.css("visibility","visible");
        else
            _menus[c].menu.css("visibility","hidden");
    }
}

init('#Menu_A');
init('#Menu_G');
init('#Menu_H');
setInterval(Menu_timer,500);
</script>