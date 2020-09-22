package my.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO { // 유저에 대한 각종 처리를 담당하는 UserDAO 클래스
	
	@Autowired
	private SqlSessionTemplate ss;
	
	// 카카오계정의 kname을 이용해 디비에서 u_idx를 찾아주는 메소드 findUserIdx()
	public int findUserIdx(String kname) {
		System.out.println("[UserDAO의 findUserIdx() 진입]");
		System.out.println("유저 kname: "+kname);
		//유저 아이디 검색
		int u_idx = ss.selectOne("weather.findUserIdx", kname);
		System.out.println("u_idx값: "+u_idx);
		return u_idx;
	}// end findUserIdx()
	
	/*public int testFind() {
		System.out.println("[UserDAO 진입]");
		int testVar = ss.selectOne("weather.findUserIdx","Kevin");
		System.out.println("[UserDAO]: testVar: "+testVar);
		return testVar;
	}*/


}
