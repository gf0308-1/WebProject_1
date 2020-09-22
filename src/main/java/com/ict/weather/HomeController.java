package com.ict.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.JsonNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import my.dao.HistoryDAO;
import my.dao.MenuDAO;
import my.util.InfoUtil;
import my.util.LoginUtil;
import my.util.MenuUtil;
import my.vo.MenuVO;
import my.vo.ResVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private HistoryDAO hDao;
	@Autowired
	private MenuUtil menuUtil;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session) throws Exception {
		
		/**네이버 날씨 정보 크롤링**/
		//String WeatherURL = "https://weather.naver.com/today";
		String WeatherURL2 = "https://search.naver.com/search.naver?sm=top_hty&fbm=0&ie=utf8&query=%EB%82%A0%EC%94%A8";
		//Document doc = Jsoup.connect(WeatherURL).get();
		Document doc = Jsoup.connect(WeatherURL2).get();
		
		Element area = doc.select(".btn_select").get(0); // .btn_select로 얻어지는 값이 2가지임: '김포시 양촌읍'과 '9월 예보' 두 가지 값이 얻어져버림 -> 첫번째값만 가져오도록 한다

		Elements now = doc.select(".todaytemp");
		//최저 최고 기온 상위 태그
		Elements todayTemp = doc.select(".merge");
		Elements min = todayTemp.select(".min");
		Elements max = todayTemp.select(".max");
		//체감기온 상위 태그
		Elements sensible = doc.select(".sensible");
		Elements sens = sensible.select(".num");

		ModelAndView mv = new ModelAndView();

		//오늘 날씨 값만 가져오기 위해서 
		//문자열 자르기를 안하면 다음 다다음 온도 값도가져오게 된다.
		String nowTemp = now.toString().substring(24,26);
		
		mv.addObject("area",area);
		mv.addObject("now",nowTemp);
		mv.addObject("max", max);
		mv.addObject("min", min);
		mv.addObject("sens", sensible);

		
		
		
		/**네이버 맛집 리스트 처리**/
		String[] ar = { "1229825670", "1017474885", "37281730", "1479575962", "1521482138", "35402809", "1649477961",
				"1048166619", "882130308", "1208774602","1508260607" };

		// json형태의 리스트
		List<JsonNode> array = new ArrayList<JsonNode>();

		List<ResVO> real = new ArrayList<ResVO>();

		for (int i = 0; i < ar.length; i++) {
			
			//식당정보 리스트 가져오기
			ResVO vo = InfoUtil.getInfo(ar[i]);
			
			real.add(vo);
		}
		
		mv.addObject("list", real);
		
		
		
		/**카카오 로그인**/
		String kakaoUrl = LoginUtil.getAuthorizationUrl(session);

		mv.addObject("kakao_url", kakaoUrl); // kakaoUrl 안에 카카오유저의id도 url파라미터안에 포함되어있다
		mv.setViewName("login");
		
		
		/**계절 메뉴 추천 리스트**/
		Date date = Calendar.getInstance().getTime(); //현재 날짜 데이터
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM",Locale.getDefault());
															// -> MM 부분 : 연/월/일 표시에서, MM은 간혹 약간빗나간값이나오는경우가있었던거같은데 -> mm이 더 정확하고 일관되었던 값리턴해줬던 것같음-?
		int month = Integer.parseInt(monthFormat.format(date));
		
		int temp = Integer.parseInt(nowTemp);
			System.out.println("temp: "+temp);
			System.out.println("month: "+month);
		// 오늘의메뉴추천기능의 MenuUtil클래스객체를 생성해서 getMenuList(기온값,날짜값)를 호출하여 추천메뉴리스틑를 얻는다.
		//MenuVO[] menuList = new MenuUtil().getMenuList(temp, month);
		
		MenuVO[] mvoList = menuUtil.getMenuList(temp, month);
		
		
		
		mv.addObject("mvoList", mvoList);
		mv.setViewName("home");
		return mv;
	}

}
