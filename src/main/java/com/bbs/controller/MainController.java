package com.bbs.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bbs.service.BbsService;
import com.bbs.service.UsersService;
import com.bbs.vo.Authmail;
import com.bbs.vo.Users;


@Controller
public class MainController {	
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	// @Inject : 객체를 자동으로 만들어줌
	@Inject
	UsersService usersService;
	@Inject
	BbsService bbsService;
	
	// url패턴이 'path/'일 경우
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model) throws Exception {
		
		model.addAttribute("msg", "반갑습니다.");
		
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/main";
		
	}
	
	// url패턴이 'path/join'일 경우
	// value = url 경로
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	//아래 join()은 함수 이름
	public String join(Model model) throws Exception {
		
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/join";
		
	}
	
	// url패턴이 'path/login'일 경우
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) throws Exception {
		// view안의 파일경로를 적으면 됨 main폴더를 따로 만들고 그 안에 main.jsp를 넣어두면 return "main/main";이라고 설정
		return "main/login";
		
	}
	
	//url패턴이 'path/bbs'일 경우
	@RequestMapping(value = "/bbs", method = RequestMethod.GET)
	public String bbs(Integer pageNumber, Model model) throws Exception {
		
		if(pageNumber == null) pageNumber = 1;
		
		model.addAttribute("map", bbsService.bbs(pageNumber));
		
		return "bbs/bbs";
		
	}
	
	// url패턴이 'path/joinAction'일 경우
		@RequestMapping(value = "/joinAction", method = RequestMethod.POST)
		// join.jsp 에서 action="./joinAction" url을 받아와서 
		// controller에서 받아온 값을 아래 Users빈에 같은 이름을 매칭시켜 저장시킴
		public String joinAction(Users users, String addr1, String addr2, String addr3)throws Exception {
			
			users.setUser_addr(addr1 + " " + addr2 + " " + addr3);
			usersService.joinAction(users);
			
			// return main/login으로 하면 url은 여전히 joinAction이기 때문에 url자체를 바꿔줘야함
			// redirect:/ 는 http://localhost:8081/과 같다
			// redirect:/만 하면 메인화면으로 이동
			return "redirect:/login";
		}
		
		// url패턴이 'path/loginAction'일 경우
		@RequestMapping(value = "/loginAction", method = RequestMethod.POST)
		public String loginAction(Users users, HttpSession session, RedirectAttributes ra)throws Exception {
			
			int result = usersService.loginAction(users);
			String url = null;
			
			if(result == 0) {
				session.setAttribute("user_id", users.getUser_id());
				url = "redirect:/";
			} else {
				// redirect 쓸 때는 model, request 사용할 수 없음
				// model.addAttribute("msg", "로그인정보가 일치하지 않습니다.");
				ra.addFlashAttribute("msg", "로그인 정보가 일치하지 않습니다.");
				
				url = "redirect:/login";
			}
					
			return url;
		}
		// url패턴이 'path/logout'일 경우
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String logout(HttpSession session) throws Exception {
			
			session.invalidate();
			
			return "redirect:/";
		}
	
	// url패턴이 'idCheck/'일 경우
	// requestmapping : url 검색할 때 사용
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	// 반환값을 페이지에 직접 출력
	// ajax 비동기 통신일 때는 페이지에 반환값을 출력한뒤 그 값을 받아오기 때문에 body에 출력된 값을 가져온다는 의미로 responsebody를 써줘야함
	@ResponseBody
	public String idCheck(String user_id) throws Exception {
		
		int result = usersService.idCheck(user_id);
		
		// result를 문자열로 바꾸기
		// 경로를 반환해줘야함
		return result + "";
		
		
	}
	
	// url패턴이 'path/sendAuthMail'일 경우
	@RequestMapping(value = "/sendAuthMail", method = RequestMethod.GET)
	// @ResponseBody를 넣지 않으면 return값이 view파일경로로 온다.
	@ResponseBody
	public String sendAuthMail(String user_mail) throws Exception {
		
		int result = usersService.setAuthnum(user_mail);
		
		return result + "";
	}
	
	// url패턴이 'path/mailAuth'일 경우 (join.js에서 url 패턴확인)
	// value에는 http://localhost:8081 에 붙는 url 적어줌
	// join.js를 처리해준다고 생각하면 됨
	@RequestMapping(value = "/mailAuth", method = RequestMethod.POST)
	// 안붙이면 리턴값이 view의 파일경로로 옴
	@ResponseBody	
	// mailAuth(String user_mail, String authmail)도 가능하지만 
	// 두개를 같이 Authmail.java에서 객체로 다루고 있기 때문에 아래로도 사용 가능
	public String mailAuth(Authmail authmail)throws Exception {
		
		return usersService.checkAuthnum(authmail) + "";
		
	}
	
	
	

	
}
