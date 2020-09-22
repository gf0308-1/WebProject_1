package com.ict.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.tomcat.jdbc.pool.DataSource;

public class Test2 {

	public static void main(String[] args) throws IOException, TasteException {
		// 여기서 쓰인 File객체는 java.io의 File클래스 객체이다
		DataModel model = new FileDataModel(new File("C:\\csv\\2020.09.15\\[2020.09.15]user_menu_hit.csv"));
		//유사도 모델 생성
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		//모든 유저들로부터 주어진 유저와 특정 임계값을 충족하거나 초과하는 neighborhood기준
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);

		//사용자 추천기 생성
		UserBasedRecommender recommender = new GenericUserBasedRecommender(
				model, neighborhood, similarity);
			
			/*현재 유저 ID에 해당되는 4개 아이템 추천*/
			List<RecommendedItem> recommendations = recommender.recommend(1, 2);
			for (RecommendedItem recommendation : recommendations) {
	
				System.out.println(2+","+recommendation.getItemID());
	
			}
	}

}// end Main 메소드

/*
 * public static DataModel loadFromDB() throws IOException {
 * 
 * DataSource dbsource = new DataSource(); // 내 oracle DB명: XE
 * dbsource.setUsername("ict03"); dbsource.setPassword("ict03");
 * dbsource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
 * dbsource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
 * 
 * DataModel dataModelDB = new MySQLJDBCDataModel(dbsource, "tbl_user_menu_hit",
 * "u_idx", "m_idx", "hit", "reg_date");
 * 
 * return dataModelDB; }// end loadFromDB()
 */
