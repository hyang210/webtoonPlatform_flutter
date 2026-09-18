package webtoon.controller;

import webtoon.dao.WebtoonDAO;
import webtoon.dao.ReviewDAO; // ReviewDAO import 추가
import webtoon.vo.WebtoonVO;
import webtoon.vo.ReviewVO; // ReviewVO import 추가
import java.util.List; // List import 추가
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 웹툰 상세 정보 요청을 처리하는 서블릿.
 * WebtoonListController에서 클릭 시 호출됨 (URL: /WebtoonDetailController?id=123)
 */
@WebServlet("/WebtoonDetailController")
public class WebtoonDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 요청 파라미터에서 웹툰 ID 획득
        String webtoonIdStr = request.getParameter("id");
        int webtoonId = 0;
        try {
            webtoonId = Integer.parseInt(webtoonIdStr);
        } catch (NumberFormatException e) {
            // ID가 유효하지 않으면 목록으로 돌려보냄
            response.sendRedirect(request.getContextPath() + "/WebtoonListController");
            return;
        }

        // DAO 객체를 생성하여 DB에서 상세 정보 및 리뷰를 가져옵니다.
        WebtoonDAO dao = new WebtoonDAO(); // 웹툰 상세 정보를 위한 DAO
        ReviewDAO reviewDao = new ReviewDAO(); // 리뷰 목록 조회를 위한 DAO 객체 생성
        
        // WebtoonDAO에 getWebtoonById(int id) 메서드가 있다고 가정
        WebtoonVO webtoon = dao.getWebtoonById(webtoonId);
        
        //  ReviewDAO를 사용하여 리뷰 목록을 실제로 가져옵니다.
        List<ReviewVO> reviewList = reviewDao.getReviewsByWebtoonId(webtoonId);

        if (webtoon != null) {
            // 상세 정보를 request 객체에 저장
            request.setAttribute("webtoonDetail", webtoon);
            
            // 리뷰 목록을 request 객체에 저장하여 JSP로 전달
            request.setAttribute("reviewList", reviewList);
            
            // 데이터를 /webtoon/webtoonDetail.jsp로 포워딩 (화면 출력 위임)
            RequestDispatcher dispatcher = request.getRequestDispatcher("/webtoon/webtoonDetail.jsp");
            dispatcher.forward(request, response);
        } else {
            // 웹툰 ID가 DB에 없는 경우
            response.sendRedirect(request.getContextPath() + "/WebtoonListController?error=notFound");
        }
    }
}