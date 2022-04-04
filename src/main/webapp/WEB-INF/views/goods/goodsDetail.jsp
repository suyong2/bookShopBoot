<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" 	isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="imageList"  value="${goods.imageList }"  />
 <%
     //치환 변수 선언합니다.
      //pageContext.setAttribute("crcn", "\r\n"); //개행문자
      pageContext.setAttribute("crcn" , "\n"); //Ajax로 변경 시 개행 문자 
      pageContext.setAttribute("br", "<br/>"); //br 태그
%>  
<style>
#layer {
	z-index: 2;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
}

#popup {
	z-index: 3;
	position: fixed;
	text-align: center;
	left: 50%;
	top: 45%;
	width: 300px;
	height: 200px;
	background-color: #ccffff;
	border: 3px solid #87cb42;
}

#close {
	z-index: 4;
	float: right;
}
</style>
<script type="text/javascript">
	function add_cart(goodsId) {
		$.ajax({
			type : "post",
			async : false, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/cart/addGoodsInCart.do",
			data : {
				goodsId:goodsId
				
			},
			success : function(data, textStatus) {
				//alert(data);
			//	$('#message').append(data);
				if(data.trim()=='add_success'){
					imagePopup('open', '.layer01');	
				}else if(data.trim()=='already_existed'){
					alert("이미 카트에 등록된 상품입니다.");	
				}
				
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다."+data);
			},
			complete : function(data, textStatus) {
				//alert("작업을완료 했습니다");
			}
		}); //end ajax	
	}

	function imagePopup(type) {
		if (type == 'open') {
			// 팝업창을 연다.
			jQuery('#layer').attr('style', 'visibility:visible');

			// 페이지를 가리기위한 레이어 영역의 높이를 페이지 전체의 높이와 같게 한다.
			jQuery('#layer').height(jQuery(document).height());
		}

		else if (type == 'close') {

			// 팝업창을 닫는다.
			jQuery('#layer').attr('style', 'visibility:hidden');
		}
	}
	
function fn_order_each_goods(goodsId,goodsTitle,goodsSalesPrice,fileName){
	var _isLogOn=document.getElementById("isLogOn");
	var isLogOn=_isLogOn.value;
	
	 if(isLogOn=="false" || isLogOn=='' ){
		alert("로그인 후 주문이 가능합니다!!!");
	} 
	
	
		var total_price,final_total_price;
		var order_goods_qty=document.getElementById("order_goods_qty");
		
		var formObj=document.createElement("form");
		var i_goodsId = document.createElement("input"); 
    var i_goodsTitle = document.createElement("input");
    var i_goodsSalesPrice=document.createElement("input");
    var i_fileName=document.createElement("input");
    var i_order_goods_qty=document.createElement("input");
    
    i_goodsId.name="goodsId";
    i_goodsTitle.name="goodsTitle";
    i_goodsSalesPrice.name="goodsSalesPrice";
    i_fileName.name="goodsFileName";
    i_order_goods_qty.name="order_goods_qty";
    
    i_goodsId.value=goodsId;
    i_order_goods_qty.value=order_goods_qty.value;
    i_goodsTitle.value=goodsTitle;
    i_goodsSalesPrice.value=goodsSalesPrice;
    i_fileName.value=fileName;
    
    formObj.appendChild(i_goodsId);
    formObj.appendChild(i_goodsTitle);
    formObj.appendChild(i_goodsSalesPrice);
    formObj.appendChild(i_fileName);
    formObj.appendChild(i_order_goods_qty);

    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/order/orderEachGoods.do";
    formObj.submit();
	}	
</script>
	<hgroup>
		<h1>컴퓨터와 인터넷</h1>
		<h2>국내외 도서 &gt; 컴퓨터와 인터넷 &gt; 웹 개발</h2>
		<h3>${goods.goodsTitle }</h3>
		<h4>${goods.goodsWriter} &nbsp; 저| ${goods.goodsPublisher}</h4>
	</hgroup>
	<div id="goods_image">
		<figure>
			<img alt="HTML5 &amp; CSS3"
				 src="/down?imageFileName=${goods.goodsId }/${goods.imageList[0].fileName}">
		</figure>
	</div>

	<div class="clear"></div>
	<!-- 내용 들어 가는 곳 -->
	<div id="container">
		<ul class="tabs">
			<li><a href="#tab1">책소개</a></li>
			<li><a href="#tab2">저자소개</a></li>
			<li><a href="#tab3">책목차</a></li>
			<li><a href="#tab4">출판사서평</a></li>
			<li><a href="#tab5">추천사</a></li>
			<li><a href="#tab6">리뷰</a></li>
		</ul>
		<div class="tab_container">
			<div class="tab_content" id="tab1">
				<h4>책소개</h4>
				<c:forEach var="image" items="${goods.imageList }"  varStatus="status">
					<c:if test="${status.count ne 1}">
						<img src="/down?imageFileName=${goods.goodsId }/${image.fileName}">
					</c:if>
				</c:forEach>
			</div>
			<div class="tab_content" id="tab2">
				<h4>저자소개</h4>
				<p>

			</div>
			<div class="tab_content" id="tab3">
				<h4>책목차</h4>
			</div>
			<div class="tab_content" id="tab4">
				<h4>출판사서평</h4>
			</div>
			<div class="tab_content" id="tab5">
				<h4>추천사</h4>
			</div>
			<div class="tab_content" id="tab6">
				<h4>리뷰</h4>
			</div>
		</div>
	</div>
	<div class="clear"></div>
<form   action='#'  >
		<input  type="submit" value="장바구니 보기">
</form>			
<input type="hidden" name="isLogOn" id="isLogOn" value="${isLogOn}"/>