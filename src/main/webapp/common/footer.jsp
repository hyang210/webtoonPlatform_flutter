<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<footer>
    <p>Webtoon Platter | &copy; All Rights Reserved.</p>
    <p>
        <c:choose>
            <c:when test="${sessionScope.lang eq 'ko'}">문의 : 류채현 | rch1879@gmail.com</c:when>
            <c:otherwise>Contact : RYU CHAI HYUN | rch1879@gmail.com</c:otherwise>
        </c:choose>
    </p>
</footer>