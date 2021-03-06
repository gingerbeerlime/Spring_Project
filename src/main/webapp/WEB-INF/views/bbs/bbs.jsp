<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- JSTL과 EL을 이용해서 Session에 있는 user_id 값ㅇ르 가져와 user_id에 저장 -->
<c:set var ="user_id" value="${sessionScope.user_id }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${path}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${path}/resources/css/custom.css">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="${path}/resources/js/bootstrap.js"></script>

<style>
	a, a:hover {
		color: #000000;
		text-decoration: none;
		
	}
</style>

</head>
<body>

<jsp:include page="../bbsNav.jsp" />

<!-- 메인 게시판  -->
<div class="container">
	<div class="row">		
		<table class="table table-striped" style="text-align: center; border: 1px solid #bbbbbb;">			
			<thead>
				<tr>
					<th style="background-color: #dddddd; text-align: center;">번호</th>
					<th style="background-color: #dddddd; text-align: center;">제목</th>
					<th style="background-color: #dddddd; text-align: center;">작성자</th>
					<th style="background-color: #dddddd; text-align: center;">작성일</th>
				</tr>
			</thead>	
			<tbody>
				
				<c:forEach var="boarder" items="${map.list }">
				<tr>
					<td>${boarder.boarder_id }</td>
					<td><a href="${path}/bbs/view?boarder_id=${boarder.boarder_id}">${boarder.title }</a></td>
					<td>${boarder.writer }</td>
					<td>${boarder.reg_date }</td>
				</tr>
				</c:forEach>
			</tbody>			
		</table>
		<%-- GET방식 전송 : URL?파라미터=데이터&파라미터=데이터 --%>
		<ul class="pagination">
		
			<c:if test="${map.paging.pageNumber > 5 }">
			
			<li><a href="./bbs?pageNumber=${map.paging.before }"><span class="glyphicon glyphicon-triangle-left"></span></a></li>
			
			</c:if>
			
			<c:forEach var="i" begin="${map.paging.minPage }" end="${map.paging.maxPage }">
				<c:if test="${i == map.paging.pageNumber }">
					<li class="active"><a>${i }</a></li> 
				</c:if>
				<c:if test="${i != map.paging.pageNumber }">
				<li><a href="./bbs?pageNumber${i }">${i }</a></li> 
				</c:if>
			</c:forEach>			
			
			<c:if test ="${map.paging.next }">
			
			<li><a href="./bbs?pageNumber=${map.paging.forward }"><span class="glyphicon glyphicon-triangle-right"></span></a></li>
		
			</c:if>
		
		</ul>
		
		<a href="${path}/bbs/write" class="btn btn-default pull-right">글쓰기</a>
	</div>
</div>
<!-- 메인 게시판 종료 -->

<script>
$(document).ready(function(){
	var msg = '${msg}';
	if(msg != null && msg != '') alert(msg);
});
</script>

</body>
</html>