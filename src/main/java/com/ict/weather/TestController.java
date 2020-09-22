package com.ict.weather;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import my.dao.ExportCSVDAO;
import my.dao.HistoryDAO;
import my.vo.MenuVO;
import my.vo.UserMenuHitVO;

@Controller
public class TestController {
	
	@Autowired
	private SqlSessionTemplate ss;
	@Autowired
	private HistoryDAO hDao;
	
	// HistoryDAO의 getUserMenuHit() 이 잘 작동하는지 확인하기 위한 매핑 메소드
	@RequestMapping("/checkHistoryMethod")
	public ModelAndView checkHistoryMethod() {
		List<UserMenuHitVO> testList = hDao.getUserMenuHit(); // HistoryDAO 클래스에 대한 접근을 static으로 하지 않고, 스프링에서의 매커니즘대로 IOC적 bean객체활용을 하니까, 정상적으로 작동된다!
		ModelAndView mv = new ModelAndView();
		mv.addObject("testList",testList);
		mv.setViewName("test/testPage");
		return mv;
	}
	
	
	
	
	// 지도와 로드뷰 토글 기능 테스트 컨트롤러 메소드
	@RequestMapping("/mapAndRoadViewToggle")
	public String mapAndRoadViewToggle() {
		return "test/mapAndRoadViewToggle";
	}
	
	
	// 모달창 연습 화면 진입 메소드
	@RequestMapping("/modal")
	public String modal() {
		return "test/modalPanel";
	}
	
	/*public String mybatisTest() {
		System.out.println("테스트중");
		return "errorPage";
	}*/
	
	// 테스트 메소드
	@RequestMapping("/mybatis_test")
	public ModelAndView mybatisTest(String sa, String sb, String ac, String bc) {
		
		Map<String,String> map = new Hashtable<String,String>();
		
		map.put("season_a", sa);
		map.put("season_b", sb);
		map.put("menu_a_count", ac);
		map.put("menu_b_count", bc);

		List<MenuVO> list = ss.selectList("weather.getMenus", map);
		
		ModelAndView mv = new ModelAndView();
		
		if(list != null) {
			System.out.println("mybatis정상실행!");			
		} else {
			System.out.println("mybatis가 정상실행되지 않음");
		}
		mv.addObject("mv", list);
		mv.setViewName("errorPage");
		
		return mv;
	}
	
	
	

}
	