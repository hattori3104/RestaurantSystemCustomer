<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="Action" value="${applicationScope.Action}" scope="page" />
<c:set var="Param" value="${applicationScope.Param}" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>メニュー画面</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_menu.css">
<link rel="icon" href="data:," />
</head>
<body>

<header class="header-storename">
	<div class="header-image-wrapper">
		<img src="${pageContext.request.contextPath}/image/grain.jpg" class="header-background-image">
		<img src="${pageContext.request.contextPath}/image/biglogo.png" class="header-image">
	</div>
</header>

<main>
		<div class="tab">
			<!-- ラジオボタン（表示制御のキーになる） -->
			<!-- 直前に押されていたボタンを呼び出す -->
			<c:forEach var="category" items="${categoryList}" varStatus="status">
				<input type="radio" name="tab" class="tab-item" id="tab${status.index}" ${status.index == 0 ? "checked" : ""}>
			</c:forEach>
			<div class="tab-wrapper">
				<!-- ラベル（横スクロール） -->
				<div class="tab-labels">
					<c:forEach var="category" items="${categoryList}" varStatus="status">
						<label for="tab${status.index}"><c:out value="${category}" /></label>
					</c:forEach>
				</div>
			</div>
			<div class="tab-contents">
				<c:if test="${not empty product_info_list}">
					<c:forEach var="product" items="${product_info_list}">
						<div class="order_row hidden-row" data-category="<c:out value='${product.category_name}'/>">
							<div class="menu">
							<c:if test="${product.product_display_flag == 1}">
								<li>
									<div class="menu-row">
										<div class="break-word bold-text"><c:out value="${product.product_name}" /></div>
										<c:if test="${product.product_stock > 0}">
											<%-- 在庫がある場合は商品詳細画面へ遷移 --%>
											<form action="${Action.DETAILS_ADD}" method="post">
												<input type="hidden" name="from" value="order_menu.jsp">
												<input type="hidden" name="${Param.PRODUCT_ID}" value="<c:out value="${product.product_id}" />">
												<input type="hidden" name="${Param.PRODUCT_NAME}" value="<c:out value="${product.product_name}" />">
												<input type="hidden" name="${Param.PRODUCT_PRICE}" value="<c:out value="${product.product_price}" />">
												<input type="hidden" name="${Param.CATEGORY_NAME}" value="<c:out value="${product.category_name}" />"> 
												<input type="hidden" name="${Param.PRODUCT_STOCK}" value="<c:out value="${product.product_stock}" />">
												<input type="image" src="${pageContext.request.contextPath}/image/plusButton.png" alt="商品詳細画面へ遷移する">
											</form>
										</c:if>
										<c:if test="${product.product_stock <= 0}">
											<img src="${pageContext.request.contextPath}/image/soldout.png" alt="売り切れ" style="width: 55px; height: auto;">
										</c:if>
									</div>
									<p><c:out value="${product.product_price}" />円</p>
								</li>
								</c:if>
							</div>
							</div>
					</c:forEach>
				</c:if>
				<c:if test="${empty product_info_list}">
				商品情報がありません。
				</c:if>
			</div>
		</div>
</main>

<!-- jsにjspのcategoryListを渡す -->
<script>
		const categoryList=[
			<c:forEach items="${product_category}" var="cat" varStatus="loop">
			'<c:out value="${cat}"/>'<c:if test="${!loop.last}">,</c:if>
			</c:forEach>
			];
</script>

<script src="${pageContext.request.contextPath}/js/order_menu.js"></script>

<footer class="footer-buttons">
		<div class="table-number"><c:out value="${sessionScope.table_id}" />卓</div>
		<div class="footer-wrapper">
			<!--ボタン-->
			<!--注文リストへ遷移-->
			<form action="${Action.LIST}" method="post">
				<button class="fixed-right-button">
					<img src="${pageContext.request.contextPath}/image/cart.png" alt="注文リストのボタン">
					<c:if test="${empty sessionScope.orderListInfo}">
					<div>
						注文リスト
					</div>
					</c:if>
					<!--注文リストが空でないときのみ注文件数を表示-->
					<c:if test="${not empty sessionScope.orderListInfo}">
					<div class="orderlist">
						注文リスト
						<span class="count">${fn:length(sessionScope.orderListInfo)}</span>
					</div>
					</c:if>
				</button>
			</form>
			<!--履歴・お会計へ遷移-->
			<form action="${Action.HISTORY}" method="post">
				<button class="fixed-left-button">
					<img src="${pageContext.request.contextPath}/image/menuhistory.png" alt="履歴・お会計のボタン"> 履歴・お会計
				</button>
			</form>
		</div>
</footer>

<script>
	  const contextPath = '<%= request.getContextPath() %>';
</script>

</body>
</html>
