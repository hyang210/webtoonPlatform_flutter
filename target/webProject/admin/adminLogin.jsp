<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webtoon Platter - 
        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">관리자 로그인</c:when><c:otherwise>Administrator Login</c:otherwise></c:choose>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">관리자 로그인</c:when><c:otherwise>Administrator Login</c:otherwise></c:choose>
        </h2>
        
        <form action="<%= request.getContextPath() %>/AdminLoginController" method="post" class="login-form">
            
            <label for="adminId">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">관리자 아이디:</c:when><c:otherwise>Admin ID:</c:otherwise></c:choose>
            </label>
            <input type="text" id="adminId" name="adminId" required><br>

            <label for="adminPw">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">비밀번호:</c:when><c:otherwise>Password:</c:otherwise></c:choose>
            </label>
            <input type="password" id="adminPw" name="adminPw" required><br>
            
            <button type="submit" class="btn-submit">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">로그인</c:when><c:otherwise>Login</c:otherwise></c:choose>
            </button>
            
            <c:if test="${param.error eq 'adminFail'}">
                <p style="color: red; margin-top: 15px;">
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">아이디 또는 비밀번호가 일치하지 않습니다.</c:when>
                        <c:otherwise>Invalid Admin ID or Password.</c:otherwise>
                    </c:choose>
                </p>
            </c:if>
        </form>
    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>