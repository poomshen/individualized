<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<div id="content">
	<h2>로그인</h2>
	<h3 class="hidden">방문페이지 로그</h3>
	<ul id="breadscrumb" class="block_hlist clear">
		<li>HOME</li>
		<li>회원가입</li>
		<li>로그인</li>
	</ul>
	<h3 class="hidden">회원가입 폼</h3>
	<div id="join-form" class="join-form margin-large">
		<!-- Spring security 로그인 실패 message 처리하기
			 /joinus/login.htm?error=kkkkk
		  -->
		<c:if test="${param.error != null}">
			<div>
				로그인실패<br>
				 <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
     			 이유 : <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
     			</c:if>
			</div>
		</c:if>
		<!-- END -->
		
		
		
		
		<!-- 
		2단계 테스트 
		변환 (JSTL) 사용
		<form name="f" action="/SpringMVC_Basic05_WebSite_Security/login" method="post"> 
		
		두번째 방법이 문안하게 사용
		<form name="f" action="${pageContext.request.contextPath}/login" method="post"> 
		
		-->
		   <c:url value="/login" var="loginURL" />
		   <form action="${loginURL}" method="post">
			<fieldset>
				<legend class="hidden">로그인 폼</legend>
				<h3>
					<img src="images/txtTitle.png" />
				</h3>
				<ul id="loginBox">
					<li><label for="uid">아이디</label>
					<input name="username"	class="text" /></li>
					<li><label for="pwd">비밀번호</label>
					<input type="password"	name="password" class="text" /></li>
				</ul>
				<p>
					<input type="submit" id="btnLogin" value="" />
				</p>
				<ul id="loginOption">
					<li><span>아이디 또는 비밀번호를 분실하셨나요?</span><a href="/Member/FindID"><img
							alt="ID/PWD 찾기" src="images/btnFind.png" /></a></li>
					<li><span>아이디가 없으신 분은 회원가입을 해주세요.</span><a
						href="/Member/Agree"><img alt="회원가입" src="images/btnJoin.png" /></a></li>
				</ul>
			</fieldset>
		</form>
	</div>
</div>

