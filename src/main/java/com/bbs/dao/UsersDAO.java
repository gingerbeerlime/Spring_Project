package com.bbs.dao;

import com.bbs.vo.Authmail;
import com.bbs.vo.Users;

//인터페이스
public interface UsersDAO {

	// UPDATE문 반환값 있음
	public String idCheck(String user_id) throws Exception;
	public Integer getAuthnum(String user_mail) throws Exception;
	// INSERT문 반환타입 없음
	public void setAuthnum(Authmail authmail) throws Exception;
	// UPDATE문 반환타입 없음
	public void resetAuthnum(Authmail authmail) throws Exception;
	// DELETE문 반환타입 없음(매개변수는 mapper에 들어오는 인수와 이름이 같아야함)
	public void deleteAuthmail(String user_mail) throws Exception;
	// INSERT문 반환타입 없음
	public void join(Users users) throws Exception;
}
