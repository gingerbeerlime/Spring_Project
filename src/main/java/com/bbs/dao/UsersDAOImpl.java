package com.bbs.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bbs.vo.Authmail;
import com.bbs.vo.Users;

// UsersDAO 인터페이스를 구현하는 곳
// 저장소 역할
@Repository
public class UsersDAOImpl implements UsersDAO {
	
	// @Inject 인터페이스를 먹는다...
	@Inject
	SqlSession sqlSession;
	
	final String SESSION = "com.bbs.mappers.bbs";
	
	@Override
	public String idCheck(String user_id) throws Exception {
		
		// select문에서 받아오는 데이터가 1개이기 때문에 selectOne()
		return sqlSession.selectOne(SESSION + ".idCheck", user_id);
	}
	
	@Override
	public Integer getAuthnum(String user_mail) throws Exception {

		return sqlSession.selectOne(SESSION + ".getAuthnum", user_mail);
	}

	@Override
	public void setAuthnum(Authmail authmail) throws Exception {
		
		sqlSession.insert(SESSION + ".setAuthnum", authmail);
	}


	@Override
	public void resetAuthnum(Authmail authmail) throws Exception {

		sqlSession.update(SESSION + ".resetAuthnum", authmail);
	}

	@Override
	public void deleteAuthmail(String user_mail) throws Exception {
		// session을 받아오는건 로그인 한다는 뜻
		sqlSession.delete(SESSION + ".deleteAuthmail", user_mail);
		
	}

	@Override
	public void join(Users users) throws Exception {
		// .join은 mapper의 id값
		sqlSession.insert(SESSION + ".join", users);
		
	}



}
