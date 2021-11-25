package com.bbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bbs.dao.UsersDAO;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Inject
	UsersDAO dao;

	@Override
	public int idCheck(String user_id) throws Exception {
		
		int result = 0;
		
		if(dao.idCheck(user_id) != null) result = 1;
		
		
		
		return result;
	}
	
	
	}


