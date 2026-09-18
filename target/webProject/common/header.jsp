<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="lang" value="${sessionScope.lang != null ? sessionScope.lang : 'ko'}" scope="session"/>

<header>
    <div class="logo">
        <a href="<%= request.getContextPath() %>/WebtoonListController">
            <h1>Webtoon Platter</h1>
        </a>
    </div>
    
    <nav class="main-nav">
        <ul>
            <li>
                <a href="<%= request.getContextPath() %>/WebtoonListController">
                    <c:choose>
                        <c:when test="${lang eq 'ko'}">전체 웹툰 목록</c:when>
                        <c:otherwise>All Comics</c:otherwise>
                    </c:choose>
                </a>
            </li>
            
            <c:if test="${sessionScope.adminId != null}">
                <li>
                    <a href="<%= request.getContextPath() %>/webtoon/webtoonRegForm.jsp">
                        <c:choose>
                            <c:when test="${lang eq 'ko'}">웹툰 등록</c:when>
                            <c:otherwise>Reg. Webtoon</c:otherwise>
                        </c:choose>
                    </a>
                </li>
            </c:if>
            
            <%-- 2. 사용자 전용: 마이페이지 링크 --%>
            <c:if test="${sessionScope.userId != null}">
                <li>
                    <a href="<%= request.getContextPath() %>/user/myPage.jsp">
                        <c:choose>
                            <c:when test="${lang eq 'ko'}">마이페이지</c:when>
                            <c:otherwise>My Page</c:otherwise>
                        </c:choose>
                    </a>
                </li>
                
                <li>
                    <a href="<%= request.getContextPath() %>/CartListController">
                        <c:choose>
                            <c:when test="${lang eq 'ko'}">장바구니</c:when>
                            <c:otherwise>Cart</c:otherwise>
                        </c:choose>
                    </a>
                </li>
            </c:if>
            
            <c:if test="${sessionScope.userId != null || sessionScope.adminId != null}">
                <li>
                    <c:if test="${sessionScope.userId != null}">
                        <span style="margin-right: 10px; color: #4CAF50; font-weight: bold;">
                            ${sessionScope.userId}님 (<fmt:formatNumber value="${sessionScope.loginUser.coin}" pattern="#,###" /> 코인)
                        </span>
                    </c:if>
                    
                    <a href="<%= request.getContextPath() %>/LogoutController">
                        <c:choose>
                            <c:when test="${sessionScope.userId != null}">
                                로그아웃
                            </c:when>
                            <c:when test="${sessionScope.adminId != null}">
                                ${sessionScope.adminId}님 로그아웃 (관리자)
                            </c:when>
                            <c:otherwise>Logout</c:otherwise>
                        </c:choose>
                    </a>
                </li>
            </c:if>
            
            <c:if test="${sessionScope.userId == null && sessionScope.adminId == null}">
                <li>
                    <a href="<%= request.getContextPath() %>/user/loginForm.jsp">
                        <c:choose>
                            <c:when test="${lang eq 'ko'}">로그인</c:when>
                            <c:otherwise>Login</c:otherwise>
                        </c:choose>
                    </a>
                </li>
                
                <li>
                    <a href="<%= request.getContextPath() %>/admin/adminLogin.jsp">
                        <c:choose>
                            <c:when test="${lang eq 'ko'}">관리자 로그인</c:when>
                            <c:otherwise>Admin Login</c:otherwise>
                        </c:choose>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
    
    <div class="lang-switch">
        <a href="<%= request.getContextPath() %>/LangChangeController?lang=ko" 
           class="${lang eq 'ko' ? 'active' : ''}">🇰🇷</a>
        | 
        <a href="<%= request.getContextPath() %>/LangChangeController?lang=en" 
           class="${lang eq 'en' ? 'active' : ''}">🇺🇸</a>
    </div>
</header>