package com.ict.test;

import java.util.Calendar;
import java.util.Timer;

public class Scheduler {

	public static void main(String[] args) {
		// 때가 되면 실행할 내용 객체 생성
		//PeriodicSearch ps = new PeriodicSearch();
		GenerateCSV gcsv = new GenerateCSV();
		// 타이머 객체 생성
		Timer timer = new Timer();
		// 시작 날짜 객체 생성
		Calendar date = Calendar.getInstance();
		// 시작날짜의 상세내용 설정
		date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.HOUR, 4);
		date.set(Calendar.MINUTE, 4);
		date.set(Calendar.SECOND, 50);
		date.set(Calendar.MILLISECOND, 0);
		 
		timer.scheduleAtFixedRate(gcsv, date.getTime(), 1000 * 10); // 시스템은 밀리초단위로계산하므로, 1초부터생각하고자한다면 무조건 1000부터 여겨야한다.
	}
}