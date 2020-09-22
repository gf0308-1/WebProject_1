package my.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import my.dao.MenuDAO;
import my.vo.MenuVO;

public class MenuUtil {
	
	
	@Autowired
	private MenuDAO menuDao;	
	
	//==============멤버 필드========================
	// 현재 온도
	private static int nowTemp = 0;
	// 계절 구간 a,b
	private static String season_a = null,
						  season_b = null;
	// 계절 별 가중치 a,b
	private static int weight_a = 0,
					   weight_b = 0;
	//============================================
	
	
	public MenuUtil() {
		
	}
	
	public MenuVO[] getMenuList(int temp, int month) {
		System.out.println("getMenuList() 진입!");

		nowTemp = temp; // 파라미터로 받아온 현재기온값을 여기 멤버필드에 초기화

		// <'현재의 계절 파악 + 어디 계절에 더 가까운지 + 계절기준치에 대한 근사값을 통한 가중치 구하기' 로직>
		// [정상적인 봄/가을 기온 및 봄/가을 날짜 일 때]
		if ((temp >= 2 && temp <= 23) && ((month >= 3 && month <= 5) || (month >= 10 && month <= 11))) { // 2~23 도 사이의
																											// 기온이 일 경우
																											// => '봄
																											// 기온'이지만,
																											// 동시에
																											// '가을기온'도
																											// 포함하고 있는
																											// 상태
			// 봄인인지 가을인지, 날짜를 통해 판별해 분기해준다
			if ((temp >= 5 && temp <= 21) && (month >= 10 && month <= 11)) { // => 가을 일 경우!
				// 가을
				calculateWhen("autumn"); // 가을일 경우의 '계절구간범위 + 각 가중치'를 구한다
			} else if (month >= 3 && month <= 5) { // => 봄 일 경우! (봄 기온 범위는 이미 만족한 상태이고, 날짜만 3~5월이면 봄이란 것이 확실하므로)
				// 봄
				calculateWhen("spring"); // 봄일 경우의 '계절구간범위 + 각 가중치'를 구한다
			}
		} else if (temp >= 18 && (month >= 6 && month <= 9)) { // [정상적인 여름 기온 및 날짜일 때]
			// 여름 // 기온이 18도 이상이면, 기온 상 여름일 조건은 만족 + (30년간 통계로 가을시작일이 9월28일인데, 여름마지막일은 9월
			// 27일이므로)여름 해당 월은 6,7,8,9월 이다
			calculateWhen("summer"); // 여름일 경우의 '계절구간범위 + 각 가중치'를 구한다
		} else if (temp <= 7 && (month == 12 || month == 1 || month == 2)) { // [정상적인 겨울 기온 및 날짜 일 때]
			// 겨울 // (30년간 통계로) 기온이 7도 이하면, 기온 상 겨울인 조건은 만족 + 겨울의 월은 12,1,2월 임
			calculateWhen("winter"); // 겨울일 경우의 '계절구간범위 + 각 가중치'를 구한다
		} else { // 이 외의 경우가 발생했을 때
			// => 정상 시나리오 안에 있지 않은 예외 상황 발생 => 에러페이지로 이동해서 예외상황 에러메세지를 표시해준다
			System.out.println("정상 시나리오 안에 있지 않은 예외상황 발생 : '기후변화로 인한 이상기후 입니다.'");
			ModelAndView mv = new ModelAndView();
			mv.addObject("message", "날씨를 기반으로 메뉴를 추천하는 과정에서 문제 발생 : '기후변화로 인한 이상기후 입니다.'");
			mv.setViewName("redirect:/errorPage");
/************************************************************ 에러페이지 이동 처리 해야함 ****************************************************************************/
		}
		// 조건문 종료 // 현재계졀, 계절 구간 범위, 계절메뉴별 가중치 모두 구함

		// 화면에 출력해 줄 총 메뉴 갯수
		int total_menu_count = 6; // int형 변수는 초기화하지 않으면 자동으로 보통 0으로 초기화가 이뤄짐(하지만, 누적을 위해 사용할 것일 경우, 확실히 0 으로 명시적
									// 초기화 해주는 것이 좋다)
		int menu_a_count = (int) Math.round(total_menu_count * (weight_a * 0.01)); // season_a에 대해 tbl_menu 테이블에서 뽑아야 할
																					// 메뉴 갯수
		int menu_b_count = total_menu_count - menu_a_count; // season_b에 대해 tbl_menu 테이블에서 뽑아야 할 메뉴 갯수

		// 여태까지의 내용 확인 출력 메소드
		printThem(season_a, season_b, weight_a, weight_b, menu_a_count, menu_b_count);

		// DAO 를 호출하여 메뉴들을 DB에서 얻어오는 작업 하기
		List<MenuVO> list = menuDao.getMenus(season_a, season_b, menu_a_count, menu_b_count);
		System.out.println("DAO 메소드 getMenus() 수행 정상 완료 => list 얻어냄 \n \n \n");
		// 얻어낸 list를, 더 처리하기에 부담없는 고정배열로 만든다
		MenuVO[] mvoArr = null;

		if (list != null && list.size() > 0) { // list가 값이 제대로 들어있을 경우
			mvoArr = new MenuVO[list.size()]; // 가변배열 list 의 배열내 요소 갯수만큼, 고정 배열 요소 갯수를 삼아서 만들어준다
			list.toArray(mvoArr);
		}

		// DAO 처리 결과로, (결과물이 제대로 왔던 안왔던 간에,) 최종적으로 결과물인 고정배열 mvoArr가 완성됨
		return mvoArr;
	}// end getMenuList() 메소드

