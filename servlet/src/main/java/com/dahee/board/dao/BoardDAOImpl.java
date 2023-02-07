package com.dahee.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.dahee.board.vo.BoardVO;
import com.dahee.io.db.DB;

public class BoardDAOImpl implements BoardDAO{
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	@Override
	public List<BoardVO> list() throws Exception {
		System.out.println("BoardDAOImpl.list()");
		// 결과가 전달되는 변수의 타입은 List 타입
		List<BoardVO> list = null; // 나중에 DB에서 꺼내서 채운다.
		try {
			// 1.확인 2.연결
			con = DB.getConnection();
			// 3. sql
			String sql = "SELECT no, title, writer, "
					+ " to_char(writeDate, 'yyyy-mm-dd') writeDate, hit "
					+ " FROM board ORDER BY no DESC";
			System.out.println("BoardDAOImpl.list().sql : " + sql);
			// 4. 실행객체 & 데이터 세팅
			pstmt = con.prepareStatement(sql);
			// 5. 실행 -> rs
			rs = pstmt.executeQuery();
			// 6. 데이터 저장 - list 변수에 저장
			// rs가 null이 아니면 처리한다.
			if(rs != null) {
				// rs에 다음 데이터가 있으면 처리 한다.
				while(rs.next()) {
					// list가 null이면 한번만 생성
					if(list == null) list = new ArrayList<>();
					// 데이터를 담을 vo 객체를 생성
					BoardVO vo = new BoardVO();
					// rs의 데이터를 꺼내서 vo에 담는다.
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setWriter(rs.getString("writer"));
					vo.setWriteDate(rs.getString("writeDate"));
					vo.setHit(rs.getLong("hit"));
					// 데이터가 담긴 vo를 list에 담는다.
					list.add(vo);
				} // end of while
			} // end of if
		} catch (Exception e) {
			e.printStackTrace(); // 개발자를 위한 오류 메시지
			throw new Exception("게시판 리스트 DB처리 중 오류 발생"); // 설정한 오류 메시지 담아서 표시
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try~catch~finally
		return list;
	} // end of list()

	@Override
	public BoardVO view(long no) throws Exception {
		System.out.println("BoardDAOImpl.view().no : " + no);
		// 리턴 데이터 선언
		BoardVO viewVO = null;
		try {
			con = DB.getConnection();
			String sql = "SELECT no, title, content, writer, "
					+ " to_char(writeDate, 'yyyy-mm-dd') writeDate, hit "
					+ " FROM board WHERE no = ? ";
			System.out.println("BOardDAOImpl.view().sql : " + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if(rs != null && rs.next()) {
				viewVO = new BoardVO();
				viewVO.setNo(rs.getLong("no"));
				viewVO.setTitle(rs.getString("title"));
				viewVO.setContent(rs.getString("content"));
				viewVO.setWriter(rs.getString("writer"));
				viewVO.setWriteDate(rs.getString("writeDate"));
				viewVO.setHit(rs.getLong("hit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("게시판 글보기 DB처리 중 오류 발생");
		} finally {
			DB.close(con, pstmt, rs);
		}
		return viewVO;
	}

	@Override
	public int increase(long no) throws Exception {
		System.out.println("BoardDAOImpl.increase().no : " + no);
		int result = 0;
		try {
			con = DB.getConnection();
			String sql = "UPDATE board SET hit = hit + 1 WHERE no = ?";
			System.out.println("BOardDAOImpl.increase().sql : " + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
			result = pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("BoardDAOImpl.increase() - 조회수가 1 증가 완료");
			} else {
				throw new Exception("글번호가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("게시판 조회수 DB처리 중 오류 발생");
		} finally {
			DB.close(con, pstmt);
		}
		return result;
	}

	@Override
	public int write(BoardVO vo) throws Exception {
		System.out.println("BoardDAOImpl.write().vo : " + vo);
		int result = 0;
		try {
			con = DB.getConnection();
			String sql = "INSERT INTO board(no, title, content, writer, pw) "
					+ " VALUES(board_seq.nextval, ?, ?, ?, ?)";
			System.out.println("BOardDAOImpl.write().sql : " + sql);
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			pstmt.setString(4, vo.getPw());
			result = pstmt.executeUpdate();
			System.out.println("BoardDAOImpl.write() - 글등록 완료");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("게시판 글등록 DB처리 중 오류 발생");
		} finally {
			DB.close(con, pstmt);
		}
		return result;
	}

	@Override
	public int update(BoardVO vo) throws Exception {
		System.out.println("BoardDAOImpl.update().vo : " + vo);
		int result = 0;
		try {
			con = DB.getConnection();
			String sql = "UPDATE board SET title=?, content=?, writer=? "
					+ " WHERE no = ? AND pw = ?";
			System.out.println("BoardDAOImpl.update().sql : " + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getNo());
			pstmt.setString(5, vo.getPw());
			result = pstmt.executeUpdate();
			System.out.println("BoardDAOImpl.update() - 글수정 완료");		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("게시판 글수정 DB처리 중 오류 발생");			
		} finally {
			DB.close(con, pstmt);
		}
		return result;
	}

	@Override
	public int delete(BoardVO vo) throws Exception {
		System.out.println("BoardDAOImpl.delete().vo : " + vo);
		int result = 0;
		try {
			con = DB.getConnection();
			String sql = "DELETE FROM board WHERE no = ? AND pw = ?";
			System.out.println("BoardDAOImpl.delete().sql : " + sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPw());
			result= pstmt.executeUpdate();
			System.out.println("BoardDaoImpl.delete() - 글삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("게시판 글삭제 DB처리 중 오류 발생");
		} finally {
			DB.close(con, pstmt);
		}
		return result;
	}
} // end of class