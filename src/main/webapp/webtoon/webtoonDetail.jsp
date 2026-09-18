<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${sessionScope.lang eq 'ko'}">${webtoonDetail.koTitle}</c:when>
            <c:otherwise>${webtoonDetail.enTitle}</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <style>
        .detail-header { display: flex; align-items: flex-start; gap: 30px; margin-bottom: 30px; }
        .detail-thumb { width: 250px; height: auto; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); }
        .detail-info h1 { margin-top: 0; font-size: 2.2em; }
        .detail-meta p { margin: 5px 0; font-size: 1.1em; }
        .webtoon-actions {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            display: flex;
            gap: 10px;
            align-items: center;
            background-color: #f9f9f9;
        }
        .price-display {
            font-size: 1.3em;
            color: #ff5722;
            font-weight: bold;
            margin-right: auto;
        }
        .btn-cart, .btn-buy {
            padding: 10px 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1em;
            font-weight: bold;
            transition: background-color 0.2s;
        }
        .btn-cart {
            background-color: #6c757d;
            color: white;
        }
        .btn-cart:hover { background-color: #5a6268; }
        .btn-buy {
            background-color: #28a745;
            color: white;
        }
        .btn-buy:hover { background-color: #1e7e34; }
        .alert-message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: bold;
        }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-fail { background-color: #f8d7da; color: #721c24; border: 1px solid #c3e6cb; }
        .review-section { margin-top: 40px; padding-top: 20px; border-top: 1px solid #eee; }
        .review-form textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; resize: vertical; margin-bottom: 10px; }
        .review-list { list-style: none; padding: 0; }
        .review-item { border-bottom: 1px dashed #eee; padding: 15px 0; }
        .review-item strong { display: block; margin-bottom: 5px; }
    </style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <main class="container">
        <c:if test="${param.reviewResult != null}">
            <div class="alert-message
                <c:choose>
                    <c:when test="${param.reviewResult eq 'success'}">alert-success</c:when>
                    <c:otherwise>alert-fail</c:otherwise>
                </c:choose>
            ">
                <c:choose>
                    <c:when test="${param.reviewResult eq 'success' && sessionScope.lang eq 'ko'}">리뷰가 성공적으로 등록되었습니다.</c:when>
                    <c:when test="${param.reviewResult eq 'success' && sessionScope.lang eq 'en'}">Review posted successfully.</c:when>
                    <c:when test="${param.reviewResult eq 'fail' && sessionScope.lang eq 'ko'}">리뷰 등록에 실패했습니다. (필수 정보 누락 또는 DB 오류)</c:when>
                    <c:otherwise>Failed to post review. (Missing info or DB error)</c:otherwise>
                </c:choose>
            </div>
        </c:if>
        <c:if test="${param.cartResult != null}">
            <div class="alert-message
                <c:choose>
                    <c:when test="${param.cartResult eq 'success'}">alert-success</c:when>
                    <c:otherwise>alert-fail</c:otherwise>
                </c:choose>
            ">
                <c:choose>
                    <c:when test="${param.cartResult eq 'success' && sessionScope.lang eq 'ko'}">
                        장바구니에 웹툰이 성공적으로 추가되었습니다!
                    </c:when>
                    <c:when test="${param.cartResult eq 'success' && sessionScope.lang eq 'en'}">
                        Webtoon added to cart successfully!
                    </c:when>
                    <c:when test="${param.cartResult eq 'fail' && sessionScope.lang eq 'ko'}">
                        장바구니 추가를 실패했습니다. (이미 담겨있거나 DB 오류)
                    </c:when>
                    <c:otherwise>
                        Failed to add webtoon to cart. (Already in cart or DB error)
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
        <div class="detail-header">
            <c:url var="thumbnailUrl" value="/${webtoonDetail.thumbnailPath}" />
            <img src="${thumbnailUrl}" alt="${webtoonDetail.koTitle} Thumbnail" class="detail-thumb">
            <div class="detail-info">
                <h1>
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">${webtoonDetail.koTitle}</c:when>
                        <c:otherwise>${webtoonDetail.enTitle}</c:otherwise>
                    </c:choose>
                </h1>
                <div class="detail-meta">
                    <p><strong>
                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">작가:</c:when><c:otherwise>Author:</c:otherwise></c:choose>
                    </strong> ${webtoonDetail.author}</p>
                    <p><strong>
                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">플랫폼:</c:when><c:otherwise>Platform:</c:otherwise></c:choose>
                    </strong> Webtoon Platter</p>
                    <p>
                        <strong>
                            <c:choose>
                                <c:when test="${sessionScope.lang eq 'ko'}">장르:</c:when>
                                <c:otherwise>Genre:</c:otherwise>
                            </c:choose>
                        </strong>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">
                                ${webtoonDetail.genre}
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${webtoonDetail.genre eq '판타지'}">Fantasy</c:when>
                                    <c:when test="${webtoonDetail.genre eq '액션'}">Action</c:when>
                                    <c:otherwise>${webtoonDetail.genre}</c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>
                        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">평점:</c:when><c:otherwise>Rating:</c:otherwise></c:choose>
                    </strong> ⭐️ ${webtoonDetail.rating}</p>
                </div>
                <div class="webtoon-actions">
                    <span class="price-display">
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">가격: ${webtoonDetail.price} 코인</c:when>
                            <c:otherwise>Price: ${webtoonDetail.price} Coins</c:otherwise>
                        </c:choose>
                    </span>
                    <c:if test="${sessionScope.userId != null}">
                        <form action='<c:url value="/CartAddController"/>' method="post" style="display: inline;">
                            <input type="hidden" name="webtoonId" value="${webtoonDetail.webtoonId}">
                            <button type="submit" class="btn-cart">
                                <c:choose>
                                    <c:when test="${sessionScope.lang eq 'ko'}">장바구니 담기</c:when>
                                    <c:otherwise>Add to Cart</c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                        <button class="btn-buy"
                                onclick="alert('결제 기능은 아직 구현되지 않았습니다.');">
                            <c:choose>
                                <c:when test="${sessionScope.lang eq 'ko'}">바로 구매</c:when>
                                <c:otherwise>Buy Now</c:otherwise>
                            </c:choose>
                        </button>
                    </c:if>
                    <c:if test="${sessionScope.userId == null}">
                        <p style="color: #6c757d; font-style: italic;">
                            <c:choose>
                                <c:when test="${sessionScope.lang eq 'ko'}">로그인 후 구매 및 장바구니 기능을 이용할 수 있습니다.</c:when>
                                <c:otherwise>Login to use Buy and Cart features.</c:otherwise>
                            </c:choose>
                        </p>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="review-section">
            <h3>
                <c:choose>
                    <c:when test="${sessionScope.lang eq 'ko'}">리뷰 작성</c:when>
                    <c:otherwise>Write a Review</c:otherwise>
                </c:choose>
            </h3>
            <c:if test="${sessionScope.userId != null}">
                <form action="<c:url value='/ReviewController'/>" method="post" class="review-form">
                    <input type="hidden" name="webtoonId" value="${webtoonDetail.webtoonId}">
                    <textarea name="reviewContent" rows="4"
                              placeholder="<c:choose><c:when test="${sessionScope.lang eq 'ko'}">솔직한 리뷰를 남겨주세요.</c:when><c:otherwise>Leave your honest review.</c:otherwise></c:choose>"
                              required></textarea>
                    <button type="submit" class="btn-submit" style="width: auto; padding: 10px 20px; margin-top: 0;">
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">리뷰 등록</c:when>
                            <c:otherwise>Post Review</c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </c:if>
            <c:if test="${sessionScope.userId == null}">
                <p style="color: gray;">
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">리뷰를 작성하려면 <a href='<c:url value="/user/loginForm.jsp"/>'>로그인</a>이 필요합니다.</c:when>
                        <c:otherwise><a href='<c:url value="/user/loginForm.jsp"/>'>Login</a> to write a review.</c:otherwise>
                    </c:choose>
                </p>
            </c:if>
            <h3>
                <c:choose>
                    <c:when test="${sessionScope.lang eq 'ko'}">사용자 리뷰</c:when>
                    <c:otherwise>User Reviews</c:otherwise>
                </c:choose>
            </h3>
            <ul class="review-list">
                <c:choose>
                    <c:when test="${not empty reviewList}">
                        <c:forEach var="review" items="${reviewList}">
                            <li class="review-item">
                                <strong>${review.userId}</strong>
                                <p>${review.reviewContent}</p>
                                <small style="color: #999;">${review.regDate}</small>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <c:choose>
                                <c:when test="${sessionScope.lang eq 'ko'}">아직 등록된 리뷰가 없습니다.</c:when>
                                <c:otherwise>No reviews yet.</c:otherwise>
                            </c:choose>
                        </p>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </main>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>