	// 계절 별 범위 구간 + 각 가중치 계산하는 메소드 calculateWhen()
	public void calculateWhen(String season) {
		// 계절 별 기준치 기온값(season_standard_temperature)
		int sst_a = 0, // 비교치1
			sst_b = 0, // 비교치2
			sst_self = 0; // 자기자신 기준치

		switch (season) {
		case "spring": // 봄 일 경우
			sst_a = 24; // 다음 계절인 여름의 기준치값
			sst_b = 1; // 이전 계절인 겨울의 기준치값
			sst_self = 12; // 봄 자기자신 기준치값
			break;
		case "summer":
			season_a = "summer";
			season_b = null;
			weight_a = 100;
			weight_b = 0;
			break;
		case "autumn": // 가을 일 경우
			sst_a = 1; // 다음 계절인 겨울의 기준치값
			sst_b = 24; // 이전 계절인 여름의 기준치값
			sst_self = 13; // 가을 자기자신 기준치값
			break;
		case "winter":
			season_a = "winter";
			season_b = null;
			weight_a = 100;
			weight_b = 0;
			break;
		}// end switch문

		if (season.equals("spring") || season.equals("autumn")) { // 계절값이 '봄' 또는 '가을' 일 경우만 수행한다 (세부수행작업)
			// 계절 구간 파악 로직
			if (Math.abs(nowTemp - sst_a) < Math.abs(nowTemp - sst_b)) { // 다음 계절에 더 가까운 경우
				if (season.equals("spring")) { // 봄 일 경우
					season_a = "spring";
					season_b = "summer";
				} else if (season.equals("autumn")) {
					season_a = "autumn";
					season_b = "winter";
				}
				weight_a = 100 - Math.round((Math.abs(nowTemp - sst_self) * 100)
						/ (Math.abs(nowTemp - sst_self) + Math.abs(nowTemp - sst_a)));
				weight_b = 100 - weight_a;

			} else { // 이전 계절에 더 가까운 경우
				if (season.equals("spring")) { // 봄 일 경우
					season_a = "spring";
					season_b = "winter";
				} else if (season.equals("autumn")) {
					season_a = "autumn";
					season_b = "summer";
				}
				weight_a = 100 - Math.round( (Math.abs(nowTemp - sst_self) * 100) 
											/ (Math.abs(nowTemp - sst_self) + Math.abs(nowTemp - sst_b)) );
				weight_b = 100 - weight_a;
			}
		} // end if

	}// end calculateWhen() 메소드

// 중간 내용 확인 용 print 메소드
	public void printThem(String season_a, String season_b, int weight_a, int weight_b,
						int menu_a_count, int menu_b_count) {
		System.out.println("=====================================");
		System.out.println("[season_a]: " + season_a);
		System.out.println("[season_b]: " + season_b);
		System.out.println("[weight_a]: " + weight_a);
		System.out.println("[weight_b]: " + weight_b);
		System.out.println("[season_a_menu]: " + menu_a_count);
		System.out.println("[season_b_menu]: " + menu_b_count);
		System.out.println("=====================================");
	}

}
