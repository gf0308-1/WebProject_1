package com.ict.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import my.dao.ExportCSVDAO;

@Controller
public class ExportCSVController {
	@Autowired
	private ExportCSVDAO csvDao;
	
	
	@RequestMapping(value="/export_data_as_csv", method=RequestMethod.GET)
	public ModelAndView exportDataAsCSV() {
		ModelAndView mv = new ModelAndView();
		
		boolean result = csvDao.exportCSV();
		
		mv.addObject("result", result);
		mv.setViewName("test/testPage");
		
		return mv;
	}
	
}
