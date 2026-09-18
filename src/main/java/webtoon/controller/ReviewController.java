package webtoon.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // 세션에서 사용자 ID를 가져오기 위해 필요
import webtoon.dao.ReviewDAO;
import webtoon.vo.ReviewVO;

import java.io.IOException;

// 이 경로는 리뷰 폼의 action 속성과 일치해야 합니다.
@WebServlet("/ReviewController")
public class ReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewController() {
        super();
    }

    // 리뷰 등록은 보통 POST 요청으로 처리됩니다.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        boolean success = false;
        
        // 1. 세션에서 사용자 정보(userId) 가져오기
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        
        // 2. 폼에서 웹툰 ID와 리뷰 내용 가져오기
        String webtoonIdStr = request.getParameter("webtoonId");
        String reviewContent = request.getParameter("reviewContent");
        
        // 3. 웹툰 ID 유효성 검사 및 변환
        int webtoonId = -1;
        if (webtoonIdStr != null && !webtoonIdStr.trim().isEmpty()) {
            try {
                webtoonId = Integer.parseInt(webtoonIdStr);
            } catch (NumberFormatException e) {
                System.err.println("리뷰 등록 실패: 웹툰 ID가 숫자가 아님.");
                // 유효하지 않은 요청 처리
                response.sendRedirect(request.getContextPath() + "/error.jsp"); 
                return;
            }
        }
        
        // DEBUG: 획득한 파라미터 확인
        System.out.println("DEBUG: ReviewController - 획득한 userId: " + userId);
        System.out.println("DEBUG: ReviewController - 획득한 webtoonId: " + webtoonId);
        System.out.println("DEBUG: ReviewController - 획득한 Content: " + reviewContent);
        
        // 4. 필수 데이터 및 로그인 상태 확인
        if (userId == null || webtoonId == -1 || reviewContent == null || reviewContent.trim().isEmpty()) {
            System.err.println("리뷰 등록 실패: 필수 정보(사용자ID, 웹툰ID, 내용) 누락.");
            
            // 리뷰 폼으로 돌아가며 에러 메시지를 표시하거나, 다른 처리를 할 수 있습니다.
            String redirectUrl = request.getContextPath() + "/WebtoonDetailController?webtoonId=" + (webtoonId != -1 ? webtoonId : "");
            
            // response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required review information."); // 기존 로직
            response.sendRedirect(redirectUrl + "&reviewResult=fail&reason=missing_data"); // 수정된 로직
            return;
        }
        
        try {
            // 5. ReviewVO 객체 생성 및 데이터 설정
            ReviewVO review = new ReviewVO();
            review.setWebtoonId(webtoonId);
            review.setUserId(userId);
            review.setReviewContent(reviewContent);
            
            // ReviewVO에 toString() 메서드가 구현되어 있어야 디버깅이 용이합니다.
            System.out.println("DEBUG: ReviewController - DAO 호출 직전 VO: " + review.toString());
            
            // 6. ReviewDAO를 통해 DB에 저장
            ReviewDAO dao = new ReviewDAO();
            success = dao.insertReview(review);
            
        } catch (Exception e) {
            System.err.println("리뷰 등록 중 DB 오류 또는 기타 오류 발생: " + e.getMessage());
            e.printStackTrace();
            success = false;
        }
        
        // 7. 결과에 따라 웹툰 상세 페이지로 리다이렉트
        String redirectUrl = request.getContextPath() + "/WebtoonDetailController?webtoonId=" + webtoonId;
        
        if (success) {
            System.out.println("DEBUG: ReviewController - 리뷰 등록 성공!");
            // 등록 성공 시 상세 페이지로 이동
            response.sendRedirect(redirectUrl + "&reviewResult=success");
        } else {
            System.out.println("DEBUG: ReviewController - 리뷰 등록 실패 (DAO에서 false 반환)!");
            // 등록 실패 시 상세 페이지로 이동하며 실패 메시지 전달
            response.sendRedirect(redirectUrl + "&reviewResult=fail");
        }
    }
}