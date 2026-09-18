<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebTune Platter - 
        <c:choose><c:when test="${sessionScope.lang eq 'ko'}">회원가입</c:when><c:otherwise>Register</c:otherwise></c:choose>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <%-- 유효성 검사를 위해 validation.js를 include --%>
    <script src="<%= request.getContextPath() %>/js/validation.js"></script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">회원가입</c:when><c:otherwise>User Registration</c:otherwise></c:choose>
        </h2>
        
        <%-- RegisterController로 POST 요청. 클라이언트 유효성 검사 함수 호출 --%>
        <form action="<%= request.getContextPath() %>/RegisterController" 
              method="post" 
              class="register-form"
              onsubmit="return validateForm();">
            
            <label for="userId">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">아이디 (4~12자, 영문/숫자):</c:when><c:otherwise>ID (4~12 chars, letters/nums):</c:otherwise></c:choose>
            </label>
            <input type="text" id="userId" name="userId" required><br>
            <p id="idMessage" style="color: red; font-size: 0.9em; margin-top: 5px;"></p>

            <label for="userPw">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">비밀번호 (최소 8자):</c:when><c:otherwise>Password (min 8 chars):</c:otherwise></c:choose>
            </label>
            <input type="password" id="userPw" name="userPw" required><br>

            <label for="confirmPw">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">비밀번호 확인:</c:when><c:otherwise>Confirm Password:</c:otherwise></c:choose>
            </label>
            <input type="password" id="confirmPw" name="confirmPw" required><br>
            <p id="pwMatchMessage" style="color: red; font-size: 0.9em; margin-top: 5px;"></p>

            <label for="name">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">이름:</c:when><c:otherwise>Name:</c:otherwise></c:choose>
            </label>
            <input type="text" id="name" name="name" required><br>

            <label for="email">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">이메일:</c:when><c:otherwise>Email:</c:otherwise></c:choose>
            </label>
            <input type="email" id="email" name="email" required><br>
            
            <button type="submit" class="btn-submit">
                <c:choose><c:when test="${sessionScope.lang eq 'ko'}">가입하기</c:when><c:otherwise>Register</c:otherwise></c:choose>
            </button>
            
            <%-- 오류 메시지 출력 --%>
            <c:if test="${param.error eq 'duplicateId'}">
                <p style="color: red; margin-top: 15px; text-align: center;">
                    <c:choose><c:when test="${sessionScope.lang eq 'ko'}">이미 사용 중인 아이디입니다.</c:when><c:otherwise>ID is already taken.</c:otherwise></c:choose>
                </p>
            </c:if>
        </form>
    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>