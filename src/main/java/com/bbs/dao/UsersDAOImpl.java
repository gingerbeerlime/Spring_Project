package com.bbs.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

//저장소 역할
@Repository
public class UsersDAOImpl implements UsersDAO {
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int idCheck(String user_id) throws Exception {
		
		// select문에서 받아오는 데이터가 1개이기 때문에 selectOne()
		return sqlSession.selectOne("com.bbs.mappers.bbs.idCheck", user_id);
	}



}
