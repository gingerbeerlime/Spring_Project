package com.bbs.controller;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bbs.service.BbsService;
import com.bbs.vo.Boarder;
import com.bbs.vo.UploadFile;

@Controller
@RequestMapping(value = "/bbs/*")
public class BbsController {
	
	@Inject
	BbsService bbsService;

	// url패턴이 'path/bbs/'일 경우
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String bbs(Model model) throws Exception {
		return "redirect:/bbs";
	}
	
	// url패턴이 'path/bbs/write'일 경우
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(RedirectAttributes ra, HttpSession session) throws Exception {
		
		// 로그인이 안되어있으면 로그인 페이지로 이동시키고 로그인이 필요하다 알려줌
		if(session.getAttribute("user_id") == null) {
			ra.addFlashAttribute("msg", "로그인이 필요합니다.");
			return "redirect:/login";
		}
		// 로그인 되어있으면 글쓰기 페이지 출력
		return "bbs/write";
	}
	
	// url패턴이 'path/bbs/view'일 경우
	// int와 같은 기본데이터타입은 null값을 가질 수 없고 Integer와 같은 객체는 null값을 받을 수 있음
	// -> 500 내부 서버 오류 방지
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Integer boarder_id, Model model, RedirectAttributes ra) throws Exception {
		
		HashMap<String, Object> map = bbsService.view(boarder_id);
		
		// map에 있는 boarder 빈을 가져와서 검증
		if(map.get("boarder") == null) {
			// 존재하지 않는 게시물입니다. msg 보내고 
			ra.addFlashAttribute("msg", "존재하지 않는 게시물입니다.");
			// bbs로 돌려보냄
			return "redirect:/bbs";
		}
		
		// boarder라는 이름으로 boarder 객체 전달
		model.addAttribute("map", map);
		
		return "bbs/view";
	}
	
	// url패턴이 'path/bbs/update'일 경우
		@RequestMapping(value = "/update", method = RequestMethod.GET)
		public String update(Integer boarder_id, Model model, HttpSession session, RedirectAttributes ra) throws Exception {
			
			// 세션 받아오기
			String user_id = (String) session.getAttribute("user_id");
			
			HashMap<String, Object> map = bbsService.view(boarder_id);
			// map.get("boarder")은 object타입이라서 형변환 boarder가 자손 데이터타입이라 더 큰 값을 넣어줄 수 없음
			Boarder boarder = (Boarder)map.get("boarder");
			
			if(user_id == null) {
				ra.addFlashAttribute("msg", "로그인이 필요합니다.");
				return "redirect:/login";
			}
			
			// map에 있는 boarder 빈을 가져와서 검증
			if(boarder == null) {
				ra.addFlashAttribute("msg", "존재하지 않는 게시물입니다.");
				return "redirect:/bbs";
			}
			
			if(!user_id.equals(boarder.getWriter())) {
				ra.addFlashAttribute("msg", "권한이 없습니다.");
				return "redirect:/bbs";
			}
			
			
			
			// boarder라는 이름으로 boarder 객체 전달
			model.addAttribute("map", map);
			
			return "bbs/update";
		}
	
	// url패턴이 'path/bbs/writeAction'일 경우
	@RequestMapping(value = "/writeAction", method = RequestMethod.POST)
	public String writeAction(Boarder boarder, MultipartFile file , HttpSession session, RedirectAttributes ra) throws Exception {
		
		// 세션 검증
		String user_id = (String) session.getAttribute("user_id");
	
		
		if(user_id == null) {
			// 로그인이 필요합니다. msg전송, redirect쓸 때는 model 쓸수 없다
			ra.addFlashAttribute("msg", "로그인이 필요합니다.");
			return "redirect:/login";
		}
		
		// 유저 아이디를 작성자로 저장
		boarder.setWriter(user_id);
		// 게시글 작성 기능 실행
		bbsService.writeAction(boarder, file);
		
		return "redirect:/bbs";
	}
	

	// url패턴이 'path/bbs/downloadAction'일 경우
	@RequestMapping(value="/downloadAction", method = RequestMethod.GET)
	public String downloadAction(UploadFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		bbsService.downloadAction(request, response, uploadFile);
		
		return "redirect:/bbs/view?boarder_id=" + uploadFile.getBoarder_id();
		
	}
	
	// url패턴이 'path/bbs/updateAction'일 경우
	@RequestMapping(value="/updateAction", method = RequestMethod.POST)
	public String updateAction(Boarder boarder, MultipartFile file) throws Exception {
		
		bbsService.updateAction(boarder, file);
		
		return "redirect:/bbs/view?boarder_id=" + boarder.getBoarder_id();
	}
	
	// url패턴이 'path/bbs/deleteAction'일 경우
	@RequestMapping(value = "/deleteAction", method = RequestMethod.GET)
	public String deleteAction(int boarder_id) throws Exception {
		
		bbsService.deleteAction(boarder_id);
		
		return "redirect:/bbs";
		
	}
	
	
}
