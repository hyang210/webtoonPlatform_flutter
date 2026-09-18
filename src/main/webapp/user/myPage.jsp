<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${sessionScope.lang eq 'ko'}">마이페이지</c:when>
            <c:otherwise>My Page</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <style>
        .profile-section {
            display: flex;
            align-items: flex-start;
            gap: 40px;
        }
        .profile-info h3 { margin-top: 0; }
        .profile-img-container {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            overflow: hidden;
            border: 2px solid #ccc;
        }
        .profile-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .profile-placeholder {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 64px;
            background: #f3f3f3;
        }
        .profile-form {
            max-width: 400px;
            margin-top: 20px;
        }
    </style>
</head>
<body>

    <c:if test="${sessionScope.userId == null}">
        <c:url var="loginUrl" value="/user/loginForm.jsp">
            <c:param name="error" value="needLogin"/>
        </c:url>
        <c:redirect url="${loginUrl}" />
    </c:if>

    <%@ include file="../common/header.jsp" %>
    
    <main class="container">
        <h2>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'ko'}">${sessionScope.userId}님의 마이페이지</c:when>
                <c:otherwise>${sessionScope.userId}'s My Page</c:otherwise>
            </c:choose>
        </h2>
        
        <div class="profile-section">
            <div class="profile-info">
                <h3>
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">환영합니다, ${sessionScope.userId}님!</c:when>
                        <c:otherwise>Welcome, ${sessionScope.userId}!</c:otherwise>
                    </c:choose>
                </h3>
                
                <p>
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ko'}">이메일:</c:when>
                        <c:otherwise>Email:</c:otherwise>
                    </c:choose>
                    ${sessionScope.loginUser.userEmail}
                </p>
                
                <p style="margin-top: 20px;">
                    <a href="#" onclick="alert('회원 정보 수정 기능 구현')">
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">내 정보 수정</c:when>
                            <c:otherwise>Edit Profile</c:otherwise>
                        </c:choose>
                    </a>
                </p>
            </div>
            
            <div>
                <div class="profile-img-container">
                    <c:choose>
                        <c:when test="${not empty sessionScope.loginUser.profilePath}">
                            <c:url var="profileImageUrl" value="/${sessionScope.loginUser.profilePath}" />
                            <img src="${profileImageUrl}" class="profile-img" alt="Profile Image">
                        </c:when>
                        <c:otherwise>
                            <div class="profile-placeholder" aria-label="No profile image">👤</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="profile-form">
                    <p>
                        <c:choose>
                            <c:when test="${sessionScope.lang eq 'ko'}">프로필 이미지 변경 (파일 업로드)</c:when>
                            <c:otherwise>Change Profile Image (File Upload)</c:otherwise>
                        </c:choose>
                    </p>
                    
                    <form action="<c:url value='/ProfileUpController'/>" method="post" enctype="multipart/form-data">
                        
                        <input type="file" name="profileFile" required accept="image/*">
                        
                        <button type="submit" class="btn-submit" style="margin-top: 10px; width: auto; padding: 5px 15px;">
                            <c:choose>
                                <c:when test="${sessionScope.lang eq 'ko'}">업로드</c:when>
                                <c:otherwise>Upload</c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                    
                    <c:if test="${param.uploadResult eq 'success'}">
                        <p style="color: blue;">
                            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">업로드가 완료되었습니다.</c:when><c:otherwise>Upload successful.</c:otherwise></c:choose>
                        </p>
                    </c:if>
                    <c:if test="${param.uploadResult eq 'fail'}">
                        <p style="color: red;">
                            <c:choose><c:when test="${sessionScope.lang eq 'ko'}">파일 업로드에 실패했습니다.</c:when><c:otherwise>File upload failed.</c:otherwise></c:choose>
                        </p>
                    </c:if>
                </div>
            </div>
        </div>
    </main>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>