<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- 장바구니 기능은 로그인 사용자만 접근 가능 --%>
<c:if test="${sessionScope.userId == null}">
    <script>
        // 🚨 alert() 대신 커스텀 모달이나 div 메시지 사용을 권장하지만, JSP 환경을 위해 유지합니다.
        // window.alert('로그인 후 이용 가능합니다.');
        location.href = '<%= request.getContextPath() %>/user/loginForm.jsp';
    </script>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webtoon Platter - 장바구니</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css"> 
    <style>
        /* 기존 cartList.jsp의 CSS를 여기에 유지합니다. */
        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .page-title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-size: 2em;
        }
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        .cart-table th, .cart-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
            text-align: center;
        }
        .cart-table th {
            background-color: #f8f8f8;
            font-weight: 600;
            color: #555;
        }
        .webtoon-thumbnail {
            width: 80px;
            height: 120px;
            object-fit: cover;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .price-cell {
            color: #FF5722; /* 코인 색상 */
            font-weight: bold;
        }
        .total-area {
            text-align: right;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 2px solid #555;
            font-size: 1.2em;
            font-weight: 600;
        }
        .total-price {
            color: #FF5722;
            font-size: 1.3em;
            margin-left: 10px;
        }
        .action-buttons {
            text-align: center;
            margin-top: 30px;
        }
        .action-buttons a, .action-buttons button {
            display: inline-block;
            padding: 12px 25px;
            margin: 0 10px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.1s;
        }
        .purchase-btn {
            background-color: #4CAF50; /* 구매 버튼 녹색 */
            color: white;
        }
        .purchase-btn:hover {
            background-color: #45a049;
            transform: translateY(-1px);
        }
        .continue-shopping-btn {
            background-color: #007BFF; /* 쇼핑 계속하기 파란색 */
            color: white;
        }
        .continue-shopping-btn:hover {
            background-color: #0056b3;
            transform: translateY(-1px);
        }
        .delete-btn {
            background-color: #f44336; /* 삭제 버튼 빨간색 */
            color: white;
            padding: 8px 15px;
            font-size: 0.9em;
        }
        .delete-btn:hover {
            background-color: #d32f2f;
        }
        .empty-cart-message {
            text-align: center;
            font-size: 1.5em;
            color: #999;
            padding: 50px 0;
        }
    </style>
</head>
<body>

<jsp:include page="../common/header.jsp" />

<main class="container">
    <h2 class="page-title">
        <c:choose>
            <c:when test="${lang eq 'ko'}">장바구니 목록</c:when>
            <c:otherwise>Shopping Cart</c:otherwise>
        </c:choose>
    </h2>

    <c:choose>
        <%-- 1. 장바구니에 항목이 있을 경우 --%>
        <c:when test="${not empty cartList}">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">웹툰 썸네일</c:when>
                                <c:otherwise>Thumbnail</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">제목</c:when>
                                <c:otherwise>Title</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">작가</c:when>
                                <c:otherwise>Author</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">가격</c:when>
                                <c:otherwise>Price</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">추가일</c:when>
                                <c:otherwise>Added Date</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <c:choose>
                                <c:when test="${lang eq 'ko'}">관리</c:when>
                                <c:otherwise>Manage</c:otherwise>
                            </c:choose>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="totalPrice" value="0" />
                    <c:forEach var="item" items="${cartList}">
                        <c:set var="totalPrice" value="${totalPrice + item.webtoon.price}" />
                        <tr>
                            <td class="thumbnail-cell">
                                <img src="<%= request.getContextPath() %>/${item.webtoon.thumbnailPath}" 
                                     alt="${item.webtoon.koTitle} 썸네일" 
                                     class="webtoon-thumbnail">
                            </td>
                            <td>
                                <a href="<%= request.getContextPath() %>/WebtoonDetailController?webtoonId=${item.webtoon.webtoonId}">
                                    ${item.webtoon.koTitle}
                                </a>
                            </td>
                            <td>${item.webtoon.author}</td>
                            <td class="price-cell">
                                <fmt:formatNumber value="${item.webtoon.price}" pattern="#,###" /> 코인
                            </td>
                            <td>
                                <fmt:formatDate value="${item.addedDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td>
                                <%-- 삭제 버튼: 카트 ID와 웹툰 ID를 넘겨 삭제 처리 --%>
                                <form action="<%= request.getContextPath() %>/CartDeleteController" method="post" style="display: inline;">
                                <input type="hidden" name="webtoonId" value="${item.webtoon.webtoonId}">
                                <button type="submit" class="delete-btn">
                                        <c:choose>
                                            <c:when test="${lang eq 'ko'}">삭제</c:when>
                                            <c:otherwise>Delete</c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <div class="total-area">
                <p>
                    <c:choose>
                        <c:when test="${lang eq 'ko'}">총 결제 금액:</c:when>
                        <c:otherwise>Total Price:</c:otherwise>
                    </c:choose>
                    <span class="total-price">
                        <fmt:formatNumber value="${totalPrice}" pattern="#,###" /> 코인
                    </span>
                </p>
            </div>
            
            <div class="action-buttons">
                <a href="<%= request.getContextPath() %>/WebtoonListController" class="continue-shopping-btn">
                    <c:choose>
                        <c:when test="${lang eq 'ko'}">쇼핑 계속하기</c:when>
                        <c:otherwise>Continue Shopping</c:otherwise>
                    </c:choose>
                </a>
                <a href="<%= request.getContextPath() %>/PurchaseController?type=cart" class="purchase-btn">
                    <c:choose>
                        <c:when test="${lang eq 'ko'}">전체 구매하기</c:when>
                        <c:otherwise>Purchase All</c:otherwise>
                    </c:choose>
                </a>
            </div>
        </c:when>

        <%-- 2. 장바구니에 항목이 없을 경우 --%>
        <c:otherwise>
            <p class="empty-cart-message">
                <c:choose>
                    <c:when test="${lang eq 'ko'}">장바구니에 담긴 웹툰이 없습니다.</c:when>
                    <c:otherwise>Your cart is empty.</c:otherwise>
                </c:choose>
            </p>
            <div class="action-buttons">
                <a href="<%= request.getContextPath() %>/WebtoonListController" class="continue-shopping-btn">
                    <c:choose>
                        <c:when test="${lang eq 'ko'}">웹툰 보러가기</c:when>
                        <c:otherwise>Go to Webtoon List</c:otherwise>
                    </c:choose>
                </a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

</body>
</html>