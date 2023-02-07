package com.dahee.board.service;

import com.dahee.board.dao.BoardDAO;
import com.dahee.main.ServiceInterface;

public class BoardListServiceImpl implements ServiceInterface {
	// dao 선언
	private BoardDAO dao;
	// setter
	@Override
	public void setDao(Object obj) {
		this.dao = (BoardDAO)obj;
	}
	
	@Override
	public Object service(Object obj) throws Exception {
		System.out.println("BoardListServiceImpl.service()");
		return dao.list();
	}
}