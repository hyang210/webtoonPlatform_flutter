package webtoon.controller;

import webtoon.dao.UserDAO;
import webtoon.vo.UserVO;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie; // <-- Jakarta EE
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // <-- Jakarta EE

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 1. 폼 데이터 획득
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String rememberId = request.getParameter("rememberId"); // 체크박스 (null 또는 "on")

        UserDAO dao = new UserDAO();
        
        // 2. DB에서 사용자 인증 및 정보 획득 (DAO 호출)
        // UserDAO에 loginCheck(String id, String pw) 메서드가 있다고 가정
        UserVO loginUser = dao.loginCheck(userId, userPw); 

        String url = "";
        
        if (loginUser != null) {
            // 3. 로그인 성공 시 처리
            
            // 3-1. 세션 설정 (로그인 상태 유지 및 개인화 데이터 저장)
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", loginUser); // 사용자 전체 정보 저장
            session.setAttribute("userId", userId);         // ID만 별도 저장 (편의상)
            session.setMaxInactiveInterval(60 * 30);        // 세션 유지 시간 30분 설정

            // 3-2. 쿠키 처리 (아이디 저장)
            if ("on".equals(rememberId)) {
                // '아이디 저장' 체크 시, 쿠키에 ID 저장 (7일 유지)
                Cookie idCookie = new Cookie("savedUserId", userId);
                idCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 (초 단위)
                response.addCookie(idCookie);
            } else {
                // 체크 해제 시, 기존 쿠키 삭제
                Cookie idCookie = new Cookie("savedUserId", "");
                idCookie.setMaxAge(0); // 유효시간 0초 설정으로 즉시 삭제
                response.addCookie(idCookie);
            }
            
            // 로그인 성공 후 메인 페이지로 리다이렉트
            url = request.getContextPath() + "/WebtoonListController"; 
            
        } else {
            // 4. 로그인 실패 시 처리
            
            // 로그인 폼으로 돌아가면서 에러 메시지 전달
            url = request.getContextPath() + "/user/loginForm.jsp?error=loginFail";
        }

        // 5. 페이지 이동
        response.sendRedirect(url);
    }
    
    // GET 요청 시 로그인 폼으로 이동하도록 처리
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
    }
}