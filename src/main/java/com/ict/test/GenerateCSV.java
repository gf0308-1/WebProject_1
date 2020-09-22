package com.ict.test;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import my.dao.HistoryDAO;
import my.dao.MenuDAO;

@Controller
public class GenerateCSV extends TimerTask {
	
	@Autowired
	private HistoryDAO historyDao;

	
	@Override
	public void run() {
		System.out.println("타이머Task 실행!");
		//historyDao.spoolCSV();
		
	}

	
}
