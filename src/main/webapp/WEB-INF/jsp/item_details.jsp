<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="Action" value="${applicationScope.Action}" scope="page" />
<c:set var="Param" value="${applicationScope.Param}" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>item_detail.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item_details.css">
<link rel="icon" href="data:," />
</head>
<body>
	<%
	String previousState = request.getParameter("previous_state");
	%>
	<!-- ヘッダー(店の名前) -->
	<header class="header-storename">
		<div class="header-image-wrapper">
			<img src="${pageContext.request.contextPath}/image/grain.jpg" class="header-background-image"> 
			<img src="${pageContext.request.contextPath}/image/biglogo.png" class="header-image">
		</div>
	</header>
	<header class="header-product">
		<div class="header-product-wrapper">
			<!-- EL式を使用して商品名を表示 -->
			<%
			//メニュー画面から遷移してきた場合
			if (previousState.equals("OrderMenu")) {
			%>
			<c:if test="${not empty item_info}">
				<div class="product-text">
					<c:out value="${item_info.product_name}" />
				</div>
				<div class="price-text">
					<c:out value="${item_info.product_price}" />
					円(税込)
				</div>
			</c:if>
			<%
			//注文リスト画面から遷移してきた場合
			} else if (previousState.equals("OrderList")) {
			%>
			<c:if test="${not empty item_info}">
				<div class="product-text">
					<c:out value="${item_info.product_name}" />
				</div>
				<div class="price-text">
					<c:out value="${item_info.product_price}" />
					円(税込)
				</div>
			</c:if>
			<%
			}
			%>
		</div>
	</header>
	<main class="details-main">
		<%
		//メニュー画面から遷移してきた場合
		if (previousState.equals("OrderMenu")) {
		%>
		<!-- セッションからカテゴリー名を取ってくる -->
		<c:if test="${not empty item_info.category_name}">
			<c:choose>
				<c:when
					test="${item_info.category_name == 'お好み焼き' || item_info.category_name == 'もんじゃ焼き'}">
					<c:if test="${not empty topping_info}">
						<p>トッピング</p>
						<c:forEach var="topping" items="${topping_info}" varStatus="status">
							<li class="topping-row">
								<c:if test="${topping.topping_display_flag == 1}">
									<div class="break-topping">
										<c:out value="${topping.topping_name}" />
										：
										<c:out value="${topping.topping_price}" />
										円
									</div>
									<c:if test="${topping.topping_stock > 0}">
										<button class="counter-button minus" data-id="<c:out value='${topping.topping_id}' />" data-index="<c:out value='${status.index}' />">-</button>
										<input type="text" value="0" class="counter-input" readonly data-id="<c:out value='${topping.topping_id}' />">
										<button class="counter-button plus" data-index="<c:out value='${status.index}' />" data-id="<c:out value='${topping.topping_id}' />" data-max="<c:out value='${topping.topping_stock}' />">+</button>
									</c:if>
									<c:if test="${topping.topping_stock <= 0}">
										<img src="${pageContext.request.contextPath}/image/soldout.png" alt="Sold Out" class="soldout-img" />
									</c:if>
								</c:if>
							</li>
						</c:forEach>
					</c:if>
					<c:if test="${empty topping_info}">
						<div>トッピングはありません。</div>
					</c:if>
				</c:when>
			</c:choose>
		</c:if>
		<%
		//注文リスト画面から遷移してきた場合
		} else if (previousState.equals("OrderList")) {
		%>
		<!-- セッションからカテゴリー名を取ってくる -->
		<c:if test="${not empty item_info.category_name}">
			<c:choose>
				<c:when test="${item_info.category_name == 'お好み焼き' || item_info.category_name == 'もんじゃ焼き'}">
					<p>トッピング</p>
					<c:if test="${not empty topping_info}">
						<c:forEach var="topping" items="${topping_info}" varStatus="status">
							<c:set var="topping_quantity" value="${item_info.topping_quantity[status.index]}" />
							<li class="topping-row">
								<c:if test="${topping.topping_display_flag == 1}">
									<div class="break-topping">
										<c:out value="${topping.topping_name}" />
										：
										<c:out value="${topping.topping_price}" />
										円
									</div>
									<c:if test="${topping.topping_stock > 0}">
										<button class="counter-button minus" data-id="<c:out value='${topping.topping_id}' />" data-index="<c:out value='${status.index}' />">-</button>
										<input type="text" value="<c:out value='${topping_quantity}' />" class="counter-input" readonly data-id="<c:out value='${topping.topping_id}' />">
										<button class="counter-button plus" data-index="<c:out value='${status.index}' />" data-id="<c:out value='${topping.topping_id}' />" data-max="<c:out value='${topping.topping_stock}' />">+</button>
									</c:if>
									<c:if test="${topping.topping_stock <= 0}">
										<img src="${pageContext.request.contextPath}/image/soldout.png" alt="Sold Out" class="soldout-img" />
									</c:if>
								</c:if></li>
						</c:forEach>
					</c:if>
					<c:if test="${empty topping_info}">
						<div>トッピングはありません。</div>
					</c:if>
				</c:when>
			</c:choose>
		</c:if>
		<%
		}
		%>
	</main>
	<footer class="footer-buttons">
		<div class="table-number">
 		<c:out value="${sessionScope.table_id}" />
			卓
		</div>
		<div class="footer-wrapper">
			<!-- ボタン -->
			<% 
			//メニュー画面から遷移してきた場合
			if (previousState.equals("OrderMenu")) {
			%>
			<form action="OrderListServlet" method="post">
				<button class="fixed-right-button">
					<input type="hidden" name="product_id" value="<c:out value='${item_info.product_id}' />"> 
					<input type="hidden" name="product_name" value="<c:out value='${item_info.product_name}' />">
					<input type="hidden" name="category_name" value="<c:out value='${item_info.category_name}' />">
					<input type="hidden" name="product_price" value="<c:out value='${item_info.product_price}' />">
					<input type="hidden" name="product_stock" value="<c:out value='${item_info.product_stock}' />">
					<c:forEach var="topping" items="${topping_info}" varStatus="status">
						<input type="hidden" name="topping_id" value="<c:out value='${topping.topping_id}' />">
						<input type="hidden" name="topping_name" value="<c:out value='${topping.topping_name}' />">
						<input type="hidden" name="topping_price" value="<c:out value='${topping.topping_price}' />">
						<input type="hidden" name="topping_quantity" id="topping-<c:out value='${topping.topping_id}' />" value="0">
						<input type="hidden" name="topping_stock" value="<c:out value='${topping.topping_stock}' />">
					</c:forEach>
					<input type="hidden" name="${Param.TOTAL}" id="input-total" value=""> 
					<img src="${pageContext.request.contextPath}/image/addCart.png"alt="追加のボタン"> 追加
				</button>
			</form>
			<%
			//注文リスト画面から遷移してきた場合
			} else if (previousState.equals("OrderList")) {
			%>
			<form action="OrderListServlet" method="post">
				<button class="fixed-right-button">
					<input type="hidden" name="order_id"value="<c:out value='${item_info.order_id}' />"> 
					<input type="hidden" name="product_id" value="<c:out value='${item_info.product_id}' />"> 
					<input type="hidden" name="product_name" value="<c:out value='${item_info.product_name}' />">
					<input type="hidden" name="category_name" value="<c:out value='${item_info.category_name}' />">
					<input type="hidden" name="product_price" value="<c:out value='${item_info.product_price}' />">
					<input type="hidden" name="subtotal" value="<c:out value='${item_info.subtotal}' />">
					<c:forEach var="topping_id" items="${item_info.topping_id}" varStatus="status">
						<c:set var="topping_name" value="${item_info.topping_name[status.index]}" />
						<c:set var="topping_price" value="${item_info.topping_price[status.index]}" />
						<c:set var="topping_quantity" value="${item_info.topping_quantity[status.index]}" />
						<c:set var="topping_stock" value="${item_info.topping_stock[status.index]}" />
						<input type="hidden" name="topping_id_arr" value="<c:out value='${topping_id}' />">
						<input type="hidden" name="topping_name_arr" value="<c:out value='${topping_name}' />">
						<input type="hidden" name="topping_price_arr" value="<c:out value='${topping_price}' />">
						<%--valueに変更していない個数がリセットされないように取得した${topping_quantity}を入れる--%>
						<input type="hidden" name="topping_quantity_arr" 
						id="topping-<c:out value='${topping_id}' />" value="<c:out value='${topping_quantity}' />">
						<input type="hidden" name="topping_stock_arr" value="<c:out value='${topping_stock}' />">
					</c:forEach>
					<input type="hidden" name="${Param.TOTAL}" id="input-total" value=""> 
					<img src="${pageContext.request.contextPath}/image/changeCart.png" alt="変更のボタン"> 変更
				</button>
			</form>
			<%
			}
			%>
			<a href="OrderMenuServlet">
				<button class="fixed-left-button">
					<img src="${pageContext.request.contextPath}/image/menu.png" alt="メニューのボタン"> メニュー
				</button>
			</a>
		</div>
	</footer>
	<footer class="footer-subtotal">
		<div class="footer-subtotal-wrapper">
			<div class="subtotal-text">
				小計:<span id="total">0</span>円(税込)
			</div>
		</div>
	</footer>

	<script>
    // 商品本体価格
    const basePrice = <c:out value="${empty item_info ? (empty item_info ? 0 : item_info.product_price) : item_info.product_price}" />;
    const initialTotal = <c:out value="${empty item_info ? (empty item_info ? 0 : item_info.subtotal) : item_info.product_price}" />;

    // トッピングの価格を JS オブジェクトで渡す
    const toppingPrices = {
	<c:if test="${not empty topping_info}">
    	<c:forEach var="topping" items="${topping_info}" varStatus="status">
        	"${topping.topping_id}": ${topping.topping_price}<c:if test="${!status.last}">,</c:if>
    	</c:forEach>
	</c:if>
	};
	</script>
	<script src="${pageContext.request.contextPath}/js/item_details.js"></script>
</body>
</html>
