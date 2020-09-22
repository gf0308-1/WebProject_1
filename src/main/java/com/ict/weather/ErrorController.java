package com.ict.weather;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	
	@RequestMapping(value="/errorPage", method=RequestMethod.GET) // redirect로 데이터를 전송받을수있는 방식은 GET방식일 때 뿐이다.(POST일땐 안됨)
	public ModelAndView errorPage(String message) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("message", message);
		mv.setViewName("errorPage");
		
		return mv;
	}

}
