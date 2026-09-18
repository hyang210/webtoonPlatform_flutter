package webtoon.controller;

import webtoon.dao.CartDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 장바구니 항목을 삭제하는 컨트롤러
 */
@WebServlet("/CartDeleteController") 
public class CartDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String webtoonIdStr = request.getParameter("webtoonId");
        
        // 🚨 디버깅: 파라미터 값 콘솔 출력
        System.out.println("--- CartDeleteController DEBUG ---");
        System.out.println("userId: " + userId);
        System.out.println("webtoonIdStr: " + webtoonIdStr);
        System.out.println("----------------------------------");
        
        // 1. 필수 파라미터 체크 및 로그인 체크
        if (userId == null || webtoonIdStr == null || webtoonIdStr.isEmpty()) {
            System.err.println("DEBUG: userId 또는 webtoonIdStr이 유효하지 않아 삭제 실패 (파라미터 오류)");
            response.sendRedirect(request.getContextPath() + "/CartListController?result=delete_fail");
            return;
        }

        int webtoonId = 0; // 초기화
        try {
            webtoonId = Integer.parseInt(webtoonIdStr);
        } catch (NumberFormatException e) {
            System.err.println("DEBUG: webtoonIdStr (" + webtoonIdStr + ")을 숫자로 변환 실패.");
            // 숫자 변환 실패 시 (삭제 실패)
            response.sendRedirect(request.getContextPath() + "/CartListController?result=delete_fail");
            return;
        }

        // 2. DAO를 통한 삭제
        CartDAO cartDAO = CartDAO.getInstance(); 
        boolean success = false;
        
        try {
             success = cartDAO.deleteCartItem(userId, webtoonId);
        } catch (Exception e) {
             System.err.println("DEBUG: CartDAO 호출 중 예외 발생: " + e.getMessage());
             e.printStackTrace();
        }

        // 🚨 디버깅: 최종 DAO 실행 결과 출력
        System.out.println("DEBUG: Final delete success result: " + success);

        // 3. 결과 리다이렉트 (장바구니 목록 페이지로 돌아가며 결과 메시지 전달)
        if (success) {
            response.sendRedirect(request.getContextPath() + "/CartListController?result=delete_success");
        } else {
            response.sendRedirect(request.getContextPath() + "/CartListController?result=delete_fail");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DELETE 요청도 doGet으로 처리하도록 합니다.
        doGet(request, response);
    }
}