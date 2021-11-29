package com.bbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bbs.dao.BbsDAO;

@Service
public class BbsServiceImpl implements BbsService {
	
	@Inject
	BbsDAO dao;
}
