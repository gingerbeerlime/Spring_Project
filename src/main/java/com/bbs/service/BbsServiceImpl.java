package com.bbs.service;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bbs.dao.BbsDAO;
import com.bbs.vo.Boarder;
import com.bbs.vo.UploadFile;

@Service
public class BbsServiceImpl implements BbsService {
	
	@Inject
	BbsDAO dao;
	
	static final String PATH = "F:\\eclipse\\workspace\\Spring_Project\\src\\main\\webapp\\resources\\upload\\";
	
	@Override
	public void writeAction(Boarder boarder, MultipartFile file) throws Exception {
	
		// 1) 게시글 작성 기능
		boarder = dao.write(boarder);
		// 반환값(AUTO_INCREMENT한 BOARDER_ID값)이 있기 때문에 boarder를 바꿔줌(REUPDATE느낌)
		// 아래에서 이 BOARDER_ID값을 다시 사용해야하기 때문에 BOARDER를 다시 바꿔주는 것
		
		// 2) 파일 업로드 기능
		// 파일 객체가 비었을 때 (파일 입력하지 않았을 때)
		if(file.isEmpty()) return;

		// 작성자가 올린 파일의 원본 이름
		String file_name =  file.getOriginalFilename();
		// 파일의 확장자 구함
		String suffix = FilenameUtils.getExtension(file_name);
		// 랜덤한 중복되지 않은 id값을 받아옴
		UUID uuid = UUID.randomUUID();
		// 파일이 저장될 때 이름
		String file_realName = uuid + "." + suffix;
		// 파일 업로드
		file.transferTo(new File(PATH + file_realName));
	
		UploadFile uploadFile = new UploadFile();
		uploadFile.setBoarder_id(boarder.getBoarder_id());
		uploadFile.setFile_name(file_name);
		uploadFile.setFile_realName(file_realName);
		
		dao.fileUpload(uploadFile);
		
		
	}

	@Override
	public HashMap<String, Object> view(Integer boarder_id) throws Exception {

		Boarder boarder = dao.getBoarder(boarder_id);
		UploadFile uploadFile = dao.getUploadFile(boarder_id);
		
		// Map<String, Object> map = new HashMap<String, Object>();
		// 위도 가능 HashMap은 Map의 자손이기 때문에
		HashMap<String, Object> map = new HashMap<String, Object>();
		// <key, value>
		// key : 스트링값, value : 객체타입(boarder랑 uploadFile의 부모 데이터타입)
		map.put("boarder", boarder);
		map.put("uploadFile", uploadFile);
		
		return map;
	}

	
	
	
}
