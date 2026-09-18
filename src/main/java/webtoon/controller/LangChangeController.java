package webtoon.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet;      
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;        

@WebServlet("/LangChangeController")
public class LangChangeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 요청 파라미터에서 새로운 언어 값 (lang)을 획득
        String newLang = request.getParameter("lang");
        
        // 2. 유효성 검사 (ko 또는 en 인지 확인)
        if (newLang != null && (newLang.equals("ko") || newLang.equals("en"))) {
            
            // 3. 세션 객체 획득
            HttpSession session = request.getSession();
            
            // 4. 세션에 언어 정보를 저장 (다국어 처리에 사용)
            session.setAttribute("lang", newLang);
        }
        
        // 5. 사용자가 언어 변경 요청을 보낸 이전 페이지 URL 획득
        // 이 URL을 사용하여 사용자를 원래 페이지로 리다이렉트합니다.
        String referer = request.getHeader("referer");
        
        if (referer != null) {
            // 이전 페이지로 리다이렉트
            response.sendRedirect(referer);
        } else {
            // 이전 페이지 정보가 없을 경우, 기본 페이지(WebtoonListController)로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}