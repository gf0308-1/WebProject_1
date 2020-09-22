<%@page
	import="org.springframework.web.bind.annotation.SessionAttributes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Home</title>
<link rel="stylesheet" type="text/css"
	href="https://ssl.pstatic.net/staticv2.weather/responsive/css/weather_webfont_202008211710.css">


<!-- Bootstrap core CSS -->
<link href="resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/heroic-features.css" rel="stylesheet">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://ssl.pstatic.net/staticv2.weather/responsive/js/weather_common_202008211710.js"></script>

<style type="text/css">
header {
	background-image: url("resources/img/summer.jpg");
}

body {
	margin: 0;
	padding: 0;
	height: 100%;
}

.contents-wrap {
	min-height: 100%;
	position: relative;
	padding-bottom: 100px; /* footer height */
}

footer {
	width: 100%;
	height: 100px;
	position: absolute;
	bottom: 0;
	background: #F5ECCE;
	text-align: center;
	color: white;
}

.carousel-inner>.carousel-item>input {
	border-radius: 100px;
	float: left;
	min-width: 30%;
	height: 25%;
	max-width: 30%;
	margin-right: 10px;
	cursor: pointer;
}

.lead {
	font-size: 24px;
	font-family: fantasy;
}

#demo {
	padding-left: 6%;
	margin-bottom: 20px;
}

.font-box {
	text-align: center;
}

.log {
	height: 20%;
	text-align: center;
}

.logbtn {
	width: 45px;
	height: 45px;
	border-radius: 20px;
	color: white;
	background-image: url("resources/img/loginbtn.png");
	background-size: 45px 45px;
}

.carousel-control-prev:hover, .carousel-control-next:hover {
	background-color: #F5ECCE;
}

#logoutIcon {
	width: 100px;
	height: auto;
	margin-left: 30px;
}

#logo {
	width: 150px;
	height: 150px;
}

/* 로그인 폼 관련 */
.card {
	margin: 0 auto; /* Added */
	float: none; /* Added */
	margin-bottom: 10px; /* Added */
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.modal-content {
	margin: auto;
	width: fit-content;
	border-radius: 20px;
}

#probtn {
	border-radius: 60px;
	cursor: pointer;
}

.list {
	padding: 0px 15px 0px 15px;
	margin: 150px 100px 20px 100px;
}

.list>table {
	text-align: left;
	margin: auto;
	border-spacing: 20px;
}

.list>td {
	padding: 0px;
}

.resimg {
	width: 200px;
	height: 200px;
}

#biz:hover {
	background-color: black;
	color: white;
}
</style>


</head>

<body>

	<%
		session.getAttribute("token");

	application.setAttribute("sessionid", session.getAttribute("token")); //인증토큰

	application.setAttribute("kimage", session.getAttribute("kimage")); //유저 프로필
	application.setAttribute("userName", session.getAttribute("userName")); //유저이름
	%>

<%-- 닉네임값 kname이 (세션에담겨서) 여기에 존재하고있음을 확인함 --%>
<%-- <c:if test="${kname ne null }">
	kname이 있음: ${kname }
</c:if>
<c:if test="${kname eq null }">
	kname이 없음: ${kname }
</c:if> --%>

<!-- 동적 form 객체 projectForm -->
<%-- <form name="projectForm" action="" method="POST">
	<!-- 로그인한유저의 카카오닉네임정보(id대신,이메일은 정보제공여부에따라 사용못할수있으므로..)를 얻으면 여기에 담아둔다  -->
	<input type="hidden" id="kname" name="kname" value="${kname }"/>
