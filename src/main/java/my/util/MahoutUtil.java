package my.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutUtil { // 머하웃 사용자기반추천 버튼 클릭했을 시 추천을 만들어 제공해주는 클래스

	public static List<Integer> getItems(int u_idx) throws TasteException, IOException {
		System.out.println("[MahoutUtil] getItems() 진입");
		// 임시 int 변수 m
		int m = 0;
		
		
		Date date = Calendar.getInstance().getTime(); // 현재 날짜 데이터
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
		String dateDir = dateFormat.format(date);
		// 오늘날짜를 기준으로 사용하여 저장경로와 파일명 만들어내기
		String savePath = "C:/csv/" + dateDir;
		String fileName = "[" + dateDir + "]user_menu_hit";
		// 분석에사용할 데이터모델을 csv파일에서 얻기
		DataModel model = new FileDataModel(new File(savePath+"/"+fileName+".csv")); // 날짜별로 그날 그날 당일의 데이터(최신데이터이므로) csv를 참고해 분석하도록 자동화
		//DataModel model = new FileDataModel(new File("C:\\csv\\sample\\ratings_edit.csv")); // 날짜별로 그날 그날 당일의 데이터(최신데이터이므로) csv를 참고해 분석하도록 자동화
		//DataModel model = new FileDataModel(new File("C:\\csv\\"+dateDir+"\\[2020.09.15]user_menu_hit.csv"));
		// 유사도 모델 생성
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		// 모든 유저들로부터 주어진 유저와 특정 임계값을 충족하거나 초과하는 neighborhood기준 => Threshold 클래스 방식의 구현 방법
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		// 모든데이터를 다차원으로 분석해 좌표상에 각 노드를 배치했을 때, 기준점을 기준으로 최근접이웃값들을 찾아내서 이용하는방식의 neighborhood 구하기방법 => NearestNUserNeighborhood 클래스 방식
		//UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model); // 현재데이터수가많지않으므로, 총10명의 유저중 5명까지는 근사이웃으로 설정한다
		// 사용자 추천기 생성
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		// 현재 이 요청을한 유저의 u_idx값
		
		
		/* 현재 유저 ID에 해당되는 4개 아이템 추천 */ // u_idx
		List<RecommendedItem> recommendations = recommender.recommend(5, 6); // '유저아이디(u_idx) 1번 유저'에게 추천할아이템 6개를 추천한다
		// 추천항목 아이템 가변배열
		List<Integer> intList = new ArrayList<Integer>();
		
		System.out.println("[MahoutUtil] List<RecommendedItem> recommendations 얻어냄");
		if(recommendations != null && recommendations.size() != 0) { // 추천항목이 있을경우
			for(int i=0; i<recommendations.size(); i++) {
				// 추천항목 i
				m = (int) recommendations.get(i).getItemID();
				System.out.println("[최종확인] 유저"+5+"에게로 추천항목"+(i+1)+": 메뉴" + m);
				
				intList.add(m); // 가변배열 intList에 추가해넣어준다
				System.out.println("intList.add(m) 수행완료");
			} // intList 완성
		} 
		System.out.println("getItems 메소드 정상종료");
		return intList;
	}
}
