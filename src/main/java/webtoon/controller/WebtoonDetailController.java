package webtoon.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webtoon.dao.ReviewDAO;
import webtoon.dao.WebtoonDAO;
import webtoon.vo.ReviewVO;
import webtoon.vo.WebtoonVO;

@WebServlet("/WebtoonDetailController")
public class WebtoonDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String webtoonIdStr = request.getParameter("id");
        if (webtoonIdStr == null || webtoonIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        int webtoonId;
        try {
            webtoonId = Integer.parseInt(webtoonIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        WebtoonDAO webtoonDAO = new WebtoonDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        WebtoonVO webtoon = webtoonDAO.getWebtoonById(webtoonId);
        if (webtoon == null) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController?error=notFound");
            return;
        }

        List<ReviewVO> reviewList = reviewDAO.getReviewsByWebtoonId(webtoonId);

        request.setAttribute("webtoonDetail", webtoon);
        request.setAttribute("reviewList", reviewList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/webtoon/webtoonDetail.jsp");
        dispatcher.forward(request, response);
    }
}
