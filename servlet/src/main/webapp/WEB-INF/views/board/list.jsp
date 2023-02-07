<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
.dataRow:hover {
	background : #eee;
	cursor: pointer;
}
</style>
<script type="text/javascript">
$(function(){
	// 게시판 한줄을 클릭하는 이벤트
	$(".dataRow").click(function(){
		let no = $(this).find(".no").text();
		location="view.do?no="+no +"&inc=1";
	});
});
</script>
</head>
<body>
<div class="container">
<h1>게시판 리스트(EL/JSTL)</h1>
<!-- BoardController에서 key로 list라고 저장한 것을 찾는다. -->
<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<!-- 데이터가 있는 만큼 반복문 처리 -->
		<c:forEach items="${list }" var="vo">
			<tr class="dataRow">
				<!-- 변수 자체를 호출한게 아니라 getter를 호출한것이다. -->
				<td class="no">${vo.no }</td>
				<td>${vo.title }</td>
				<td>${vo.writer }</td>
				<td>${vo.writeDate }</td>
				<td>${vo.hit }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a href="write.do" class="btn btn-default">글쓰기</a>
</div>
</body>
</html>