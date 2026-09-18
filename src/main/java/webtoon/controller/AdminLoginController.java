package webtoon.controller;

import webtoon.dao.AdminDAO;
import webtoon.vo.AdminVO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Jakarta EE

/**
 * 관리자 로그인 요청을 처리하는 서블릿.
 * AdminDAO를 통해 인증하고 세션을 설정합니다.
 */
@WebServlet("/AdminLoginController")
public class AdminLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 1. 폼 데이터 획득
        String adminId = request.getParameter("adminId");
        String adminPw = request.getParameter("adminPw");
        
        AdminDAO dao = new AdminDAO();
        
        // 2. DB에서 관리자 인증 및 정보 획득
        AdminVO loginAdmin = dao.adminLoginCheck(adminId, adminPw); 

        String url = "";
        
        if (loginAdmin != null) {
            // 3. 로그인 성공 시 처리
            
            // 3-1. 세션 설정 (관리자 권한 부여)
            HttpSession session = request.getSession();
            session.setAttribute("adminId", adminId);         // 관리자 ID 세션 저장
            session.setAttribute("isAdmin", true);            // 관리자 여부 플래그
            session.setMaxInactiveInterval(60 * 60);        // 세션 유지 시간 1시간 설정

            // 3-2. 관리자 웹툰 등록 페이지로 리다이렉트
            url = request.getContextPath() + "/admin/webtoonNewRegForm.jsp"; 
            
        } else {
            // 4. 로그인 실패 시 처리
            
            // 관리자 로그인 폼으로 돌아가면서 에러 메시지 전달
            url = request.getContextPath() + "/admin/adminLogin.jsp?error=adminFail";
        }

        // 5. 페이지 이동
        response.sendRedirect(url);
    }
    
    // GET 요청 시 관리자 로그인 폼으로 이동
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/admin/adminLogin.jsp");
    }
}