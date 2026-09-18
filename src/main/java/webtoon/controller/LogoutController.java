package webtoon.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Jakarta EE

/**
 * 사용자 로그아웃 요청을 처리하는 서블릿.
 * 세션을 무효화하여 로그인 상태를 해제합니다.
 */
@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 세션 객체 획득 (세션이 있으면 반환, 없으면 null 반환)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 2. 세션 무효화 (로그아웃 처리의 핵심)
            session.invalidate();
        }
        
        // 3. 로그아웃 후 메인 페이지로 리다이렉트
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/WebtoonListController?msg=logout");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}