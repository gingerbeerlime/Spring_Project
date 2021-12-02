package com.bbs.dao;

import java.util.List;

import com.bbs.vo.Boarder;
import com.bbs.vo.UploadFile;

public interface BbsDAO {
	
	// insert문이지만 selectkey에서 boarder빈으로 저장되는 값이 있어 boarder로 반환
	public Boarder write(Boarder boarder) throws Exception;
	public void fileUpload(UploadFile uploadFile) throws Exception;
	public Boarder getBoarder(Integer boarder_id) throws Exception;
	public UploadFile getUploadFile(Integer boarder_id) throws Exception;
	public UploadFile getUploadFile(String file_realName) throws Exception;
	public void updateBoarder(Boarder boarder) throws Exception;
	public void updateFile(UploadFile uploadFile) throws Exception;
	// bbsMapper에 보면 전달해줘야할 파라미터값이 없음
	public int getMaxBoarder_id() throws Exception;
	public List<Boarder> getBbsList(int boarder_id) throws Exception;
	public void deleteBoarder(int boarder_id) throws Exception;
	
}
 