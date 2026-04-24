<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>accounting.jsp</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/accounting.css">

</head>
<body>
	<!--ヘッダー(店の名前)-->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="${pageContext.request.contextPath}/image/grain.jpg" class="header-background-image">
			<img src="${pageContext.request.contextPath}/image/biglogo.png"class="header-image">
		</div>
	</header>
	<main>
		<div class="center-container">
			<div class="square-box">
				<p class="center-text">会計が確定されました</p>
				<p class="center-text">ご利用ありがとうございます</p>
				<span class="bold-text size-text">${accounting_info.table_id}卓</span>
				<span class="underline-text bold-text size-text">合計:${accounting_info.total_price}円(税込)</span>
			</div>
		</div>
		<p class="center-text">レジにてお支払いください</p>
		<p class="center-text">またのご利用をお待ちしております</p>
	</main>
	<uji:dispatch />
	<uji:resourceText id="uji.disableBack" />
</body>
</html>
