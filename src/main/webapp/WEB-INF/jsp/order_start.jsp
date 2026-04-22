<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>order_start.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_start.css">
<script src="${pageContext.request.contextPath}/js/order_start.js"></script>
</head>

<body>

<header class="header-storename">
	<div class="header-image-wrapper">
		<img src="${pageContext.request.contextPath}/image/grain.jpg" class="header-background-image">
		<img src="${pageContext.request.contextPath}/image/biglogo.png" class="header-image">
	</div>
</header>

<main>
	<div class="center-container">
		<div class="square-box">
			<div class="table-number">
				<c:out value="${table_info.table_id}" /> 卓
			</div>
		</div>
	</div>
	
	<p class="center-text">いらっしゃいませ！</p>
	<p class="center-text">人数を設定してください</p>
	
	<div class="people-counter">
		<button type="button" id="decrement-btn">-</button>
		<span id="guest-count">1</span>
		<button type="button" id="increment-btn">+</button>
	</div>

	<div class="center-container">
		<form action="OrderMenuServlet" method="post">
			<input type="hidden" name="previous_state" value="OrderStart">
			<input type="hidden" name="session_id" value="<c:out value='${table_info.session_id}' />">
			<input type="hidden" name="table_id" value="<c:out value='${table_info.table_id}' />">
			<input type="hidden" name="session_status" value="<c:out value='${table_info.session_status}' />">
			<input type="hidden" name="guest_count" id="guest-input" value="1">
			<button class="state-btn">注文開始</button>
		</form>
	</div>
</main>
</body>
</html>