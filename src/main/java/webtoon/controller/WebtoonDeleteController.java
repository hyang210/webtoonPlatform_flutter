package webtoon.controller;

import webtoon.dao.WebtoonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 웹툰 삭제 요청을 처리하는 서블릿 (관리자 전용).
 * URL: /WebtoonDeleteController?id=123
 */
@WebServlet("/WebtoonDeleteController")
public class WebtoonDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        
        // 1. 관리자 권한 확인 (세션의 'adminId'를 확인한다고 가정)
        if (session.getAttribute("adminId") == null) {
            // 관리자가 아니면 접근 거부 후 로그인 페이지로 리다이렉트
            response.sendRedirect(contextPath + "/user/loginForm.jsp?error=unauthorized");
            return;
        }

        // 2. 요청 파라미터에서 웹툰 ID 획득
        String webtoonIdStr = request.getParameter("id");
        int webtoonId = 0;
        try {
            webtoonId = Integer.parseInt(webtoonIdStr);
        } catch (NumberFormatException e) {
            // ID가 유효하지 않으면 목록으로 돌려보냄
            response.sendRedirect(contextPath + "/WebtoonListController?deleteResult=invalidId");
            return;
        }

        // 3. DAO를 통해 웹툰 삭제 처리
        WebtoonDAO dao = new WebtoonDAO();
        boolean result = dao.deleteWebtoon(webtoonId);

        // 4. 결과에 따라 리다이렉트
        if (result) {
            response.sendRedirect(contextPath + "/WebtoonListController?deleteResult=success");
        } else {
            response.sendRedirect(contextPath + "/WebtoonListController?deleteResult=fail");
        }
    }
}