package webtoon.controller;

import webtoon.dao.UserDAO;
import webtoon.vo.UserVO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 사용자 회원가입 요청을 처리하는 서블릿.
 * 폼 데이터를 받아 UserDAO를 통해 DB에 저장합니다.
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 1. 폼 데이터 획득
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // 2. VO 객체에 데이터 설정
        UserVO newUser = new UserVO();
        newUser.setUserId(userId);
        newUser.setUserPw(userPw); // 실제 환경에서는 암호화 필수
        newUser.setUserName(name);
        newUser.setUserEmail(email);

        UserDAO dao = new UserDAO();
        boolean result = false;
        String url = "";

        try {
            // 3. DAO를 이용하여 DB에 사용자 정보 저장
            result = dao.insertUser(newUser); // UserDAO에 insertUser(UserVO) 메서드가 있어야 함
            
            if (result) {
                // 4. 회원가입 성공 시 로그인 폼으로 리다이렉트
                url = request.getContextPath() + "/user/loginForm.jsp?msg=registerSuccess";
            } else {
                // 5. 실패 시 회원가입 폼으로 돌아가 에러 메시지 전달
                url = request.getContextPath() + "/user/registerForm.jsp?error=dbFail";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            url = request.getContextPath() + "/user/registerForm.jsp?error=systemError";
        }

        // 6. 페이지 이동
        response.sendRedirect(url);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GET 요청은 허용하지 않고 POST만 허용하도록 할 수 있지만, 여기서는 폼으로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/user/registerForm.jsp");
    }
}