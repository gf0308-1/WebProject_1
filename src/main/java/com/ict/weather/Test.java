package com.ict.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

class Test {

	public static void main(String[] args) {

		/*
		 * 
5~19도 : 봄 || 3/20~6/20
20~ : 여름 || 6/21~9/21
5~19도 : 가을 || 9/22~12/20
 ~4도 : 겨울 ||12/21~3/19

		 * */
		Scanner sc = new Scanner(System.in);
		ArrayList<String> list = new ArrayList<String>();
		
		 //봄 음식 
		   String a[] = { "화전", "쑥떡", "두견 주" }; 
		   // 여름 
		   String  b[] = { "삼계탕","밀 쌈", "수단" }; 
		   // 가을 
		   String c[] = { "국화전", "송편", "화채" }; 
		   // 겨울 
		   String d[] = {"떡국", "부럼", "수정과" };
		  
		
		
		//봄으로 테스트
		String startDt = "20200320";
		int endDt = 20200620;

		int startYear = Integer.parseInt(startDt.substring(0, 4));
		int startMonth = Integer.parseInt(startDt.substring(4, 6));
		int startDate = Integer.parseInt(startDt.substring(6, 8));

		Calendar cal = Calendar.getInstance();
		
		// Calendar의 Month는 0부터 시작하므로 -1 해준다.
		// Calendar의 기본 날짜를 startDt로 셋팅해준다.
		cal.set(startYear, startMonth - 1, startDate-1);
		
		while (true) {
			
			// 날짜 출력
			//System.out.println(getDateByString(cal.getTime()));

			// Calendar의 날짜를 하루씩 증가한다.
			cal.add(Calendar.DATE, 1); // one day increment
			list.add(getDateByString(cal.getTime()));
			// 현재 날짜가 종료일자보다 크면 종료
			if (getDateByInteger(cal.getTime()) > endDt)
				break;
		}
		
		//리스트값
		System.out.println(list);
		
		//리스트안의 값들을 비교해서 맞다면 true아니면 false를 리턴해주는 메소드
		
		if(list.contains(sc.next())) {
			//봄배열 출력문
			System.out.println(Arrays.toString(a));
		}else {
			System.out.println("봄아닙니다.");
		}
	}

	public static int getDateByInteger(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(date));
	}

	public static String getDateByString(Date date) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        return sdf.format(date);
	    } 
	 
}