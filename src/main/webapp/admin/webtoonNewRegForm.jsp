<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webtoon Platter - 
        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 등록</c:when><c:otherwise>Webtoon Registration</c:otherwise></c:choose>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <c:if test="${sessionScope.adminId == null}">
        <c:redirect url="<c:url value='/admin/adminLogin.jsp'><c:param name='error' value='needAdminLogin'/></c:url>" />
    </c:if>

    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 신규 등록</c:when><c:otherwise>Register New Webtoon</c:otherwise></c:choose>
        </h2>
        
        <form action="<%= request.getContextPath() %>/WebtoonRegController" 
              method="post" 
              class="webtoon-form" 
              enctype="multipart/form-data">
            
            <label for="koTitle">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 제목 (국문):</c:when><c:otherwise>Webtoon Title (KR):</c:otherwise></c:choose>
            </label>
            <input type="text" id="koTitle" name="koTitle" required><br>

            <label for="enTitle">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 제목 (영문):</c:when><c:otherwise>Webtoon Title (EN):</c:otherwise></c:choose>
            </label>
            <input type="text" id="enTitle" name="enTitle" required><br>

            <label for="platform">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">플랫폼:</c:when><c:otherwise>Platform:</c:otherwise></c:choose>
            </label>
            <select id="platform" name="platform" required>
                <option value="">
                    <c:choose><c:when test="${sessionScope.lang eq 'ko'}">선택</c:when><c:otherwise>Select</c:otherwise></c:choose>
                </option>
                <option value="Naver">Webtoon Platter</option>
            </select><br>

            <label for="author">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">작가:</c:when><c:otherwise>Author:</c:otherwise></c:choose>
            </label>
            <input type="text" id="author" name="author" required><br>
            
            <label for="genre">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">장르:</c:when><c:otherwise>Genre:</c:otherwise></c:choose>
            </label>
            <input type="text" id="genre" name="genre" required><br>

            <label for="thumbnailFile">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">썸네일 이미지:</c:when><c:otherwise>Thumbnail Image:</c:otherwise></c:choose>
            </label>
            <input type="file" id="thumbnailFile" name="thumbnailFile" required accept="image/*"><br>
            
            <button type="submit" class="btn-submit">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">등록</c:when><c:otherwise>Register</c:otherwise></c:choose>
            </button>
            
            <c:if test="${param.result eq 'success'}">
                <p style="color: blue; margin-top: 15px; text-align: center;">
                    <c:choose><c:when test="${sessionScope.lang eq 'ko'}">웹툰 등록이 완료되었습니다.</c:when><c:otherwise>Webtoon registration successful.</c:otherwise></c:choose>
                </p>
            </c:if>
            <c:if test="${param.result eq 'fail'}">
                <p style="color: red; margin-top: 15px; text-align: center;">
                    <c:choose><c:when test="${sessionScope.lang eq 'ko'}">등록에 실패했습니다. (DB 또는 파일 오류)</c:when><c:otherwise>Registration failed. (DB or File Error)</c:otherwise></c:choose>
                </p>
            </c:if>
        </form>
    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>