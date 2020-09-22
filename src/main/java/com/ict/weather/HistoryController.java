package com.ict.weather;

import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import my.dao.HistoryDAO;
import my.dao.MenuDAO;
import my.dao.UserDAO;
import my.util.MahoutUtil;
import my.vo.MenuVO;

@Controller
public class HistoryController {

	@Autowired
	private MenuDAO menuDao;
	@Autowired
	private	UserDAO userDao;
	//@Autowired
	//private HistoryDAO historyDao;
	
	@RequestMapping(value = "/mahout", method = RequestMethod.POST)
	public ModelAndView mahoutRecommend(String kname) throws TasteException, IOException { // 디비에서 u_idx를가져오기위해 그에해당하는 user_name(kname)을 받았다
		System.out.println("mahoutRecommend() 들어옴: ");
		System.out.println("mahoutRecommend() 내에서의 kname: "+kname);
		
		// kname을 통해 u_idx를 찾는 DAO메소드
		int u_idx = userDao.findUserIdx(kname); // => u_idx 를 얻어냈다
		System.out.println("[HistoryController] u_idx: "+u_idx);
		
		//머하웃 추천 아이템 얻어내기
		//int item = MahoutUtil.getItems(u_idx);
		List<Integer> items = MahoutUtil.getItems(u_idx); // 특정사용자에게추천할만한 메뉴아이템들을 얻어옴
			System.out.println("items: "+items); // 끝.
			
		List<MenuVO> menuList = menuDao.getMahoutMenuList(items);
			System.out.println("menuList: "+menuList);
//		MenuVO vo = menuDao.getMenuOne(items);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("menuList",menuList); // menuList 리스트 째로 mahoutEx로 보내준다음, 거기서 반복문으로 이미지+메뉴명 뿌려준다
		mv.setViewName("mahoutEx");
		/*mv.addObject("menu_img",vo.getMenu_img());
		mv.addObject("menu_name",vo.getMenu_name());*/
		return mv;
	}
	
}
