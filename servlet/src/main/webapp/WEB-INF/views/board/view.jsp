<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
#deleteDiv{
	display: none;
}
</style>

<script type="text/javascript">
$(function(){
	// 삭제버튼 이벤트 처리 - a tag
	$("#deleteBtn").click(function(){
		$("#deleteDiv").slideDown(); // 보이게
		return false; // 페이지 전송 a 취소
	});
	$("#cancelDeleteBtn").click(function(){
		$("#deleteDiv").slideUp(); // 사라지게
	});
});
</script>
</head>
<body>
<div class="container">
	<h1>게시판 글보기</h1>
	<table class="table">
		<tbody>
			<tr class="dataRow">
				<th>번호</th>
				<td>${vo.no }</td>
			</tr>
			<tr class="dataRow">
				<th>제목</th>
				<td>${vo.title }</td>
			</tr>
			<tr class="dataRow">
				<th>내용</th>
				<td>${vo.content }</td>
			</tr>
			<tr class="dataRow">
				<th>작성자</th>
				<td>${vo.writer }</td>
			</tr>
			<tr class="dataRow">
				<th>작성일</th>
				<td>${vo.writeDate }</td>
			</tr>
			<tr class="dataRow">
				<th>조회수</th>
				<td>${vo.hit }</td>
			</tr>
		</tbody>
	</table>
	<a href="update.do?no=${vo.no }&inc=0" class="btn btn-default">수정</a>
	<a href="delete.do" class="btn btn-default" id="deleteBtn">삭제</a>
	<a href="list.do"  class="btn btn-default">목록</a>
	<div id="deleteDiv" class="well">
		<form action="delete.do" method="post">
			<input type="hidden" name="no" value="${vo.no }">
			<div class="form-group">
				<label for="pw">비밀번호</label>
				<input type="password" name="pw" class="form-control">
			</div>
			<button type="submit" class="btn btn-default">삭제</button>
			<button type="button" id="cancelDeleteBtn" class="btn btn-default">취소</button>
		</form>
	</div>
</div>
</body>
</html>