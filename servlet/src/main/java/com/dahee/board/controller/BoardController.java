package com.dahee.board.controller;

import javax.servlet.http.HttpServletRequest;

import com.dahee.board.vo.BoardVO;
import com.dahee.main.Execute;
import com.dahee.main.ServiceInterface;

public class BoardController  {

	// private 변수로 BoardListService, view, write, update, delete 등등..
	private ServiceInterface boardListService;
	private ServiceInterface boardViewService;
	private ServiceInterface boardWriteService;
	private ServiceInterface boardUpdateService;
	private ServiceInterface boardDeleteService;
	
	// setter를 만들어서 생성된 service를 받는다.
	public void setBoardListService(ServiceInterface boardListService) {
		this.boardListService = boardListService;
	}
	public void setBoardViewService(ServiceInterface boardViewService) {
		this.boardViewService = boardViewService;
	}
	public void setBoardWriteService(ServiceInterface boardWriteService) {
		this.boardWriteService = boardWriteService;
	}
	public void setBoardUpdateService(ServiceInterface boardUpdateService) {
		this.boardUpdateService = boardUpdateService;
	}
	public void setBoardDeleteService(ServiceInterface boardDeleteService) {
		this.boardDeleteService = boardDeleteService;
	}
	
	// 실행 메서드 -> return되는 String 데이터가 jsp 또는 url 정보가 된다.
	public String execute(HttpServletRequest request) throws Exception{
		System.out.println("BoardController.execute().request : " + request);
		// 어떤 요청을 하는지 알아봐야 함
		// writeForm과 write에서는 같은 URL를 사용. get방식으로 들어오면 Form이고 post 방식이면 write 처리.
		String uri = request.getRequestURI(); // writeForm
		String method = request.getMethod(); // write
		System.out.println("BoardController.execute().method : " + method); // get, post 방식인지 확인
		String jsp = null;
		// 메뉴에 따른 처리
		switch(uri) {
		// 글목록
		case"/board/list.do": // blank나 .이나 /같은 특수문자 주의.
			// 리스트라고 생각을 하고 처리 하기. - 마지막에 request에 담는다.
			// 처리한 Execute.run을 list라는 이름으로 request에 담는다.
			request.setAttribute("list", Execute.run(boardListService, null));
			jsp = "board/list"; 
			break;
		// 글보기
		case"/board/view.do":
			// 데이터 수집 -> 반드시 list.do부터 시작해야 데이터가 넘어온다.
			long no = Long.parseLong(request.getParameter("no"));
			int inc = Integer.parseInt(request.getParameter("inc"));
			// 데이터 처리 -> request에 담는다. -> jsp에서 EL 객체를 사용해서 꺼내쓴다.
			BoardVO viewVO = (BoardVO) Execute.run(boardViewService, new Object[] {no, inc});
			// 내용 줄바꿈 처리 - \n를 <br/> 태그로 처리
			viewVO.setContent(viewVO.getContent().replace("\n", "<br/>"));
			request.setAttribute("vo", viewVO);
			jsp = "board/view";
			break;
		// 글등록
		case"/board/write.do":
			// get 방식일 때
			if(request.getMethod().equals("GET")) {
				jsp = "board/write"; 
			// post 방식일 때
			} else {
				// 데이터 수집
				BoardVO writeVO = new BoardVO();
				writeVO.setTitle(request.getParameter("title"));
				writeVO.setContent(request.getParameter("content"));
				writeVO.setWriter(request.getParameter("writer"));
				writeVO.setPw(request.getParameter("pw"));
				// 글등록 처리
				Execute.run(boardWriteService, writeVO);
				jsp = "redirect:list.do";
			}
			break;
		// 글수정
		case"/board/update.do":
			// get 방식일 때
			if(request.getMethod().equals("GET")) {
				long updateNo = Long.parseLong(request.getParameter("no"));
				int updateInc = Integer.parseInt(request.getParameter("inc"));
				BoardVO updateVO = (BoardVO) Execute.run(boardViewService, new Object[] {updateNo,updateInc});
				updateVO.setContent(updateVO.getContent().replace("\n", "<br/>"));
				request.setAttribute("vo", updateVO);
				jsp = "board/update"; 
			// post 방식일 때
			} else {
				BoardVO updateVO = new BoardVO();
				long updateNo = Long.parseLong(request.getParameter("no"));
				updateVO.setNo(updateNo);
				updateVO.setTitle(request.getParameter("title"));
				updateVO.setContent(request.getParameter("content"));
				updateVO.setWriter(request.getParameter("writer"));
				updateVO.setPw(request.getParameter("pw"));
				// DB 처리
				Execute.run(boardUpdateService, updateVO);
				jsp = "redirect:view.do?no="+updateVO.getNo()+"&inc=0";
			}
			break;
		// 글삭제
		case"/board/delete.do":
			// 데이터 수집 - 글번호, 비밀번호
			BoardVO deleteVO = new BoardVO();
			long deleteNo = Long.parseLong(request.getParameter("no"));
			deleteVO.setNo(deleteNo);
			deleteVO.setPw(request.getParameter("pw"));
			Execute.run(boardDeleteService, deleteVO);
			jsp = "redirect:list.do";
			break;
		default:
			throw new Exception("잘못된 페이지를 요청");
		}
		return jsp;
	} // end of execute()
} // end of class