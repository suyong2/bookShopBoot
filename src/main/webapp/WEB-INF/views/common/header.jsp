<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />

<body>
	<div id="logo">
	<a href="/">
		<img width="176" height="80" alt="booktopia" src="/image/Booktopia_Logo.jpg">
	</a>
	</div>
	<div id="head_link">
		<ul>
			<li><a data-toggle="modal" href="#explainModal">알려드립니다.</a></li>
		   <c:choose>
		     <c:when test="${not empty user }">
				 <li id="user">
					 <a href="#" style="color: black; text-decoration: none;">
						 Logged in as: ${userName}
					 </a>
				 </li>
			   <li><a href="/logout">로그아웃</a></li>
			   <li><a href="#">마이페이지</a></li>
			   <li><a href="#">장바구니</a></li>
			   <li><a href="#">주문배송</a></li>
			 </c:when>
			 <c:otherwise>
			   <li><a data-toggle="modal" href="#exampleModal">로그인</a></li>
			   <li><a href="#">회원가입</a></li>
			 </c:otherwise>
			</c:choose>
			<li><a href="#">고객센터</a></li>
			<c:if test="${not empty user }">
				<li class="no_line"><a href="/admin/goods/main">관리자</a></li>
			</c:if>
		</ul>
	</div>
	<br>
	<div id="search" >
		<form name="frmSearch" action="#" >
			<input name="searchWord" class="main_input" type="text"  onKeyUp="keywordSearch()"> 
			<input type="submit" name="search" class="btn1"  value="검 색" >
		</form>
	</div>
   <div id="suggest">
        <div id="suggestList"></div>
   </div>

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">회원 로그인</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
<!--					<div class="col-md-6">-->
						<a href="/oauth2/authorization/google" class="btn btn-primary active" role="button">Google Login</a>
<!--						<a href="/oauth2/authorization/facebook" class="btn btn-secondary active" role="button">Facebook Login</a>-->
						<a href="/oauth2/authorization/naver" class="btn btn-success active" role="button">Naver Login</a>
					<!-- </div>-->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="explainModal" tabindex="-1" role="dialog" aria-labelledby="explainModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="explainModalLabel">알려드립니다.</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
<!--					<div class="col-md-6">-->
						본 포트폴리오는 <자바 웹을 다루는 기술>-길벗/ 이병승님의 책에 나오는 예제를
						스프링부트와 JPA, AWS S3, RDS(마리아DB)로 변경하여 수정한 결과물입니다.<br/>
						현재 상세보기와 SNS 로그인후 관리자페이지에서 아이템 및 이미지추가, 삭제만 구현되었습니다만,
						계속 작업진행할 예정입니다.<br/>
						감사합니다.
<!--					</div>-->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
