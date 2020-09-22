package my.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import my.vo.MenuVO;

public class MenuDAO {
	
	// DB와 직접 상호작용하기 위한 SqlSessionTemplate 객체를 자동으로 가져와지게  하기
	@Autowired
	private SqlSessionTemplate ss;
	
	// 오늘의메뉴추천 제공을 위한 메소드 getMenus()
	public List<MenuVO> getMenus(String season_a, String season_b, int menu_a_count, int menu_b_count) {
			System.out.println("[DAO]: getMenus() 진입!");
		
		// 관련된 sql 매퍼 객체에 필요한, Map타입의 파라미터객체로 투입해줄 Map객체를 생성한다
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("season_a", season_a);
		map.put("season_b", season_b);
		map.put("menu_a_count", String.valueOf(menu_a_count));
		map.put("menu_b_count", String.valueOf(menu_b_count));

		List<MenuVO> list = ss.selectList("weather.getMenus", map); // <--- 여기가 문제
		
			System.out.println("[DAO]: ss.selectList(\"weather.getMenus\", map) 수행 완료! => list 얻어냄");
		
		return list;
	}
	
	
	//아이템id를 넣어주면
	public MenuVO getMenuOne(int item){
		
		MenuVO vo = ss.selectOne("weather.getItem",String.valueOf(item));
		
		return vo;
	}
	
	
	// 머하웃으로 추천된 메뉴들을 디비에서 찾아서(이미지 등) 리턴해주는 메소드 getMahoutMenuList()
	public List<MenuVO> getMahoutMenuList(List<Integer> items) {
		List<MenuVO> mahoutList = new ArrayList<MenuVO>();
				
		for(int i=0; i<items.size(); i++) {
			int inputInt = items.get(i);
			// sql 매퍼객체를 호출하여 select 처리 시행
			MenuVO recMenu = ss.selectOne("weather.getMahoutMenuOne", inputInt);
			mahoutList.add(recMenu);
		}
		// getMahoutMenuList() 정상종료
		return mahoutList;
	}// getMahoutMenuList()
	
	
	
	
	
}
