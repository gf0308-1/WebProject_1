package my.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import my.vo.UserMenuHitVO;

// 우리 서비스의 DB내의 'user_menu_hit' 데이터가 csv로 자동으로 엑스포트 되는 클래스
public class ExportCSVDAO {

	@Autowired
	private HistoryDAO hDao;

	// 메인메소드
	public boolean exportCSV() {
		// 엑스포트 할 데이터 리스트를 DAO메소드로 가져오기
		List<UserMenuHitVO> exportList = hDao.getUserMenuHit(); // ---- 여기서 (잘와야되는데) 왜 자꾸 NullPointerException이 뜨지??
		// savePath 정하기
		// dateDir값을 현재날짜를 얻어서 값으로 삼아주기
		// SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");
		Date date = Calendar.getInstance().getTime(); // 현재 날짜 데이터
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
		String dateDir = dateFormat.format(date);

		// String dateDir = "2020.09.14_1";
		String savePath = "C:/csv/" + dateDir;
		String fileName = "[" + dateDir + "]user_menu_hit";
		// csv로 내보내기
		boolean result = exportUserMenuHit(exportList, savePath, fileName);

		return result;
		/*
		 * 위에꺼가 잘 안되서, 직접 List를 만들어주고 csv로 잘 export되나 테스트하기위해 만든 것=> 잘 실행됨!
		 * List<UserMenuHitVO> exportList = new ArrayList<UserMenuHitVO>(); for(int
		 * i=1;i<=5;i++) { UserMenuHitVO uvo = new UserMenuHitVO();
		 * uvo.setU_idx(String.valueOf(i)); uvo.setM_idx(String.valueOf(i));
		 * uvo.setHit(String.valueOf(i)); exportList.add(uvo); } // exportList 제대로 왔나
		 * 테스트 해보기 for(UserMenuHitVO vo : exportList) {
		 * System.out.print("u_idx: "+vo.getU_idx()+"  ");
		 * System.out.print("m_idx: "+vo.getM_idx()+"  ");
		 * System.out.print("hit: "+vo.getHit()); System.out.println(); }
		 */

	}// end exportCSV()

	// 해당데이터의 csv엑스포트를 수행하는 메소드 // -- 이 부분 (static인메소드가 2개이면 거슬리므로, 이 부분 위의 메소드안에
	// 포함시켜줄지, 아님 다른방법으로 해볼지 결정해서 해결하기)
	public static boolean exportUserMenuHit(List<UserMenuHitVO> exportList, String savePath, String fileName) {
		// 결과를 나타내는 result 변수
		boolean result = true;

		BufferedWriter bw;
		try {
			File dir = new File(savePath); // savePath주소를 갖는 File객체를 생성한다
			if (!dir.isDirectory()) { // 해당주소에 디렉토리경로가 없을 경우 // File객체.isDirectory() : 파일객체가갖고있는주소가 실제로 디렉토리인지, 즉 해당
										// 주소로 된 디렉토리경로가 지금 존재하고있는지 확인하는 boolean 메소드
				dir.mkdirs(); // 해당주소로 디렉토리를 생성한다
			}

			bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(savePath + "/" + fileName + ".csv"), "UTF-8"));

			for (UserMenuHitVO vo : exportList) { // VO객체에 담아서 오는게아니라 Map에 임의로 담겨 오더라도, 칼럼명은 이미알고있으므로 순서대로 칼럼명의값을 가져와
													// String에담아주면 될듯하다
				// 고정배열(3칸짜리)을 만들기 : 아래 각 요소별 유효성검사할 때, vo객체형보다 반복문돌리기쉬운 타입인 배열(기왕이면고정배열)로 만들어
				// 사용하기 위함
				String[] strArr = new String[3];

				strArr[0] = vo.getU_idx();
				strArr[1] = vo.getM_idx();
				strArr[2] = vo.getHit();

				String line = ""; // csv파일의 매 줄을 가리키는 line // 이 부분 StringBuffer 로 바꾸기
				String data = ""; // 매 줄 안에 각각 값 하나 하나인 data

				for (String st : strArr) {
					if (st != null) { // 값이 null이 아닐 때
						data = st;
						if (data.length() != 0) { // 값이 null도 아니고, 공백도 아닌, 제대로 된 값 일 때
							line = line + data + ",";
						} else { // 값이 null은 아니지만 공백일 때
							line = line + " ,"; // 공백만 넣는다
						}
					} else { // 값이 null이었을 때
						line = line + ","; // null은 공백조차 표시하지않고 바로 컴마 표시
					}
				} // end for() : csv파일로 삼을 내용 한 줄 line 완성
				line = line.substring(0, line.length() - 1); // 각 line에서 맨 마지막째자리(,)만 배제하고 line 을 잡는다
				bw.write(line);
				bw.write(System.getProperty("line.separator")); // 줄바꿈 기호를 포함시킨다
				// data.replace(",","_");
			} // end 모든 레코드 줄들을 bw에 넣기 완료

			// bw의 최종 저장 시행
			bw.flush(); // 저장 시행
			bw.close(); // 종료

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return result;

	}// end exportUserMenuHit()

}
