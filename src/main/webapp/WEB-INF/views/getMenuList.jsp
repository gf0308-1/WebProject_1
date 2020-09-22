<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>getMenus()한 결과 확인 화면</title>
	<style>
		.menu_table { border-collapse:collapse; border:solid 2px black; }
		.menu_table th { border: solid 2px black; padding:10px; }
		.menu_table td { border: solid 1px black; padding:10px; }
	</style>
</head>
<body>
	<c:if test="${mvoArr ne null}">
	
		<h1>mvoArr값이 도착함!!</h1>
		<hr>
		<br><br><br><br>
	
		<table class="menu_table"> <!--  cellspacing="0" cellpadding="0" -->
			<tr>
				<th>idx</th>
				<th>menu_name</th>
				<th>season</th>
			</tr>
			<c:forEach var="mvo" items="${mvoArr}">
				<tr>
					<td>${mvo.idx}</td>
					<td>${mvo.menu_name}</td>
					<td>${mvo.season}</td>
				</tr>
			</c:forEach>
		</table>

	</c:if>
	
	<c:if test="${mvoArr eq null}">도착한 값이 없음</c:if>
</body>
</html>