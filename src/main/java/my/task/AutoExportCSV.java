package my.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import my.dao.ExportCSVDAO;

@Component
public class AutoExportCSV { // Quartz 라이브러리를 적용해 @Scheduled로 주기적으로 어떤처리가 되어지는 클래스 AutoExportCSV
	@Autowired
	private ExportCSVDAO csvDao;

	@Scheduled(cron="0 0 0 * * *") // 매 일 '0시 0분 0초' 마다 주기적으로 아래 메소드를 반복실행 한다(서버가 구동되고 있는[=>프로젝트가 구동되고 있는] 동안은!)
	public void periodicExport() { // periodicExport
		boolean exportResult = csvDao.exportCSV();
		System.out.println("user_menu_hit 데이터 export 결과: "+exportResult);
	}

}
