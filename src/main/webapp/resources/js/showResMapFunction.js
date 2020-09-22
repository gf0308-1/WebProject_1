/**
 *   showResMap.js에서 쓰이는 함수들 js파일 
 */

// 지도상에 클릭한 지점을 화면중심으로 삼아 화면조정을 해주는 함수 setNewCenter()
function setNewCenter() {
	map.panTo(clickedLatLng); // new kakao.map.LatLng()와 같은 것 => LatLng객체를 투입해줘야 함.
	marker.setPosition(clickedLatLng);
}		

// 지도화면의 원래 디폴트 중심부로 화면전환해주는 함수
function moveToOriCenter() {
	map.setCenter(oriCenterLatLng);
	marker.setPosition(oriCenterLatLng);
}
// 지도화면의 원래 디폴트 중심부로 화면전환을 좀더 부드럽게 애니메이션처럼 해주는 함수
function panToOriCenter() {
	map.panTo(oriCenterLatLng);
	marker.setPosition(oriCenterLatLng);
}


// 지도 화면 상의 '정보'를 얻어오는 기능 
function getInfo() {
	// 지도의 현재 중심좌표를 얻어옵니다 
    var center = map.getCenter(); 
    // 지도의 현재 레벨을 얻어옵니다
    var level = map.getLevel();
    // 지도타입을 얻어옵니다
    var mapTypeId = map.getMapTypeId(); 		    
    // 지도의 현재 영역을 얻어옵니다 
    var bounds = map.getBounds();		    
    // 영역의 남서쪽 좌표를 얻어옵니다 
	var swLatLng = bounds.getSouthWest(); 		    
	// 영역의 북동쪽 좌표를 얻어옵니다 
	var neLatLng = bounds.getNorthEast();     
	// 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
	var boundsStr = bounds.toString();		    
			    
	var message = '지도 현 중심좌표는 \n위도: ' + center.getLat() + '\n';
	message += '경도: ' + center.getLng() + '\n';
	message += '지도 레벨: ' + level + '\n';
	message += '지도 타입: ' + mapTypeId + '\n';
	message += '지도의 남서쪽 좌표: (' + swLatLng.getLat() + ', ' + swLatLng.getLng() + ') \n';
	message += '북동쪽 좌표: (' + neLatLng.getLat() + ', ' + neLatLng.getLng() + ') \n';	     // 왜 북서, 남동 좌표 객체는 없지?
	//message += '남동쪽 좌표: (' + seLatLng.getLat() + ', ' + seLatLng.getLng() + ') \n';	    
	alert(message);
}
