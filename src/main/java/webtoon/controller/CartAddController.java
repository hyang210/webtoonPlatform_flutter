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

    private final CartDAO cartDAO = CartDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("userId");
        String webtoonIdParam = request.getParameter("webtoonId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=needLogin");
            return;
        }

        int webtoonId;
        try {
            webtoonId = Integer.parseInt(webtoonIdParam);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        boolean success = cartDAO.insertWebtoonToCart(userId, webtoonId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/CartListController?result=add_success");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/WebtoonDetailController?id=" + webtoonId + "&cartResult=fail");
        }
    }
}
