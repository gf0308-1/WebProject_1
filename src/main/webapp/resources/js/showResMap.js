/**
 *  해당 맛집들을 지도로 보여주는 지도 기능의 자바스크립트
 */

	// 전역 변수
	// 맨 처음 디폴트좌표로 지정되는 좌표
	var oriLat = null, // 디폴트 위도좌표 oriLat
		oriLng = null; // 디폴트 경도좌표 oriLng
	var oriCenterLatLng = null; // 위도경도 합체본 객체
				
	// 지도 상에 클릭한 지점의 좌표값
	var clickedLatLng = null; // 위도경도 합체본 객체 
				
				
	// [최우선사항-1] 지도부터 우선 먼저 띄워놓는다!
	var container = document.getElementById('map'); // 지도를 담을 컨테이너 div 객체
	
	var options = { // 지도객체를 구현할 때의 설정 내용인 options객체
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 중앙좌표값  // 예: 카카오 본사 좌표: 33.450701, 126.570667 
		level: 3
	};
	
	// 지도를 화면에 생성  // 지도 객체 map은 전역변수임
	var map = new kakao.maps.Map(container, options);
	// 마커를 생성 // 마커는 전역변수임
	var marker = null;
			
			
	// [최우선사항-2]geolocation으로 현재 브라우저의 위치정보를 알아낸다
	if(!navigator.geolocation) { // HTML5의 geolocation을 사용할 수 있는 지 확인 : true/false
		// 사용할 수 없었을 경우
		alert('geolocation이 사용되지 않음');
	} else {
		// 사용할 수 있는 경우
		console.log('geolocation 사용 가능!');
		// navigator.geolocation.getCurrentPosition() 함수를 실행하면 '현재위치에 대한 데이터값'이 반환된다,
		// 그러면  바로 어떤 함수가 실행되게 하여(콜백함수이용) 재료로 사용하기 위한 값(position이라고 명명)을 파라미터로 받고, 어떤 작업을 수행하도록 한다. // (콜백함수: "어떤 것이 이뤄지자마자 바로 실행되도록 해놓은 함수")
		navigator.geolocation.getCurrentPosition(function(position){ 
			oriLat = position.coords.latitude; // geolocation을 통해 얻은 현재위치의 위도좌표 
			oriLng = position.coords.longitude; // geolocation을 통해 얻은 현재위치의 경도좌표
			console.log('현재 위치 위도값: ' + oriLat + '\n현재 위치 경도값: ' + oriLng); // 로그창에서 한번 확인!
			
			oriCenterLatLng = new kakao.maps.LatLng(oriLat, oriLng); // 전체좌표 객체 생성 
			
			// 디폴트 통합좌표객체를 얻었으면, 해당 좌표로 지도화면 이동 처리한다.
			if(oriCenterLatLng == null) {
				console.log('반환된 위도경도값이 없습니다.');
				return;
			} else { // 값이 제대로 있을 겨우
				map.setCenter(oriCenterLatLng); // 지도 화면의 중심좌표를 해당좌표로 이동시킨다
					
				// "마커는 geolocation처리 후에 얻어온 데이터를 이용해주어야 하므로, geolocation 처리구문 안의 이후부분으로 포함시키도록 한다" 
				
				// [지도에 클릭한 지점에 마커 표시]
				// 마커 이미지 설정하기 (이건안해도됨, 굳이 커스터마이징 하고자 할 때 하는 것) 마커이미지 예시 "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png"
				var imageSrc = '/project/resources/image/marker_1.png' , // 마커이미지의 주소입니다    
				   	imageSize = new kakao.maps.Size(34, 39), // 맵
				   	imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다. : 27, 69
				// 이미지정보를 가지고 있는 마커이미지 객체를 생성
				var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
				// 마커 생성
				marker = new kakao.maps.Marker({
					// 마커가 생성되면, 그 마커를 지도(디폴트화면)의 중심부에 놓는다 
					position: oriCenterLatLng, // 마커가 뜰 위치좌표를 지정해주어 거기에 뜨게한다.
					//image: markerImage, // 마커 이미지 설정
					clickable: true // 클릭 가능하게 설정 // marker.setClickable(true); 이렇게도 설정 가능함
				});
				
				// 지도에 마커를 표시합니다
				marker.setMap(map);
				marker.setDraggable(true); // 마커를 드래그 가능하게 설정
				
				// 지도타입 컨트롤러(조절기) 설정
				var mapTypeControl = new kakao.maps.MapTypeControl();
				map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
				// 줌 컨트롤러 생성
				var zoomControl = new kakao.maps.ZoomControl();
				map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
							
							
				// ==================[지도에 클릭 이벤트를 등록]==================
				// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
				kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
					console.log('click리스너 안으로 진입');
					// 클릭한 위도, 경도 정보를 가져옵니다
					//var clickedLatLng = mouseEvent.latLng;
				    clickedLatLng = mouseEvent.latLng;
					var strLatLng = '(' + clickedLatLng.getLat() + ',' + clickedLatLng.getLng() + ')';
				    console.log('클릭 지점의 좌표 데이터: ' + strLatLng);
					// 지도상에 클릭한 지점에 마커를 띄워주기 처리한다.
					marker.setPosition(clickedLatLng);
					// 좌표를 알려주는 문자열을 만든다.
				    var message = '클릭한 위치의 좌표: ' + strLatLng;
					// 웹문서에서 위 내용을 표시해주기로 해서 만들어놓은 부분에, innerHTML로 메세지 문자열을 넣는다. 
					var resultDiv = document.getElementById('result_1');
				    resultDiv.innerHTML = message;
				    // 자바스크립트의 .html() 함수는, 선택자.html(html코드) 했을 때 "해당 선택자 부분에 파라미터로준 html코드가 주입되어 작성되어지는 효과"의 함수이다.
				    // 자바스크립트의 .innerHTML속성은, 말 그대로 해당 선택자의 HTML안에(HTML코드,태그 안에) 부분을 의미한다 -> 여기에다가 text값, 문자값등을 부여해 넣으면 웹페이지에서 그대로 반영이 된다.
				});
								
						
				// [지도가 이동, 확대, 축소로 인해 중심좌표가 변경되면 발생할 이벤트]
				kakao.maps.event.addListener(map, 'center_changed', function() {
				    // 지도의  레벨을 얻어옵니다
				    var level = map.getLevel();
				    // 지도의 중심좌표를 얻어옵니다 
				    var latlng = map.getCenter();
 
				    var message = '지도 레벨: ' + level + ' / ' + '현 중심 좌표: 위도 ' + latlng.getLat() + ', 경도 ' + latlng.getLng();
				    
				    var resultDiv = document.getElementById('result_2');
				    resultDiv.innerHTML = message;
				});
							
								
				// 지도가 이동, 확대, 축소로 인해 지도영역이 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
				kakao.maps.event.addListener(map, 'bounds_changed', function() {
					// [힌트]이 bounds_changed 이벤트가 발생 때는, 확장/축소 된 영역 내에서 그에 비례해 정보들이 표시되도록 새로 어떤 처리를 할 신호탄으로서 사용할 수 있을듯.
							
				});
						
						
				// 타일로드 이벤트: '지도상에 로드되어야 할 그래픽적 컴포넌트들(tile)이 다 로드가 완료됐을 때의 이벤트'
				kakao.maps.event.addListener(map, 'tilesloaded', displayMarker);
				function displayMarker() {
					//alert('all tiles loaded!');
				}
						
								
				// [마커에 대한 인포윈도우(infoWindow)]
				// 마커를 클릭 시 뜰 인포윈도우 내용
				var clickIwContent = '<div style="padding:5px;">클릭한 가게에 대한 상세정보 나온다!</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
				// 마커 위에 마우스를 올려놓을 시 뜰 인포윈도우 내용
				var overIwContent = '<div style="padding:5px;">마우스오버 시 뜨는 내용!</div>';
						
				var iwRemoveable = true; // removeable 속성을 true로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
				
				// 마커를 클릭했을 때 뜨는 인포윈도우를 생성합니다
				var clickInfoWindow = new kakao.maps.InfoWindow({
				    content : clickIwContent,
				    removable : iwRemoveable
				});
				// 마커를 마우스오버했을 때 뜨는 인포윈도우를 생성합니다
				var overInfoWindow = new kakao.maps.InfoWindow({
				    content : overIwContent,
				    removable : iwRemoveable
				});
						
				// [마커에 '클릭 이벤트' 등록]
				kakao.maps.event.addListener(marker, 'click', function(){
				    // 마커 위에 인포윈도우를 표시합니다
				    clickInfoWindow.open(map, marker);  
				});
						
				// [마커에 '마우스오버 이벤트'를 등록]
				kakao.maps.event.addListener(marker, 'mouseover', function(){
					// 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
				    overInfoWindow.open(map, marker);
				});
			
				// [마커에 '마우스아웃 이벤트'를 등록]
				kakao.maps.event.addListener(marker, 'mouseout', function(){
				    // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
				    overInfoWindow.close();
				});
				
			}// end if-else
					
		}); // end 'navigator.geolocation.getCurrentPosition() 콜백함수'
					
	} //end if-else
	
// 함수부분은 따로 다른 js파일로 빼놓았다.			
