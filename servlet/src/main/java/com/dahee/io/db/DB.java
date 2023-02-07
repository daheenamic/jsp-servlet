package com.dahee.io.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	// 접속 정보가 필요함
	// 밖으로 가져나가는 정보가 아니기 때문에 getter setter가 필요하지 않음
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ID = "java";
	private static final String PW = "java";
	
	private static boolean isCheckDriver;
	
	static {
		try {
			Class.forName(DRIVER); // 1. 드라이버 확인. 있으면 static 요소를 로딩
			isCheckDriver = true;
			System.out.println("DB.static 블록 : 드라이버 확인 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end of static
	
	// DB.getConnection() - 1번, 2번
	public static Connection getConnection() throws Exception {
		if(!isCheckDriver) throw new Exception("오라클 드라이버가 존재하지 않음.");
		return DriverManager.getConnection(URL, ID, PW);
	}
	
	// 7. 닫기
	public static void close(Connection con, PreparedStatement pstmt) throws SQLException {
		if(con != null) con.close();
		if(pstmt != null) pstmt.close();
	}
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws SQLException {
		if(con != null) con.close();
		if(pstmt != null) pstmt.close();
		if(rs != null) rs.close();
	}
} // end of DB