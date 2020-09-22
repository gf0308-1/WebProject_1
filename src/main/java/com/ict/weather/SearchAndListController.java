package com.ict.weather;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchAndListController {
	@RequestMapping(value="test_searchAndList", method= RequestMethod.POST)
	public ModelAndView searchAndList(String menu, String address, String latitude, String longitude, String u_id) { 
		// 넘어온 form 안에 담겨있는 매개변수값이, 여기서 정의해놓은 파라미터값과 같은 타입이라면, 여기에 잘 담길것이다.		
		ModelAndView mv = new ModelAndView();
		mv.addObject("addressAndMenu", address+" "+menu);
		mv.addObject("latitude", latitude);
		mv.addObject("longitude", longitude);
		mv.addObject("u_id", u_id);
		
		mv.addObject("menu",menu);
		System.out.println(menu);
		System.out.println("[coordinate]: ("+latitude+","+longitude+")" );
		System.out.println("[u_id]: " + u_id);
		
		mv.setViewName("test_searchAndList");
		return mv;
	}
	
	
}
