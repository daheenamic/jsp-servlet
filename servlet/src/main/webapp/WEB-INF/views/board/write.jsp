<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글등록</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/regEx.js"></script>
<script type="text/javascript">
$(function(){
	$("#writeForm").submit(function(){
		// 제목,내용,작성자,비밀번호 유효성 검사 & 비밀번호 확인
		if(!checkData(reg_title, $("#title"), reg_title_error_msg, 1)) return false;
		if(!checkData(reg_content, $("#content"), reg_content_error_msg, 1)) return false;
		if(!checkData(reg_writer, $("#writer"), reg_writer_error_msg, 1)) return false;
		if(!checkData(reg_pw, $("#pw"), reg_pw_error_msg, 0)) {
			$("#pw").val("");
			return false;
		}
		if(!checkData(reg_pw, $("#pw2"), reg_pw_error_msg,0)) {
			$("pw2").val("");
			return false;
		}
		if($("#pw").val() != $("#pw2").val()){
			alert("비밀번호가 다릅니다.");
			$("#pw, #pw2").val("");
			return false;
		}
	});
	
	// 취소 이벤트
	$("#cancelBtn").click(function(){
		history.back();
	});
});
</script>
</head>
<body>
	<div class="container">
		<h1>게시판 글등록</h1>
		<form action="write.do" method="post" id="writeForm">
			<div class="form-group">
				<label for="title">제목</label>
				<input name="title" id="title" class="form-control">
			</div>
			<div class="form-group">
				<label for="content">내용</label>
				<textarea name="content" id="content" class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label for="writer">작성자</label>
				<input name="writer" id="writer" class="form-control">
			</div>
			<div class="form-group">
				<label for="pw" >비밀번호</label>
				<input name="pw" id="pw" class="form-control" type="password">
			</div>
			<div class="form-group">
				<label for="pw2">비밀번호 확인</label>
				<input name="pw2" id="pw2" class="form-control" type="password">
			</div>
			<button type="submit" class="btn btn-default">등록</button>
			<button type="reset" class="btn btn-default">새로입력</button>
			<button type="button" id="cancelBtn" class="btn btn-default">취소</button>
		</form>
	</div>
</body>
</html>