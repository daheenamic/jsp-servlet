package com.dahee.main;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dahee.board.controller.BoardController;

/**
 * Servlet implementation class DispatcherServelet
 */
// @WebServlet("/DispatcherServelet") -> web.xml에 매핑 정보를 등록해서 사용할 것이기 때문에 주석처리한다. (둘중에 하나만 사용 가능)
// 나중에 스프링 가면 DispatcherServelet 자체를 건들수가 없어서 어노테이션을 쓰지 못하고 web.xml에 매핑정보 등록하는 방법을 사용해야 한다.
// 서블릿이라고 인정 받으려면 매핑 정보가 등록 되어야 한다.
// web.xml 위치 : servlet\src\main\webapp\WEB-INF\web.xml
public class DispatcherServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
	// 기본생성자
    public DispatcherServelet() {
        super();
    }
	/**
	 * @see Servlet#init(ServletConfig)
	 */
    // 보통 init() 메서드에서는 oracle 드라이버 확인이나 처리에 필요한 객체생성(service,DAO,controller 등)을 한다.
	public void init(ServletConfig config) throws ServletException {
		System.out.println("DispatcherServlet.init() - 서버가 동작되면서 같이 처음 한 번 동작되는 초기화 메서드");
		// orcle 드라이버 확인
		try {
			Class.forName("com.dahee.io.db.DB");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 객체 생성해서 저장(조립)
		Init.init();
		System.out.println("DispatcherServlet.init() - 객체 생성함");
	}
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	// 사용자의 모든 정보가 request에 담겨있다.
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 메인 역할을 함
		System.out.println("DispatcherServlet.service() - 실행되고 있음.");
		// 한글 처리
		request.setCharacterEncoding("utf-8");
		try {
		// 사용자가 요청한 페이지(자원) - java에서는 switch문을 통해 1,2같은 번호를 눌렀었다.
		// url은 위치(서버)정보가 있고 uri는 resource 정보가 있음.
		String uri = request.getRequestURI();
		System.out.println("요청한 페이지 : " + uri);
		
		// 1. jsp로 forward 이동 -> "/WEB-INF/views/" + jsp + ".jsp"
		// 	  슬래시(/)는 두개써도 한개 처리가 되므로 2개를 써도 된다. 스프링에서는 보통 써놓는다.
		// 2. redirect : jsp 정보에 "redirect:" -> "redirect:"는 제거하고 자동으로 이동 시킬 때 url로 사용 
		String jsp = null;
		
		// 메인 처리
		if (uri.equals("/") || uri.equals("/index.do")) {
			System.out.println("메인 처리 해야 함.");
			jsp = "/main/main.jsp";
		// 오류 처리 Controller
		} else if(uri.indexOf("/error/") == 0) {
			jsp = "error/500";
		} else if(uri.indexOf("/board/") == 0) {
			// BoardController 실행
			// new해서 생성하는게 아니라 이미 생성되어있는 객체를 꺼내서 사용한다.
			jsp = ((BoardController)Init.getController("boardController")).execute(request);
		}
		int pos = jsp.indexOf("redirect:");
		// pos = 0이 나오면 맨앞(index의 0번째)에 있고 -1은 없다는 뜻.
		System.out.println("DispatcherServlet.service().pos : " + pos);
		if(pos == 0) {
			// 인덱스 맨 앞에 존재하면 redirect 시킨다. 단, "redirect:"은 제거하고 url로 사용한다.
			System.out.println("DispatcherServlet.service() - jsp 정보로 페이지 이동");
			response.sendRedirect(jsp.substring(("redirect:").length()));
		} else {
			System.out.println("DispatcherServlet.service() - jsp 정보로 jsp로 이동");
			request.getRequestDispatcher("/WEB-INF/views/"+jsp+".jsp").forward(request, response);
		} // end of if
		
		} catch (Exception e) {
			e.printStackTrace(); // 개발 당시에만 확인하고 나중에 주석처리 하면 되고 사용자에게는 밑에 코드 보여주면 됨.
			// error URL -> redirect 또는 바로 JSP로 forward 시킬 수 있다.
			// JSP를 사용하는 경우에 Exception을 사용 할 수 있다.
			// 다시 한번 실행해 달라는 메세지만 표시 할 경우에는 URL을 사용하면 되는데 오류처리 하러 갔을 때 무한루프 돌 수 있음.
			response.sendRedirect("/error/500.do"); // errorController를 만들어서 보내도 됨.
		}
	} // end of service	
} // end of class
