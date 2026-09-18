<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${sessionScope.lang eq 'ko'}">Webtoon Platter</c:when>
            <c:otherwise>Webtoon Platter</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        .alert-message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: bold;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-fail {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .webtoon-card {
            position: relative;
            padding-bottom: 50px;
        }
        .btn-delete {
            position: absolute;
            bottom: 10px;
            right: 10px;
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <main class="container">
        <h2>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'ko'}">전체 웹툰 (${webtoonList.size()}건)</c:when>
                <c:otherwise>All Webtoons (${webtoonList.size()} items)</c:otherwise>
            </c:choose>
        </h2>
        <c:if test="${param.deleteResult != null}">
            <div class="alert-message
                <c:choose>
                    <c:when test="${param.deleteResult eq 'success'}">alert-success</c:when>
                    <c:otherwise>alert-fail</c:otherwise>
                </c:choose>
            ">
                <c:choose>
                    <c:when test="${param.deleteResult eq 'success'}">웹툰이 성공적으로 삭제되었습니다.</c:when>
                    <c:when test="${param.deleteResult eq 'fail'}">웹툰 삭제에 실패했습니다. (DB 오류 또는 존재하지 않는 ID)</c:when>
                    <c:when test="${param.deleteResult eq 'invalidId'}">잘못된 웹툰 ID입니다.</c:when>
                </c:choose>
            </div>
        </c:if>
        <div class="webtoon-grid">
            <c:choose>
                <c:when test="${not empty webtoonList}">
                    <c:forEach var="item" items="${webtoonList}">
                        <div class="webtoon-card">
                            <a href="<c:url value='/WebtoonDetailController'><c:param name='id' value='${item.webtoonId}'/></c:url>">
                                <c:set var="thumbPath" value="${item.thumbnailPath}" />
                                <c:choose>
                                    <c:when test="${empty thumbPath}">
                                        <c:set var="thumbnailUrl" value="${pageContext.request.contextPath}/images/default.png" />
                                    </c:when>
                                    <c:when test="${fn:startsWith(thumbPath, 'http://') or fn:startsWith(thumbPath, 'https://')}">
                                        <c:set var="thumbnailUrl" value="${thumbPath}" />
                                    </c:when>
                                    <c:when test="${fn:startsWith(thumbPath, pageContext.request.contextPath)}">
                                        <c:set var="thumbnailUrl" value="${thumbPath}" />
                                    </c:when>
                                    <c:when test="${fn:startsWith(thumbPath, '/')}">
                                        <c:url var="thumbnailUrl" value="${thumbPath}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="thumbnailUrl" value="/${thumbPath}" />
                                    </c:otherwise>
                                </c:choose>
                                <img src="${thumbnailUrl}" alt="${item.koTitle}" class="webtoon-thumb">
                                <div class="info">
                                    <h3>
                                        <c:choose>
                                            <c:when test="${sessionScope.lang eq 'ko'}">${item.koTitle}</c:when>
                                            <c:otherwise>${item.enTitle}</c:otherwise>
                                        </c:choose>
                                    </h3>
                                </div>
                            </a>
                            <p class="platform">Webtoon Platter | ${item.author}</p>
                            <p class="rating">⭐️ ${item.rating}</p>
                            <c:if test="${sessionScope.adminId != null}">
                                <button class="btn-delete" data-webtoon-id="${item.webtoonId}" data-title="${item.koTitle}" onclick="confirmDelete(this.dataset.webtoonId, this.dataset.title)">삭제</button>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">등록된 웹툰이 없습니다.</c:when>
                            <c:otherwise>No webtoons registered.</c:otherwise>
                        </c:choose>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
    <%@ include file="../common/footer.jsp" %>
    <script>
        function confirmDelete(webtoonId, title) {
            if (confirm("정말로 '" + title + "' 웹툰을 삭제하시겠습니까?\n(관련된 모든 리뷰도 삭제됩니다.)")) {
                window.location.href = "<c:url value='/WebtoonDeleteController?id='/>" + webtoonId;
            }
        }
    </script>
</body>
</html>