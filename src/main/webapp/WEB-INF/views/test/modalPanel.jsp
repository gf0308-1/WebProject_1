<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Center Modal</title>
	<!-- include jQuery :) -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
	
	<!-- jQuery Modal -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
	
    <style>
        #my_modal {
            display: none;
            width: 300px;	
            padding: 20px 60px;
            background-color: #fefefe;
            border: 1px solid #888;
            border-radius: 3px;
        }

        #my_modal .modal_close_btn {
            position: absolute;
            top: 10px;
            right: 10px;
        }
    </style>
</head>

<body>
    <div id="my_modal">
        Lorem ipsum, dolor sit amet consectetur adipisicing elit. Expedita dolore eveniet laborum repellat sit distinctio, ipsa rem dicta alias velit? Repellat doloribus mollitia dolorem
        voluptatum ex reiciendis aut in incidunt?
        <a class="modal_close_btn">닫기</a>
    </div>

    <button id="popup_open_btn">팝업창 띄어 줘염</button>
    
    <br><br>
    
    <a id="link" href="http://naver.com">네이버링크</a>

    <script>
        function modal(id) {
            var zIndex = 9999;
            var modal = document.getElementById(id);

            // 모달 div 뒤에 희끄무레한 레이어
            var bg = document.createElement('div');
            bg.setStyle({
                position: 'fixed',
                zIndex: zIndex,
                left: '0px',
                top: '0px',
                width: '100%',
                height: '100%',
                overflow: 'auto',
                // 레이어 색갈은 여기서 바꾸면 됨
                backgroundColor: 'rgba(0,0,0,0.4)'
            });
            document.body.append(bg);

            // 닫기 버튼 처리, 시꺼먼 레이어와 모달 div 지우기
            modal.querySelector('.modal_close_btn').addEventListener('click', function() {
                bg.remove();
                modal.style.display = 'none';
            });

            modal.setStyle({
                position: 'fixed',
                display: 'block',
                boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',

                // 시꺼먼 레이어 보다 한칸 위에 보이기
                zIndex: zIndex + 1,

                // div center 정렬
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                msTransform: 'translate(-50%, -50%)',
                webkitTransform: 'translate(-50%, -50%)'
            });
        }

        // Element 에 style 한번에 오브젝트로 설정하는 함수 추가
        Element.prototype.setStyle = function(styles) {
            for (var k in styles) this.style[k] = styles[k];
            return this;
        };

        document.getElementById('popup_open_btn').addEventListener('click', function() {
            // 모달창 띄우기
            //modal('my_modal');
           
        });
    </script>
    	
	    
	<!-- modal에 띄우고자하는 코드 -->
	<div id="test" class="modal">
	  <p>떴다~ 떴다~ 모달창!</p>
	  <a href="#" rel="modal:close"><button>닫기</button></a>    <!-- 닫기버튼 -->
	</div>
	
	<!-- href 속성값을 사용하여 modal을 띄워주는 링크 -->
	<p>
		<a href="#test" rel="modal:open">
			<button>모달창 띄우기</button>
		</a>
	</p>
	
	<!--  외부의 html파일을 modal에 띄우기-->
	<!-- modal버튼이 있는 html -->
	<a href="modalPanel.jsp" rel="modal:open">
	  <button>다른 html을 모달로!</button>
	</a>
	<!-- ex.html -->
	<p>이건 ex.html입니다~</p>
	<a href="#" rel="modal:close"><button>닫기</button></a>
     


</body>
</html>