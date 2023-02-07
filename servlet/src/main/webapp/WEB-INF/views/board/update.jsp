<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글수정</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/regEx.js"></script>
<script type="text/javascript">
$(function(){
	$("#updateForm").submit(function(){
		// 유효성검사
		if(!checkData(reg_title, $("#title"), reg_title_error_msg, 1)) return false;
		if(!checkData(reg_content, $("#content"), reg_content_error_msg, 1)) return false;
		if(!checkData(reg_writer, $("#writer"), reg_writer_error_msg, 1)) return false;
		if(!checkData(reg_pw, $("#pw"), reg_pw_error_msg, 0)) {
			$("#pw").val("");
			return false;
		}
	});
	
	$("#cancelBtn").click(function(){
		history.back();
	});
});
</script>
</head>
<body>
	<div class="container">
		<h1>게시판 글수정</h1>
		<form action="update.do" method="post" id="updateForm">
			<!-- 번호는 hidden으로 만들어도 된다. -->
			<div class="form-group">
				<label for="no">번호</label>
				<input name="no" id="no" value="${vo.no }" class="form-control" readonly>
			</div>
			<div class="form-group">
				<label for="title">제목</label>
				<input name="title" id="title" value="${vo.title }" class="form-control">
			</div>
			<div class="form-group">
				<label for="content">내용</label>
				<textarea name="content" id="content" class="form-control">${vo.content }</textarea>
			</div>
			<div class="form-group">
				<label for="writer">작성자</label>
				<input name="writer" id="writer" value="${vo.writer }" class="form-control">
			</div>
			<div class="form-group">
				<label for="pw">비밀번호</label>
				<input name="pw" id="pw" class="form-control" type="password">
			</div>
			<button type="submit" class="btn btn-default">수정</button>
			<button type="reset" class="btn btn-default">새로입력</button>
			<button type="button" id="cancelBtn" class="btn btn-default">취소</button>
		</form>
	
	</div>
</body>
</html>