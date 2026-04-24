<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="Action" value="${applicationScope.Action}" scope="page" />
<c:set var="Param" value="${applicationScope.Param}" scope="page" />
<html>
<head>
<meta charset="UTF-8">
<title>order_history.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_history.css">
<script src="${pageContext.request.contextPath}/js/order_history.js" defer></script>
</head>
<body>
	<!--ヘッダー(店の名前)-->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="${pageContext.request.contextPath}/image/grain.jpg" class="header-background-image">
			<img src="${pageContext.request.contextPath}/image/biglogo.png" class="header-image">
		</div>
	</header>
	<main class="history-main">
		<!--注文履歴を取得-->
		<c:set var="orderHistory" value="${order_history_info}" />

		<div class="menu" id="order-history-container">
			<c:if test="${not empty orderHistory}">
				<!-- 合計値の初期化 -->
				<c:set var="iTotalQuantity" value="0" />
				<c:set var="iTotalPrice" value="0" />

				<div class="order-item order-item-header">
					<div class="order-item-left">
						<strong>商品名</strong>
					</div>
					<div class="order-item-center">
						<strong>数量</strong>
					</div>
					<div class="order-item-right">
						<strong>金額(税込)</strong>
					</div>
				</div>

				<!-- 明細の繰り返し -->
				<c:forEach var="history" items="${orderHistory}">
					<!-- 合計数量を加算 -->
					<c:set var="iTotalQuantity" value="${iTotalQuantity + history.product_quantity}" />
					<!-- 合計金額を加算 -->
					<c:set var="iTotalPrice" value="${iTotalPrice + history.order_price}" />

					<div class="order-item">
						<!-- 左：商品名とトッピング -->
						<div class="order-item-left">
							<div class="break-word bold-text">
								<strong><c:out value="${history.product_name}" /></strong>
							</div>
							<c:forEach var="multiple" items="${history.topping_details}">
								<div>・<c:out value="${multiple.topping_name}" />✕<c:out value="${multiple.topping_quantity}" /></div>
							</c:forEach>
						</div>

						<!-- 中央：数量 -->
						<div class="order-item-center">
							<c:out value="${history.product_quantity}" />
						</div>

						<!-- 右：金額 -->
						<div class="order-item-right">
							<c:out value="${history.order_price}" />円
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>

		<!-- 合計表示 -->
		<c:if test="${not empty orderHistory}">
			<footer class="footer-subtotal">
				<div class="footer-subtotal-wrapper">
					<div class="order-footer-text"><c:out value="${iTotalQuantity}" />点</div>
					<div class="order-footer-text"><c:out value="${iTotalPrice}" />円(税込)</div>
				</div>
			</footer>
		</c:if>

		<!-- 注文履歴なし -->
		<c:if test="${empty orderHistory}">
			<div class="break-word bold-text">注文履歴なし</div>
		</c:if>
		<c:set var="hasFlag1" value="false" />
		<!-- 注文履歴をループして、未提供の注文（order_flag == 0）が1つでもあれば true にする -->
		<c:forEach var="order" items="${orderHistory}">
		    <c:if test="${order.order_flag == 0}">
		        <c:set var="hasFlag1" value="true" />
		    </c:if>
		</c:forEach>
		
		<!-- ポップアップの背景（共通） -->
		<div class="popup-overlay" id="popup-overlay"></div>
		
		<!--未提供の注文があった場合のポップアップ -->
		<c:if test="${hasFlag1}">
		    <div class="popup-content" id="popup-content">
		        <p>未提供の注文があります。</p>
		        <button class="popup-close" id="close-popup">閉じる</button>
		    </div>
		</c:if>
		
		<!--未提供の注文がない場合のポップアップ -->
		<c:if test="${not hasFlag1}">
		    <div class="popup-content" id="popup-content">
		        <p>お会計に進みます。</p>
		        <p>よろしいですか？</p>
		        <button class="popup-close" id="close-popup">いいえ</button>
		        <form action="AccountingServlet" method="post">
		            <button class="popup-proceed" id="confirm-button">
		                <input type="hidden" name="table_id" value="<c:out value='${sessionScope.tableNumber}' />">
		                <input type="hidden" name="total" value="<c:out value='${iTotalPrice}' />">
		                は い
		            </button>
		        </form>
		    </div>
		</c:if>
	</main>
	<footer class="footer-buttons">
		<div class="table-number"><c:out value="${sessionScope.table_id}" />卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--会計確認ポップアップ表示-->
			<c:if test="${not empty order_history_info}">
				<button class="fixed-right-button" id="open-popup">
					<img src="${pageContext.request.contextPath}/image/history.png" alt="会計のボタン"> お会計
				</button>
			</c:if>
			<!--メニューへ遷移-->
			<a href="OrderMenuServlet">
				<button class="fixed-left-button">
					<img src="${pageContext.request.contextPath}/image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>
</body>
</html>
