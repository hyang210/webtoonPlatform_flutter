package webtoon.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webtoon.dao.CartDAO;

@WebServlet("/CartAddController")
public class CartAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CartDAO cartDAO = CartDAO.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String webtoonIdParam = request.getParameter("webtoonId");

        // 1. 로그인 확인
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
            return;
        }
        
        // 2. 웹툰 ID 유효성 검사
        if (webtoonIdParam == null || webtoonIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }
        
        int webtoonId = 0;
        try {
            webtoonId = Integer.parseInt(webtoonIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        try {
            // 3. 장바구니에 추가
            boolean success = cartDAO.insertWebtoonToCart(userId, webtoonId);
            
            if (success) { 
                // 복원: 성공 시, CartListController 서블릿으로 리다이렉트 (이 서블릿이 포워딩 에러를 유발하고 있음)
                response.sendRedirect(request.getContextPath() + "/CartListController?msg=cart_success");
            } else {
                // 추가 실패 (DB 오류 등)
                response.sendRedirect(request.getContextPath() + "/WebtoonDetailController?webtoonId=" + webtoonId + "&msg=cart_fail");
            }
            
        } catch (Exception e) {
            System.err.println("장바구니 추가 처리 중 오류 발생: " + e.getMessage());
            // 시스템 오류
            response.sendRedirect(request.getContextPath() + "/WebtoonDetailController?webtoonId=" + webtoonId + "&msg=cart_error");
        }
    }
}