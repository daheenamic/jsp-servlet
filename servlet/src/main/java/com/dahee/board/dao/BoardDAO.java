package com.dahee.board.dao;

import java.util.List;

import com.dahee.board.vo.BoardVO;

public interface BoardDAO {

	public List<BoardVO> list() throws Exception;
	
	public BoardVO view(long no) throws Exception;
	
	public int increase(long no) throws Exception;
	
	public int write(BoardVO vo) throws Exception;
	
	public int update(BoardVO vo) throws Exception;
	
	public int delete(BoardVO vo) throws Exception;
}