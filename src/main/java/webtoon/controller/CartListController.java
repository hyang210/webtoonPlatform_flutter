package webtoon.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webtoon.dao.CartDAO;
import webtoon.vo.CartVO;

@WebServlet("/CartListController")
public class CartListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO = CartDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=needLogin");
            return;
        }

        List<CartVO> cartList = cartDAO.getCartItemsByUserId(userId);
        request.setAttribute("cartList", cartList);
        request.setAttribute("cartLoaded", true);
        request.setAttribute("result", request.getParameter("result"));

        request.getRequestDispatcher("/cart/cartList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
