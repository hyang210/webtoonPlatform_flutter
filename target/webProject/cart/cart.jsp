<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${sessionScope.lang eq 'ko'}">장바구니 목록</c:when>
            <c:otherwise>Shopping Cart</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <style>
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .cart-table th, .cart-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .cart-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .cart-table tr:hover {
            background-color: #f5f5f5;
        }
        .cart-thumb {
            width: 60px;
            height: 90px;
            object-fit: cover;
            border-radius: 4px;
        }
        .cart-actions {
            margin-top: 30px;
            padding: 20px;
            background-color: #e9ecef;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .total-price {
            font-size: 1.5em;
            font-weight: bold;
            color: #d9534f; /* 붉은색 강조 */
        }
        .btn-delete-item, .btn-checkout {
            padding: 8px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .btn-delete-item {
            background-color: #dc3545;
            color: white;
        }
        .btn-delete-item:hover {
            background-color: #c82333;
        }
        .btn-checkout {
            background-color: #007bff;
            color: white;
            font-size: 1.1em;
            padding: 10px 20px;
        }
        .btn-checkout:hover {
            background-color: #0056b3;
        }
        .empty-cart {
            text-align: center;
            padding: 50px;
            font-size: 1.2em;
            color: #6c757d;
        }
        /* 알림 메시지 스타일 (CartAddController에서 사용된 것과 동일) */
        .alert-message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: bold;
        }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-fail { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
    <script>
        function confirmPurchase(totalPrice) {
            // 실제 구현에서는 custom modal을 사용해야 하나, 여기서는 편의상 alert 사용
            if (confirm(`
                <c:choose>
                    <c:when test="${sessionScope.lang eq 'ko'}">총 ${totalPrice} 코인을 사용하여 장바구니의 웹툰 ${cartList.size()}개를 모두 구매하시겠습니까?</c:when>
                    <c:otherwise>Are you sure you want to purchase ${cartList.size()} webtoons for a total of ${totalPrice} coins?</c:otherwise>
                </c:choose>
            `)) {
                // PurchaseController로 이동 (장바구니 전체 구매 요청)
                window.location.href = '<c:url value="/PurchaseController?type=cart"/>';
            }
        }
        
        function deleteItem(webtoonId) {
            // 실제 구현에서는 custom modal을 사용해야 하나, 여기서는 편의상 alert 사용
            if (confirm(`
                <c:choose>
                    <c:when test="${sessionScope.lang eq 'ko'}">해당 웹툰을 장바구니에서 삭제하시겠습니까?</c:when>
                    <c:otherwise>Are you sure you want to remove this webtoon from your cart?</c:otherwise>
                </c:choose>
            `)) {
                // CartDeleteController로 이동
                window.location.href = '<c:url value="/cart/CartDeleteController?webtoonId="/>' + webtoonId;
            }
        }
    </script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'ko'}">장바구니 목록</c:when>
                <c:otherwise>Shopping Cart</c:otherwise>
            </c:choose>
        </h2>
        
        <!-- 장바구니 삭제/구매 결과 메시지 표시 -->
        <c:if test="${param.result != null}">
            <div class="alert-message 
                <c:choose>
                    <c:when test="${param.result eq 'delete_success' || param.result eq 'purchase_success'}">alert-success</c:when>
                    <c:otherwise>alert-fail</c:otherwise>
                </c:choose>
            ">
                <c:choose>
                    <c:when test="${param.result eq 'delete_success' && sessionScope.lang eq 'ko'}">
                        장바구니 항목이 삭제되었습니다.
                    </c:when>
                    <c:when test="${param.result eq 'delete_success' && sessionScope.lang eq 'en'}">
                        Cart item deleted successfully.
                    </c:when>
                    <c:when test="${param.result eq 'delete_fail' && sessionScope.lang eq 'ko'}">
                        장바구니 항목 삭제에 실패했습니다.
                    </c:when>
                    <c:when test="${param.result eq 'purchase_success' && sessionScope.lang eq 'ko'}">
                        🎉 웹툰 구매가 완료되었습니다! 장바구니가 비워졌습니다.
                    </c:when>
                    <c:when test="${param.result eq 'purchase_success' && sessionScope.lang eq 'en'}">
                        🎉 Webtoon purchase completed! Your cart has been emptied.
                    </c:when>
                    <c:when test="${param.result eq 'purchase_fail' && sessionScope.lang eq 'ko'}">
                        구매에 실패했습니다. (잔액 부족 또는 DB 오류)
                    </c:when>
                    <c:when test="${param.result eq 'purchase_fail' && sessionScope.lang eq 'en'}">
                        Purchase failed. (Insufficient balance or DB error)
                    </c:when>
                    <c:otherwise>
                        처리 중 오류가 발생했습니다.
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty cartList}">
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">썸네일</c:when><c:otherwise>Thumbnail</c:otherwise></c:choose>
                            </th>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 제목</c:when><c:otherwise>Title</c:otherwise></c:choose>
                            </th>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">작가</c:when><c:otherwise>Author</c:otherwise></c:choose>
                            </th>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">가격</c:when><c:otherwise>Price</c:otherwise></c:choose>
                            </th>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">담은 날짜</c:when><c:otherwise>Added Date</c:otherwise></c:choose>
                            </th>
                            <th>
                                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">삭제</c:when><c:otherwise>Remove</c:otherwise></c:choose>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cartList}">
                            <tr>
                                <td>
                                    <img src="${item.webtoon.thumbnailPath}" alt="${item.webtoon.koTitle} Thumbnail" class="cart-thumb">
                                </td>
                                <td style="text-align: left;">
                                    <a href="<c:url value='/WebtoonDetailController?id=${item.webtoon.webtoonId}'/>">
                                        <c:choose>
                                            <c:when test="${sessionScope.lang eq 'ko'}">${item.webtoon.koTitle}</c:when>
                                            <c:otherwise>${item.webtoon.enTitle}</c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                                <td>${item.webtoon.author}</td>
                                <td>${item.webtoon.price} 코인</td>
                                <td>
                                    <fmt:formatDate value="${item.addedDate}" pattern="yyyy.MM.dd"/>
                                </td>
                                <td>
                                    <button class="btn-delete-item" onclick="deleteItem(${item.webtoon.webtoonId})">
                                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">삭제</c:when><c:otherwise>Remove</c:otherwise></c:choose>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="cart-actions">
                    <span class="total-price">
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">총 결제 금액:</c:when>
                            <c:otherwise>Total Price:</c:otherwise>
                        </c:choose>
                        ${totalPrice} 코인
                    </span>
                    <button class="btn-checkout" onclick="confirmPurchase(${totalPrice})">
                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">전체 구매하기 (${totalPrice} 코인)</c:when><c:otherwise>Checkout (${totalPrice} Coins)</c:otherwise></c:choose>
                    </button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <p>
                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">장바구니가 비어 있습니다.</c:when><c:otherwise>Your cart is empty.</c:otherwise></c:choose>
                    </p>
                    <p>
                        <a href="<c:url value='/WebtoonListController'/>">
                            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">다른 웹툰 보러 가기</c:when><c:otherwise>Go to Webtoon List</c:otherwise></c:choose>
                        </a>
                    </p>
                </div>
            </c:otherwise>
        </c:choose>
        
    </main>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>