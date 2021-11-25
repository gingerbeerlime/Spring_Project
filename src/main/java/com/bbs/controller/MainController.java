package com.bbs.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bbs.service.UsersService;


@Controller
public class MainController {	
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	// @Inject : 객체를 자동으로 만들어줌
	@Inject
	UsersService usersService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model) throws Exception {
		
		model.addAttribute("msg", "반갑습니다.");
		
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/main";
		
	}
	// value = url 경로
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	//아래 join()은 함수 이름
	public String join(Model model) throws Exception {
		
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/join";
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) throws Exception {
		
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/login";
		
	}
	// requestmapping : url 검색할 때 사용
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	public String idCheck(String user_id) throws Exception {
		
		int result = usersService.idCheck(user_id);
		
		return null;
		
	}
	
}
