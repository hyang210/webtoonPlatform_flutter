<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% 
    String savedId = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("savedId".equals(c.getName())) {
                savedId = c.getValue();
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Webtoon Platter - 
        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">로그인</c:when><c:otherwise>Login</c:otherwise></c:choose>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">사용자 로그인</c:when><c:otherwise>User Login</c:otherwise></c:choose>
        </h2>
        
        <form action="<%= request.getContextPath() %>/LoginController" method="post" class="login-form">
            
            <label for="userId">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">아이디:</c:when><c:otherwise>ID:</c:otherwise></c:choose>
            </label>
            <input type="text" id="userId" name="userId" value="<%= savedId %>" required><br>

            <label for="userPw">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">비밀번호:</c:when><c:otherwise>Password:</c:otherwise></c:choose>
            </label>
            <input type="password" id="userPw" name="userPw" required><br>
            
            <div class="options">
                <input type="checkbox" id="rememberId" name="rememberId" 
                       <%= savedId.isEmpty() ? "" : "checked" %>> 
                <label for="rememberId">
                    <c:choose><c:when test="${sessionScope.lang eq 'ko'}">아이디 저장</c:when><c:otherwise>Remember ID</c:otherwise></c:choose>
                </label>
            </div>
            
            <button type="submit" class="btn-submit">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">로그인</c:when><c:otherwise>Login</c:otherwise></c:choose>
            </button>
            
            <p style="text-align: center; margin-top: 15px;">
                <c:choose>
                    <c:when test="${sessionScope.lang eq 'ko'}">
                        계정이 없으신가요? <a href='<%= request.getContextPath() %>/user/registerForm.jsp'>회원가입</a>
                    </c:when>
                    <c:otherwise>
                        Don't have an account? <a href='<%= request.getContextPath() %>/user/registerForm.jsp'>Register</a>
                    </c:otherwise>
                </c:choose>
            </p>
            
            <%-- 로그인 실패/필요 에러 메시지 출력 --%>
            <c:if test="${param.error eq 'loginFail'}">
                <p style="color: red; margin-top: 15px; text-align: center;">
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">아이디 또는 비밀번호가 일치하지 않습니다.</c:when>
                        <c:otherwise>Invalid ID or Password.</c:otherwise>
                    </c:choose>
                </p>
            </c:if>
            <c:if test="${param.error eq 'needLogin'}">
                <p style="color: red; margin-top: 15px; text-align: center;">
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">로그인이 필요합니다.</c:when>
                        <c:otherwise>Login is required.</c:otherwise>
                    </c:choose>
                </p>
            </c:if>
        </form>
    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>