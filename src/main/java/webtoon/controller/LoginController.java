package webtoon.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webtoon.dao.UserDAO;
import webtoon.vo.UserVO;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String rememberId = request.getParameter("rememberId");

        if (userId == null || userPw == null || userId.trim().isEmpty() || userPw.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=loginFail");
            return;
        }

        UserDAO dao = new UserDAO();
        UserVO loginUser = dao.loginCheck(userId, userPw);

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=loginFail");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);
        // 기존 코드에서 사용할 수 있도록 호환용 이름도 유지합니다.
        session.setAttribute("loggedInUser", loginUser);
        session.setAttribute("userId", loginUser.getUserId());
        session.setAttribute("userEmail", loginUser.getUserEmail());
        session.setAttribute("profilePath", loginUser.getProfilePath());
        session.setMaxInactiveInterval(60 * 30);

        if ("on".equals(rememberId)) {
            Cookie idCookie = new Cookie("savedUserId", loginUser.getUserId());
            idCookie.setMaxAge(60 * 60 * 24 * 7);
            idCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(idCookie);
        } else {
            Cookie idCookie = new Cookie("savedUserId", "");
            idCookie.setMaxAge(0);
            idCookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(idCookie);
        }

        response.sendRedirect(request.getContextPath() + "/WebtoonListController");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
    }
}
