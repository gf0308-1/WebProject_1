/**
 * test_searchAndList.jsp 에 쓰이는 js소스
 */
		// 크롬 브라우저 상의 개발자 console 화면에서, 본 스크립트가 로드 되었음을 확인하기 위한 코드 
		(function() {
			console.log("[test_searchAndList.js loaded...33!]");			
		})();

		// 마커를 담을 배열입니다
		var markers = [];
		// 내 원 위치
		var oriLat = document.getElementById('original_latitude').value;
		var oriLng = document.getElementById('original_longitude').value;
		var oriCoordinate = new kakao.maps.LatLng(oriLat,oriLng); // 원 좌표 객체 생성
		
		/*  container: 지도화면 + 로드뷰화면 을 모두 담고 있을 컨테이너
			mapContainer: 지도화면을 직접 담는 컨테이너
			rvContainer: 로드뷰화면을 직접 담는 컨테이너
			mapWrapper: 맵래퍼를 가리키는 변수
			btnRoadview: 로드뷰버튼을 가리키는 변수
			btnMap: 지도버튼을 가리키는 변수
		*/
		// 나는 '마커에 딸린 로드뷰' 식으로 구현을 한다.
		var container = document.getElementById('container'),
			mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
		    btnRoadview = document.getElementById('btnRoadview'), // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다 
		    btnMap = document.getElementById('btnMap'), // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다 
		    rvContainer = document.getElementById('roadview');
		
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        //center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표 - 아무데나 띄워둔것임(일단 지도띄우고 보려고: 서울시청 좌표)
				center: oriCoordinate, //oriCoordinate,
		        level: 3 // 지도의 확대 레벨 // 지도의 레벨을 처음에 설정값을 줄수는 있으나, 지도객체의 bounds가 어떻게 되어지느냐에 따라서 이 level은 그냥 씹히고 bounds에 의해 지도 크기가 자동으로 조정되어져 표시될 수 있다. 
		    };  
		
		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);
		
		/*// 지도타입 컨트롤러(조절기) 설정
		var mapTypeControl = new kakao.maps.MapTypeControl();
		map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOP);*/

		//==============================
		
		// [지도가 이동, 확대, 축소로 인해 중심좌표가 변경되면 발생할 이벤트]
		kakao.maps.event.addListener(map, 'center_changed', function() {
		    // 지도의  레벨을 얻어옵니다
		    var level = map.getLevel();
		     
		    var message = '지도 레벨: '+level;
		    
		    var resultDiv = document.getElementById('result');
		    resultDiv.innerHTML = message;
		});
		    
		//==============================    

		// 내 위치 마커를 표시한다.
		setMyMarker();

		// 장소 검색 객체를 생성합니다
		var ps = new kakao.maps.services.Places();  
		
		// 키워드로 장소를 검색합니다
		searchPlaces();
				
		// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({zIndex:1});
		
		
		
		
		
		// ======= 함수 ==========================
		// 키워드 검색을 요청하는 함수입니다
		function searchPlaces() {
		
		    var keyword = document.getElementById('keyword').value;
			// 키워드에 대한 유효성 검사!
		    if (!keyword.replace(/^\s+|\s+$/g, '')) {
		        alert('키워드를 입력해주세요!');
		        return false;
		    }
		
		    // '장소검색 객체'를 통해 키워드로 장소검색을 요청합니다
		    ps.keywordSearch(keyword, placesSearchCB); // 여기서 ps.keywordSearch()로 동작을 수행하면, 리턴으로 '해당주소에 대응하는 좌표값'이 온다(+ status, pagination 값) -> 그들을 받아서placesSearchCB 함수가 콜백함수로서 이후 처리를 한다  
		}
		
		// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
		function placesSearchCB(data, status, pagination) { // 여기서의 data값은 '입력된주소에해당하는 좌표값'이다 /
		    if (status === kakao.maps.services.Status.OK) {
		
		        // 정상적으로 검색이 완료됐으면
		        // 검색 목록과 마커를 표출합니다
		        displayPlaces(data);
		
		        // 페이지 번호를 표출합니다
		        displayPagination(pagination);
		
		    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
		        alert('검색 결과가 존재하지 않습니다.');
		        return;

		    } else if (status === kakao.maps.services.Status.ERROR) {
		        alert('검색 결과 중 오류가 발생했습니다.');
		        return;
		    }
		}
		
		// 검색 결과 목록과 마커를 표출하는 함수입니다
		function displayPlaces(places) {
		
		    var listEl = document.getElementById('placesList'), 
			    menuEl = document.getElementById('menu_wrap'),
			    fragment = document.createDocumentFragment(), 
			    bounds = new kakao.maps.LatLngBounds(), 
			    listStr = '';
		    
		    // 검색 결과 목록에 추가된 항목들을 제거합니다
		    removeAllChildNods(listEl);
		
		    // 지도에 표시되고 있는 마커를 제거합니다
		    removeMarker();
		    
		    for ( var i=0; i<places.length; i++ ) {
		
		        // 마커를 생성하고 지도에 표시합니다
		        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
		            marker = addMarker(placePosition, i), // addMarker()함수로 마커를 추가로 만든다
		            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
		
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
				bounds.extend(placePosition);
		
		        // 마커와 검색결과 항목에 mouseover 했을때
		        // 해당 장소에 인포윈도우에 장소명을 표시합니다
		        // mouseout 했을 때는 인포윈도우를 닫습니다
		        (function(marker, title) {
		            kakao.maps.event.addListener(marker, 'mouseover', function() {
		                displayInfowindow(marker, title);
		            });
		
		            kakao.maps.event.addListener(marker, 'mouseout', function() {
		                infowindow.close();
		            });
							
		            itemEl.onmouseover =  function() {
		                displayInfowindow(marker, title);
		            };
		
		            itemEl.onmouseout =  function () {
		                infowindow.close();
		            };
		        })(marker, places[i].place_name);
		
		        fragment.appendChild(itemEl);
		    }
		
		    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
		    listEl.appendChild(fragment);
		    menuEl.scrollTop = 0;
		
		    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			//map.setBounds(bounds); // => 처음 지도화면에 진입했을 때 유저입장에서, '지도상으로 3단계 정도의 지도화면크기' 내에서 내위치와 주변맛집이 뜨는 것이 더 UI/UX상으로 좋아보임 -> 해서 자동으로 bounds조정해 지도화면크기를 조절하는 것을 비활성화시켰다.
		}
			
	
		// 검색결과 항목을 Element로 반환하는 함수입니다
		function getListItem(index, places) {
		
		    var el = document.createElement('li'),
		    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
		                '<div class="info">' +
		                '   <h5>' + places.place_name + '</h5>';
		
		    if (places.road_address_name) {
		        itemStr += '    <span>' + places.road_address_name + '</span>' +	
		                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
		    } else {
		        itemStr += '    <span>' +  places.address_name  + '</span>'; 
		    }
		                 
		      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
		                '</div>';           
		
		    el.innerHTML = '<a href="javascript:window.open(\' '+places.place_url+'\',\''+places.place_name+'\',\'width=940px,height=940px, left=500px\');">'+itemStr+'</a>';
			// [이슈1] ㄴ 위의 팝업창띄우는 부분에서, 창이 화면 중앙으로 오게 하는 수정작업 차후에 하기(지금은 급하지 않으므로)
			//el.innerHTML = '<a href="'+places.place_url+'" target="_blank">'+itemStr+'</a>';
		    el.className = 'item';
		
		    return el;
		}
		
		// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
		function addMarker(position, idx, title) {
		    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
		        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
		        imgOptions =  {
		            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
		            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
		        },
		        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
		        marker = new kakao.maps.Marker({
		            position: position, // 마커의 위치
		            image: markerImage 
		        });
			
				// 각 마커에 마우스클릭을 시행했을 시 : "화면중심이동 + 목록에 세부내용 띄우기"
				kakao.maps.event.addListener(marker, 'click', function() {
					// 화면 중심 이동
					map.setCenter(position);
					// 세부 내용 띄우기
					//alert('세부내용 표시!');
					
					// 해당마커위치의 로드뷰 띄우기 // 마커좌표:position
					// 로드뷰 객체 생성
					var roadview = new kakao.maps.Roadview(rvContainer);
					// 특정좌표를 주면 해당좌표에서 가장 가까운 파노라마id를 계산해내는 객체 roadviewClient 를 생성한다		
					var roadviewClient = new kakao.maps.RoadviewClient(); 

					// 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
					roadviewClient.getNearestPanoId(position, 50, function(panoId) {
					    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
					});
							
					// 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다 
					// Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
					roadview.setViewpoint({
					    pan: 321,
					    tilt: 0,
					    zoom: 0
					});
			
					// 로드뷰 초기화가 완료되면 
					kakao.maps.event.addListener(roadview, 'init', function() {
					    // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다 
					    var rvMarker = new kakao.maps.Marker({
					        position: position,
					        map: roadview
					    });
					});
					console.log('해당마커위치의 roadview생성 완료!');
				});// end 'click' Listener 
			
			
		    marker.setMap(map); // 지도 위에 마커를 표출합니다
		    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
		
		    return marker;
		}
		
		// 지도 위에 표시되고 있는 마커를 모두 제거합니다
		function removeMarker() {
		    for ( var i = 0; i < markers.length; i++ ) {
		        markers[i].setMap(null);
		    }   
		    markers = [];
		}
		
		// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
		function displayPagination(pagination) {
		    var paginationEl = document.getElementById('pagination'),
		        fragment = document.createDocumentFragment(),
		        i; 
		
		    // 기존에 추가된 페이지번호를 삭제합니다
		    while (paginationEl.hasChildNodes()) {
		        paginationEl.removeChild(paginationEl.lastChild);
		    }
		
		    for (i=1; i<=pagination.last; i++) {
		        var el = document.createElement('a');
		        el.href = "#";
		        el.innerHTML = i;
		
		        if (i===pagination.current) {
		            el.className = 'on';
		        } else {
		            el.onclick = (function(i) {
		                return function() {
		                    pagination.gotoPage(i);
		                }
		            })(i);
		        }
		
		        fragment.appendChild(el);
		    }
		    paginationEl.appendChild(fragment);
		}
		
		// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
		// 인포윈도우에 장소명을 표시합니다
		function displayInfowindow(marker, title) {
		    var content = '<div style="padding:5px;z-index:1;text-align:center;">' + title + '</div>';
		
		    infowindow.setContent(content);
		    infowindow.open(map, marker);
		}
		
		 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
		function removeAllChildNods(el) {   
		    while (el.hasChildNodes()) {
		        el.removeChild (el.lastChild);
		    }
		}
		 
		function setMyMarker() {
			// 내 원위치를 표시해주는 마커의 커스터마이징을 하는 부분
			var imageSrc = 'resources/img/marker_red.png' , // 마커이미지의 주소입니다 // 'https://lakelandescaperoom.com/wp-content/uploads/2019/11/google-map-marker-red-peg-png-image-red-pin-icon-png-clipart-pins-on-a-map-png-880_1360.jpg' // '/weather/resources/img/marker_red.png'    
			   	imageSize = new kakao.maps.Size(34, 39), // 36, 37	
			   	imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다. : 27, 69 // 13, 37
			// 마커이미지 객체를 생성
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
			
			// 지도 상에 내 위치지점에 마커를 띄운다
			var myMarker = new kakao.maps.Marker({
				position: map.getCenter(),
				image: markerImage,
				clickable: true
			}); // 내 위치 마커 생성
			
			myMarker.setMap(map); // 생성한 마커를 지도객체에 표출한다.
			
			// 그 마커에 대한 인포윈도우도 만들어준다
			var myMarkerIW = setMyMarkerIW();
			
			// [리스너로 마커에 '마우스오버 이벤트'를  등록]
			kakao.maps.event.addListener(myMarker, 'mouseover', function(){
				// 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
				myMarkerIW.open(map, myMarker);
			});
			kakao.maps.event.addListener(myMarker, 'mouseout', function(){
				// 마커에서 마우스를 떼었을 시 인포윈도우 없앰
				myMarkerIW.close();
				//alert('닫기!');
			});	
			
			
		}// end setMyMarker()
		
		function setMyMarkerIW() {
			// 나의 위치 표시 마커에 인포윈도우도 달아준다
			var iwContent = '<div style="color:blue;">내 위치</div>'; //padding:5px;
			var myMarkerIW = new kakao.maps.InfoWindow({
				content: iwContent,
				position: map.getCenter()
			});
			// 내 최초 위치의 마커 생성 완료
			return myMarkerIW;
		}
		
		function backToOriCenter() {
			console.log('backToOriCenter() 호출됨');
			console.log('원중심좌표값: '+map.getCenter()); // map.getCenter() 로 얻어지는 반환좌표값은, '(위도좌표,경도좌표)' 식의 '문자열 값' 이다.
			map.panTo(oriCoordinate); // oriCoordinate
		}
		
		
		// 지도와 로드뷰를 감싸고 있는 div의 class를 변경하여 지도를 숨기거나 보이게 하는 함수입니다 
		function toggleMap(active) {
		    if (active) {
		        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
		        container.className = "view_map"
					console.log('container.className: '+ container.className);
		    } else {
		        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
		        container.className = "view_roadview"
					console.log('container.className: '+ container.className);   
		    }
		}
		
		