package my.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import my.vo.UserMenuHitVO;

public class HistoryDAO {

	@Autowired
	private SqlSessionTemplate ss;
	// user_menu_hit 내역을 조회해서 spool로 csv화해 엑스포트하는 메소드 : spoolCSV()
	/*public void spoolCSV() {
		System.out.println("csv파일 생성 직전");
		ss.selectList("weather.gen_csv", "testfile_1");
		System.out.println("csv파일 생성 완료");
	}*/ // --- 이 방식은 사용 일단 안한다(mybatis환경에서 spool이 가능한지 모르겠음)
	
	/*public static String execute() {
		System.out.println("[execute() 진입]");
		//List<UserMenuHitVO> list = ss.selectList("weather.getHit");
		String str = ss.selectOne("whether.test");
		//System.out.println("ss.selectList(\"weather.getHit\") 수행 완료");
		return str;
	}*/
	
	
	
	// getUserMenuHit()메소드
	public List<UserMenuHitVO> getUserMenuHit() {
			System.out.println("[HistoryDAO]: getUserMenuHit() 메소드 진입");
		List<UserMenuHitVO> list = ss.selectList("weather.getHit"); // <------ 여기가 문제지점이다
			System.out.println("[HistoryDAO]: getUserMenuHit() 실행완료 : List<UserMenuHitVO> list 리턴됨");
		
			for(UserMenuHitVO vo : list) {
				System.out.println("u_idx: "+vo.getU_idx());
				System.out.println("m_idx: "+vo.getM_idx());
				System.out.println("hit: "+vo.getHit());
			}
			
			// HistoryDAO에서 ss.selectList("whether.getHit")으로 sql문처리 및 리턴 제대로받았는지 확인한다
		
			 //여기서도 실험 완료 => 정상적으로 값 나와서 DAO호출한곳으로 리턴값 잘 전달되어짐 ==> [결론]: mybatis부분이 문제임
			/*List<UserMenuHitVO> exportList = new ArrayList<UserMenuHitVO>();
			for(int i=1;i<=5;i++) {
				UserMenuHitVO uvo = new UserMenuHitVO();
				uvo.setU_idx(String.valueOf(i));
				uvo.setM_idx(String.valueOf(i));
				uvo.setHit(String.valueOf(i));
				exportList.add(uvo);
			}*/
			return list;
	}// end getUserMenuHit()
	
	
}// end HistoryDAO 클래스
