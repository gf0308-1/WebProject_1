<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 취향 기반 추천 메뉴 리스트</title>
</head>
<body>
	<c:if test="${menuList ne null }">
	<h1>당신의 취향 상 이것도 어때요?</h1>
		<br>
		<c:forEach var="menu" items="${menuList }">
			<div class="eachMenu">
				<h3>${menu.menu_name }</h3>
				<input type="image" class="d-block w-100" src="${menu.menu_img }" 
				 style="width:700px;height:500px;" alt="First slide" name="menu_img" value="${menu.menu_name}" onClick="c_send(this.value);" />
			</div>
		</c:forEach>
	</c:if>
	

	

	
</body>
</html>