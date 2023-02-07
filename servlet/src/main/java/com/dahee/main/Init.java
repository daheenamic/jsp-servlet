package com.dahee.main;

import java.util.HashMap;
import java.util.Map;
import com.dahee.board.controller.BoardController;
import com.dahee.board.dao.BoardDAOImpl;
import com.dahee.board.service.BoardDeleteServiceImpl;
import com.dahee.board.service.BoardListServiceImpl;
import com.dahee.board.service.BoardUpdateServiceImpl;
import com.dahee.board.service.BoardViewServiceImpl;
import com.dahee.board.service.BoardWriteServiceImpl;

// 서버를 실행하게 되면 DispatcherServelet.init()가 실행되면서 호출해서 실행되는 클래스
// 객체를 생성하고 필요한 객체를 setter나 생성자를 이용해서 넣어준다.
public class Init {
	// controller 저장변수 선언
	private static Map<String, Object> controllerMap = new HashMap<>();
	private static Map<String, ServiceInterface> serviceMap = new HashMap<>();
	private static Map<String, Object> daoMap = new HashMap<>();
	
	// 객체 생성해서 필요한 객체를 저장(조립)한다.
	// new를 계속 하면 그만큼 객체가 계속 생겨나기 때문에 다른 클래스에서 new가 나오면 안된다.
	// 그래서 최초에 한번만 실행되는 init 클래스에서만 new가 나와야 한다.
	public static void init() {
		// controller 생성
		controllerMap.put("boardController", new BoardController());
		
		// dao 생성
		daoMap.put("boardDAO", new BoardDAOImpl());
		
		// Service 생성 - list, view, write, update, delete ...
		serviceMap.put("boardListServiceImpl", new BoardListServiceImpl());
		serviceMap.put("boardViewServiceImpl", new BoardViewServiceImpl());
		serviceMap.put("boardWriteServiceImpl", new BoardWriteServiceImpl());
		serviceMap.put("boardUpdateServiceImpl", new BoardUpdateServiceImpl());
		serviceMap.put("boardDeleteServiceImpl", new BoardDeleteServiceImpl());
		
		//dao -> service : 조립
		serviceMap.get("boardListServiceImpl").setDao(daoMap.get("boardDAO"));
		serviceMap.get("boardViewServiceImpl").setDao(daoMap.get("boardDAO"));
		serviceMap.get("boardWriteServiceImpl").setDao(daoMap.get("boardDAO"));
		serviceMap.get("boardUpdateServiceImpl").setDao(daoMap.get("boardDAO"));
		serviceMap.get("boardDeleteServiceImpl").setDao(daoMap.get("boardDAO"));
		
		// service -> controller : 조립
		((BoardController)(controllerMap.get("boardController"))).setBoardListService(serviceMap.get("boardListServiceImpl"));
		((BoardController)(controllerMap.get("boardController"))).setBoardViewService(serviceMap.get("boardViewServiceImpl"));
		((BoardController)(controllerMap.get("boardController"))).setBoardWriteService(serviceMap.get("boardWriteServiceImpl"));
		((BoardController)(controllerMap.get("boardController"))).setBoardUpdateService(serviceMap.get("boardUpdateServiceImpl"));
		((BoardController)(controllerMap.get("boardController"))).setBoardDeleteService(serviceMap.get("boardDeleteServiceImpl"));
	}
	
	// controller 꺼내가기 - 변수가 private이라서 직접 사용 할 수 없고 getter를 이용해서 꺼내간다.
	public static Object getController(String key) {
		return controllerMap.get(key);
	}
}
