package webtoon.controller;

import webtoon.dao.CartDAO;
import webtoon.vo.CartVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 장바구니 목록을 조회하고 cartList.jsp로 포워딩하는 컨트롤러입니다.
 */
@WebServlet("/CartListController")
public class CartListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CartDAO cartDAO = CartDAO.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        
        // 1. 로그인 체크
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
            return;
        }
        
        try {
            // 2. 장바구니 목록 조회
            List<CartVO> cartList = cartDAO.getCartItemsByUserId(userId);
            
            // 3. Request 객체에 데이터 저장
            request.setAttribute("cartList", cartList);
            request.setAttribute("msg", request.getParameter("msg")); // 성공/실패 메시지 전달
            
            // 4. JSP로 포워딩
            // 🚨 핵심 수정: 잘못된 경로였던 "/webtoon/cartList.jsp" 대신, 
            // 🚨 일반적인 경로인 "/cart/cartList.jsp"로 포워딩하도록 경로를 수정합니다.
            request.getRequestDispatcher("/cart/cartList.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("장바구니 목록 조회 중 오류 발생: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}