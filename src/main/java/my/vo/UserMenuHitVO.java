package my.vo;

// UserMenuHit 조회결과를 담기위한 VO 객체 클래스
public class UserMenuHitVO {
	// 멤버필드
	private String u_idx,
				   m_idx,
				   hit;

	// 게터 세터
	public String getU_idx() {
		return u_idx;
	}

	public void setU_idx(String u_idx) {
		this.u_idx = u_idx;
	}

	public String getM_idx() {
		return m_idx;
	}

	public void setM_idx(String m_idx) {
		this.m_idx = m_idx;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}
	

	

}
