<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>에러 페이지(현재 mybatis에 연결되어있는지 확인하고있었음 => 연결되어 있음을 확인함!)</title>
</head>
<body>
	<!-- <h1>에러 발생</h1>
	<hr>
	<br><br><br><br>
	
	<b>message:</b> ${message } -->
	
	<c:if test="${mv ne null}">
	
		<h1>mv값이 도착함!!</h1>
		<hr>
		<br><br><br><br>
	
		<table class="menu_table"> <!--  cellspacing="0" cellpadding="0" -->
			<tr>
				<th>idx</th>
				<th>menu_name</th>
				<th>season</th>
			</tr>
			<c:forEach var="mvo" items="${mv}">
				<tr>
					<td>${mvo.idx}</td>
					<td>${mvo.menu_name}</td>
					<td>${mvo.season}</td>
				</tr>
			</c:forEach>
		</table>

	</c:if>
	
	<c:if test="${mv eq null}">도착한 값이 없음</c:if> 
	
	
	
	
	
	
	
	
	
	<%-- message값: ${message } <br>
	<br>
	msg값: ${msg } --%>
	
	
	<!-- redirect로 값을 전송할 땐 GET방식으로 보내지는 것이다.
		따라서 기본 url파라미터에 전송할데이터를 쓰는 방식도 당연히 되고,
		ModelAndView방식을 이용하고 있다면 mv.addObject()해둔 것도 알아서 잘 전송되어 전해진다.
		물론 이 둘을 동시에 다 사용할 수 도 있다.
		다만 제대로 받으려면 해당 메소드의 파라미터에 파라미터 갯수, 파라미터 명이 잘 갖춰져 있어야 한다.
	 -->
	
	
	
	
</body>
</html>