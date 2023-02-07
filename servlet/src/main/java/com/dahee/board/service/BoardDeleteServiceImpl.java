package com.dahee.board.service;

import com.dahee.board.dao.BoardDAO;
import com.dahee.board.vo.BoardVO;
import com.dahee.main.ServiceInterface;

public class BoardDeleteServiceImpl implements ServiceInterface {
	
	private BoardDAO dao;

	@Override
	public void setDao(Object obj) {
		this.dao = (BoardDAO)obj;

	}

	@Override
	public Object service(Object obj) throws Exception {
		System.out.println("BoardDeleteServiceImpl.service().vo : " + obj);
		return dao.delete((BoardVO) obj);
	}
}
