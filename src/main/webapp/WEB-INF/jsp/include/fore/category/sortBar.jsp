<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
$(function(){
	$("input.sortBarPrice").keyup(function(){
	    //清楚特殊字符
        $(this).val(this.value.replace(/\D/g,'').replace(/....(?!$)/g,'$&'));

		var begin = $("input.beginPrice").val();
		var end = $("input.endPrice").val();

		if(isNumber(begin) && isNumber(end)){
            $("div.productUnit").hide();
            $("div.productUnit").each(function(){
                var price = $(this).attr("price");
                price = new Number(price);
                if(price>=begin && price<=end){
                    $(this).show();
				}
            });
		}else if(isNumber(begin) && !isNumber(end)){
            $("div.productUnit").hide();
            $("div.productUnit").each(function(){
                var price = $(this).attr("price");
                price = new Number(price);
                if(price>=begin){
                    $(this).show();
                }
            });
        }else if(isNumber(end) && !isNumber(begin)){
            $("div.productUnit").hide();
            $("div.productUnit").each(function(){
                var price = $(this).attr("price");
                price = new Number(price);
                if(price<=end){
                    $(this).show();
                }
            });
		}else{
            $("div.productUnit").show;
        }
	});
});

/**
 * 是否为非空数字
 * @param val
 */
function isNumber(val){
    if(val == "" || val == null){
        return false;
	}
	if(!isNaN(val)){
	    return true;
	}else{
        return false;
	}
}
</script>	
<div class="categorySortBar">


	<table class="categorySortBarTable categorySortTable">
		<tr>
			<td <c:if test="${'all'==param.sort||empty param.sort}">class="grayColumn"</c:if> ><a href="?cid=${c.id}&sort=all">综合<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'review'==param.sort}">class="grayColumn"</c:if> ><a href="?cid=${c.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'date'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'saleCount'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a></td>
			<td <c:if test="${'price'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a></td>
		</tr>
	</table>
	
	
	
	<table class="categorySortBarTable">
		<tr>
			<td><input class="sortBarPrice beginPrice" type="number" placeholder="请输入"></td>
			<td class="grayColumn priceMiddleColumn">-</td>
			<td><input class="sortBarPrice endPrice" type="number" placeholder="请输入"></td>
		</tr>
	</table>

</div>