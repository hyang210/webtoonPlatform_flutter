package webtoon.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webtoon.dao.ReviewDAO;
import webtoon.vo.ReviewVO;

@WebServlet("/ReviewController")
public class ReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("userId");
        String webtoonIdStr = request.getParameter("webtoonId");
        String reviewContent = request.getParameter("reviewContent");

        int webtoonId;
        try {
            webtoonId = Integer.parseInt(webtoonIdStr);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        if (userId == null || reviewContent == null || reviewContent.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()
                    + "/WebtoonDetailController?id=" + webtoonId + "&reviewResult=fail");
            return;
        }

        ReviewVO review = new ReviewVO();
        review.setWebtoonId(webtoonId);
        review.setUserId(userId);
        review.setReviewContent(reviewContent.trim());

        boolean success;
        try {
            success = new ReviewDAO().insertReview(review);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        String result = success ? "success" : "fail";
        response.sendRedirect(request.getContextPath()
                + "/WebtoonDetailController?id=" + webtoonId + "&reviewResult=" + result);
    }
}
