package com.dahee.board.service;

import com.dahee.board.dao.BoardDAO;
import com.dahee.main.ServiceInterface;

public class BoardViewServiceImpl implements ServiceInterface {
	
	private BoardDAO dao;

	@Override
	public void setDao(Object obj) {
		this.dao = (BoardDAO)obj;
	}

	@Override
	// 넘어오는 obj는 배열
	// obj = Object[0]-no, Object[1]-increase
	public Object service(Object obj) throws Exception {
		Object[] objs = (Object[])obj;
		long no = (Long)objs[0];
		int inc = (int)objs[1];
		System.out.println("BoardViewServiceImpl.service().no/inc : " + no + "/" + inc);
		// inc = 1이면 조회수 1증가
		if(inc == 1) {
			dao.increase(no);
		}
		return dao.view(no);
	}
}