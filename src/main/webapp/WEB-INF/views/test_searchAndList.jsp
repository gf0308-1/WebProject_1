<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>맛집 지도 화면</title>
	<style>
		#logo {	width: 150px; height: 150px; }
				
		#total_window {
			text-align: center;
		}
	</style>
	<!-- 지도 화면의 원래 css 부분 -->
	<link href="resources/css/test_searchAndList.css" rel="stylesheet"/>
	<!-- Bootstrap core CSS -->
	<link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="resources/css/heroic-features.css" rel="stylesheet">
	
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://ssl.pstatic.net/staticv2.weather/responsive/js/weather_common_202008211710.js"></script>
	
</head>

<body style="padding:0;">

	<!-- total_window -->
	<div id="total_window">
		<div class="log">
			<a href="http://localhost:8080/weather/"><img alt="logo" src="resources/img/logo.png" id="logo"></a>
		</div>
		<h1>${u_id}님 주변 '${menu}' 맛집</h1>
		<hr>

	<div id="body_window">
				<div id="result" style="text-align:center; color:gray; font-style:italic;"></div>
		<div id="container" class="view_map">
			<!-- 정상지도화면에 관한 div블록 -->
			<div id="mapWrapper" class="map_wrap">
				<div id="map"></div>
						 	<!-- 최초의 원 위치 위도값 -->
							<input type="hidden" id="original_latitude"	name="original_latitude" value="${latitude}"/>
							<!-- 최초의 원 위치 경도값 -->
							<input type="hidden" id="original_longitude" name="original_longitude" value="${longitude}"/>
				<div id="menu_wrap" class="bg_white">
					<div class="option">
						<div>							
							<form onsubmit="searchPlaces(); return false;">
								<!-- onsubmit 이벤트핸들러: "form객체 등을 전송할 때, 전송 직전에 어떤 작업을 수행해주도록 하는 속성, 주로 유효성검사 처럼 전송전 수행해주어야 하는 작업 등에 많이 쓰이는 이벤트핸들러이다." -->
								<!-- onsubmit을 응용하면 form에 action속성이 필요없을 수도 있음 => 'action으로 submit해주기 전 onsubmit때 해주는 작업에서 궁긍적으로 수행하려했던 작업을 다 처리하게 된다면, 이후의 작업부분들은 필요없으므로' -->
								키워드 : <input type="text" value="${addressAndMenu}" id="keyword" size="15">
								<button type="submit">검색하기</button>
							</form>
						</div>
					</div>
					<hr>
					<!-- 해당 메뉴를 취급하는 가게 리스트 부분 -->
					<ul id="placesList"></ul>
					<div id="pagination"></div>				
				</div>
				<!-- 원위치로 가운데 오기 버튼 -->
				<div id="returnCenterBtnMenu">
					<input type="button" name="returnCenterBtn"
							value="원위치로 돌아가기" onClick="backToOriCenter();"/>
				</div>
				<!-- [이슈2] 로드뷰 부분 구현 차후에 하기 
				<input type="button" id="btnRoadview" onClick="toggleMap(false)" title="로드뷰 보기" value="로드뷰"/>
				<input type="button" id="btnMap" onClick="toggleMap(true)" title="지도 보기" value="지도"/> -->
			</div><!-- mapWrapper -->
			
			<!-- 로드뷰 화면에 관한 div블록 -->
			<!-- <div id="rvWrapper" >  --><!-- style="position:absolute;top:0;left:0;" -->
				 <!-- <div id="roadview"></div>  --><!--  style="height:100%;" -->
				<!-- <input type="button" id="btnMap" onClick="toggleMap(true)" title="지도 보기" value="지도"/> -->
			<!-- </div> -->
			
		</div>

		<!-- 카카오 지도 API 기능 임포트 -->
		<script type="text/javascript" 
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7dab56e63390166356d77358bff68a91&libraries=services"></script>
				
		<!-- 카카오 지도 API 구현 소스 -->
		<script type="text/javascript" src="resources/js/test_searchAndList.js"></script>
	
	
	</div>
	<!-- end body_window -->

	</div> <!-- end total_window -->

</body>
</html>