<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	 isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:if test='${not empty message }'>
<script>
window.onload=function()
{
  result();
}

function result(){
	alert("아이디나  비밀번호가 틀립니다. 다시 로그인해주세요");
}
</script>
</c:if>
</head>
<body>
	<H3>회원 로그인</H3>
	<div class="row">
		<div class="col-md-6">
			<a href="/oauth2/authorization/google" class="btn btn-success active" role="button">Google Login</a>
			<a href="/oauth2/authorization/naver" class="btn btn-secondary active" role="button">Naver Login</a>
		</div>
	</div>
	<br>
</body>
</html>