</form> --%>

	<div class="log">
		<a href="http://localhost:8080/weather/"><img alt="logo"
			src="resources/img/logo.png" id="logo"></a>
		<c:if test="${sessionid eq null }">
			<button type="button" class="logbtn" data-toggle="modal"
				data-target="#myModal" style="margin: 30px;"></button>
		</c:if>

		<c:if test="${sessionid ne null }">
			<img id="logoutIcon" alt="profile" src="resources/img/logouticon.png"
				data-toggle="modal" data-target="#myModal" id="probtn">
		</c:if>
	</div>
	
	<div id="greeting">
		<c:if test="${kname ne null }">
			<h3 style="text-align:center;">환영합니다 ${kname }님</h3>
		</c:if>
	<!-- 화면유지를위한 공백넣어둘까? -->
	</div>
	<br>
	<div id="mahout_recommendation" style="text-align:center;">
		<input class="btn btn-success" type="button" name="btn_mahout" value="비슷한 취향의 유저 기반 메뉴추천!"
			onClick="javascript:document.getElementById('form_mahout').submit();" />
	</div>
	
	<div id="mahout_recommendation_go">
		<form id="form_mahout" name="form_mahout" action="mahout" method="POST">
			<input type="hidden" id="kname" name="kname" value="${kname }"/>
		</form>
	</div>
	


	<!-- 모달 영역 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body" style="padding: 0px">
					<div class="card align-middle"
						style="width: 25rem; border-radius: 20px; margin: 0px">
						<div class="card-title" style="margin-top: 30px;">
							<h2 class="card-title text-center" style="color: #113366;">로그인
								폼</h2>
						</div>
						<div class="card-body">

							<c:if test="${sessionid eq null }">
								<h5 class="form-signin-heading">로그인 정보를 입력하세요</h5>
								<!-- <label for="inputEmail" class="sr-only">Your ID</label> <input
									type="text" id="uid" class="form-control" placeholder="Your ID"
									required autofocus><BR> <label for="inputPassword"
									class="sr-only">Password</label> <input type="password"
									id="upw" class="form-control" placeholder="Password" required><br>
								<div class="checkbox">
									<label> <input type="checkbox" value="remember-me">기억하기
									</label>
								</div>
								<button id="btn-Yes" class="btn btn-lg btn-warning btn-block"type="submit">로그인</button> -->
								<br>
								<a href="${kakao_url}"><img alt="kakaoLog"
									src="resources/img/kakao_login.png"
									style="display: block; width: 100%"></a>
							</c:if>
							<c:if test="${sessionid ne null }">
								<br>
								<a href="http://localhost:8080/weather/logout"><img
									alt="kakaoLogout" src="resources/img/kakao_logout.png"
									style="display: block; width: 100%"></a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">
		$(function() {
			$('[data-toggle="tooltip"]').tooltip()
		})
	</script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
	<script>
		//기본 5초 (2000)
		$('.carousel').carousel({
			interval : 2000
		})
	</script>


	<div class="contents-wrap">

		<!-- Page Content -->
		<div class="container">

			<!-- Jumbotron Header -->
			<!-- 크롤링 적용부분 이미지파일 적용법 질문 -->
			<header class="jumbotron my-4">
				<h4>현재 위치: ${area }</h4>
				<h1 class="display-3">현재 온도 ${now }℃</h1>
				<p class="lead">min : ${min } max : ${max}</p>
				<p class="lead">${sens }</p>
			</header>

			<div class="font-box">
				<p class="lead" style="font-weight: bold;">오늘의 추천 메뉴!</p>
			</div>

			<div id="demo" class="carousel slide" data-ride="carousel">
				<div class="carousel-inner">

					<!-- 슬라이드 쇼 -->
					<div class="carousel-item active">
						<!--가로-->
						<!-- 실제 적용시 a태그로하면 css가 터지니까 onclick으로 처리하자 -->
						<!-- <a href="javascript:alert('이것도인식됨!');"><img class="d-block w-100" src="resources/img/burger.jpg" alt="First slide"><input type="hidden" value="burger"/></a> -->
						<!-- github적용한 햄버거 이미지 -->
						<c:forEach var="list" items="${mvoList }" varStatus="status">
							<c:if test="${status.count >= 1 and status.count <4 }">
								<input type="image" class="d-block w-100"
									src="${ list.menu_img }" alt="First slide" name="menu"
									value="${list.menu_name}" onClick="c_send(this.value);" />
							</c:if>
						</c:forEach>
					</div>
					<div class="carousel-item" style="text-align: center;">
						<!--가로-->
						<c:forEach var="list" items="${mvoList }" varStatus="status">
							<c:if test="${status.count >= 4 and status.count < 7 }">
								<input type="image" class="d-block w-100"
									src="${ list.menu_img }" alt="First slide" name="menu"
									value="${list.menu_name }" onClick="c_send(this.value);" />
							</c:if>
						</c:forEach>
					</div>

				</div>

				<!-- 왼쪽 오른쪽 화살표 버튼 -->
				<a class="carousel-control-prev" href="#demo" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<!-- <span>Previous</span> -->
				</a> <a class="carousel-control-next" href="#demo" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<!-- <span>Next</span> -->
				</a>


				<!-- 인디케이터 -->
				<ol class="carousel-indicators"
					style="bottom: -100px; background-color: #F5ECCE; width: 20%; margin: auto; border-radius: 20px">
					<li data-target="#demo" data-slide-to="0" class="active"></li>
					<!--0번부터시작-->
					<li data-target="#demo" data-slide-to="1"></li>
				</ol>
			</div>
			<!-- slider end-->


			<div class="list">
				<div class="lead" style="text-align: center;">인기 맛집 100</div>
				<table class="table table-borderless">
					<c:forEach items="${list }" var="list" varStatus="vs">
						<tr>
							<td rowspan="4" width="200px"><img
								src="<c:out value='${list.url }'/>" class="resimg"></td>
							<td width="30%"><h3>${list.name }</h3></td>
							<td>Click >>
								<button data-toggle="collapse"
									href="#collapseExample${vs.index }" aria-expanded="false"
									aria-controls="collapseExample" id="biz">근무시간</button> <!-- collapse 대상 태그는 class를 collapse로 설정합니다. -->
								<div class="collapse" id="collapseExample${vs.index }">
									<div class="well">${list.bizhourInfo }</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">주소 : ${list.address }</td>
						</tr>
						<tr>
							<td colspan="2">전화번호 : ${list.phone }</td>
						</tr>
						<tr>
							<td colspan="2">Click >>
								<button data-toggle="collapse" href="#menu${vs.index }"
									aria-expanded="false" aria-controls="collapseExample" id="biz">메뉴</button>
								<!-- collapse 대상 태그는 class를 collapse로 설정합니다. -->
								<div class="collapse" id="menu${vs.index }">
									<div class="well">${list.menu }</div>
								</div>
							</td>
						</tr>

					</c:forEach>
				</table>
			</div>
		</div>

		<!-- Footer -->
		<footer class="py-5">
			<div class="container">
				<p class="m-0 text-center text-white">Copyright &copy; Your
					Website 2020</p>
			</div>
			<!-- /.container -->
		</footer>
	</div>






	<form name="f" method="post" action="test_searchAndList">
		<input type="hidden" id="menu" name="menu" />
		<!-- geocoder를 이용해서 얻어낸 현좌표에 대응하는 주소값을 얻어서 넣어줄 인풋객체 -->
		<input id="address" type="hidden" name="address" /> <input
			id="latitude" type="hidden" name="latitude" />
		<!-- 위도좌표값도 담아서 보내주는 객체 -->
		<input id="longitude" type="hidden" name="longitude" />
		<!-- 경도좌표값도 담아서 보내주는 객체 -->
		<input id="u_id" type="hidden" name="u_id" value="${userName }" />
		<!-- 경도좌표값도 담아서 보내주는 객체 -->
		<!-- ${userName } -->
	</form>





	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7dab56e63390166356d77358bff68a91&libraries=services"></script>
	<script>
		function afterAddrGain(result, status) {
			if (status == kakao.maps.services.Status.OK) {
				console.log('좌표->주소 변환이 정상수행됨');

				var address_name = result[0].address_name;

				console.log('좌표에 대한 실제 주소: ' + address_name);
				console.log('좌표의 주소: 위도 ' + latitude + ' 경도 ' + longitude);

				// form객체 안에 address부분에 값 주입해넣기
				document.getElementById('address').value = address_name;
				document.getElementById('latitude').value = latitude;
				document.getElementById('longitude').value = longitude;
			}
		}// end afterAddrGain()

		function c_send(menu) {
			var chk =
	<%=session.getAttribute("token")%>
		if (chk == null) {
				alert('로그인후 이용해주세요!');
				return;
			}

			document.getElementById('menu').value = menu;
			document.f.submit();
		}

		/* var u_id = document.getElementById('u_id').value;
		console.log('[user id]: '+ u_id); */

		// 현 단말기의 위치좌표를 얻어내는 html5의 geolocation 기능을 사용한다
		// [최우선사항-2]geolocation으로 현재 브라우저의 위치정보를 알아낸다
		if (!navigator.geolocation) { // HTML5의 geolocation을 사용할 수 있는 지 확인 : true/false
			// 사용할 수 없었을 경우
			console.log('geolocation이 이용불가함');
		} else {
			// 사용할 수 있는 경우
			console.log('geolocation 사용 가능!');
			// navigator.geolocation.getCurrentPosition() 함수를 실행하면 '현재위치에 대한 데이터값'이 반환된다,
			// 그러면  바로 어떤 함수가 실행되게 하여(콜백함수이용) 재료로 사용하기 위한 값(position이라고 명명)을 파라미터로 받고, 어떤 작업을 수행하도록 한다. // (콜백함수: "어떤 것이 이뤄지자마자 바로 실행되도록 해놓은 함수")
			navigator.geolocation.getCurrentPosition(function(position) {
				var LatLngObj = null;
				latitude = position.coords.latitude; // geolocation을 통해 얻은 현재위치의 위도좌표 
				longitude = position.coords.longitude; // geolocation을 통해 얻은 현재위치의 경도좌표

				console.log('현재 위치 위도값: ' + latitude + '\n현재 위치 경도값: '
						+ longitude); // 로그창에서 한번 확인!

				if (latitude == null || longitude == null) {
					console.log('위도 경도 좌표를 얻지 못했습니다.');
					//return false;
				} else { // 좌표가 제대로 얻어졌을 경우!
					/* LatLngObj = new kakao.maps.LatLng(latitude, longitude); // 전체좌표 객체 생성
					console.log('좌표객체 생성됨.'); */

					// 주소-좌표 변환 객체를 생성합니다
					var geocoder = new kakao.maps.services.Geocoder();
					console.log('geocoder 객체 생성됨');
					// geocoder객체로 좌표를이용해 실주소값을 찾는다 => geocoder.coord2RegionCode(경도,위도,콜백함수) 함수 호출
					geocoder.coord2RegionCode(longitude, latitude,
							afterAddrGain); // 좌표-주소 변환을 완료한 뒤 얻은 주소결과값을 address인풋객체 안에 넣는것까지 완료했다.
				}
			});// navigator.geolocation.getCurrentPosition()

		}// if-else
	</script>
	
	<!-- kname에 대한 js 처리 -->
	<script>
		var kname = document.getElementById('kname').value;
		if(kname != null) { // kname이 생겨났을경우
			// alert(kname); // 테스트
		}
	
	</script>
</body>
</html>
