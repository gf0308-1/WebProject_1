package my.vo;

public class MenuVO { // 메뉴테이블에 대한 VO 클래스
	// 테이블에서 가져올 칼럼값들을 담을 중간저장소 멤버필드
	String idx,
		   menu_name,
		   season,
		   menu_img;
	
	// 게터세터 메소드
	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getMenu_img() {
		return menu_img;
	}

	public void setMenu_img(String menu_img) {
		this.menu_img = menu_img;
	}
	
	

}
