package webtoon.controller;

import webtoon.dao.CartDAO;
import webtoon.dao.UserDAO; 
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
 * 장바구니 전체 구매를 처리하는 컨트롤러 (현재는 더미 로직)
 * 실제 구현에서는 잔액 차감, 구매 내역 기록, 장바구니 비우기 로직이 필요함.
 */
@WebServlet("/PurchaseController")
public class PurchaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // CartDAO는 싱글톤 패턴을 따르므로 getInstance() 사용
    private CartDAO cartDAO = CartDAO.getInstance(); 
    
    // 🚨 UserDAO에 getInstance() 메서드가 없어서 에러가 발생합니다.
    // 🚨 UserDAO가 싱글톤 패턴이 아니라고 가정하고 new UserDAO()로 변경합니다.
    // 🚨 만약 UserDAO에 싱글톤을 적용하려면, UserDAO.java 파일을 수정해야 합니다.
    private UserDAO userDAO = new UserDAO(); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        
        // 1. 로그인 체크
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
            return;
        }
        
        // 2. 장바구니 조회 및 총액 계산
        List<CartVO> cartList = cartDAO.getCartItemsByUserId(userId);
        
        if (cartList.isEmpty()) {
            // 실패 메시지 코드 변경: result=empty_cart
            response.sendRedirect(request.getContextPath() + "/CartListController?result=empty_cart");
            return;
        }
        
        int totalPrice = 0;
        for (CartVO item : cartList) {
            if (item.getWebtoon() != null) {
                // webtoon/vo/WebtoonVO.java에 price 필드가 없으므로,
                // 임시로 100으로 가정합니다. 실제 구현 시 WebtoonVO와 DB 스키마에 price 필드를 추가해야 합니다.
                totalPrice += 100; // 임시 가격 (실제 WebtoonVO에 price 필드 추가 필요)
            }
        }
        
        // 3. 🚨 구매 로직 (더미) 🚨
        boolean purchaseSuccess = false;
        
        // TODO: UserVO와 UserDAO에 `coin` 필드와 잔액 업데이트 메서드 구현 필요
        
        // 임시: 잔액이 1000 코인 이상이라고 가정하고 구매 처리 (테스트용)
        if (totalPrice <= 1000 && totalPrice > 0) { 
            // 🚨 실제 UserDAO.getUserCoin(userId) 로직으로 변경 필요
            purchaseSuccess = true;
        } else {
             // 잔액 부족 또는 총액이 0인 경우
            purchaseSuccess = false; 
        }

        if (purchaseSuccess) {
            // 4. 구매 성공 시: 장바구니 비우기
            boolean emptyCartSuccess = true; 
            for (CartVO item : cartList) {
                // 🚨 deleteCartItem(userId, webtoonId) 메서드를 호출하여 삭제
                if (!cartDAO.deleteCartItem(userId, item.getWebtoonId())) {
                    emptyCartSuccess = false;
                    // 실제로는 여기서 롤백 로직이 필요할 수 있습니다.
                    break; 
                }
            }
            
            // 5. 결과 리다이렉트
            if (emptyCartSuccess) {
                // 잔액 차감 및 구매 내역 기록 성공 시
                response.sendRedirect(request.getContextPath() + "/CartListController?result=purchase_success");
            } else {
                // 장바구니 비우기 실패 시
                response.sendRedirect(request.getContextPath() + "/CartListController?result=purchase_fail_empty");
            }
        } else {
            // 잔액 부족 또는 구매 기록 실패 시
            response.sendRedirect(request.getContextPath() + "/CartListController?result=purchase_fail_coin");
        }
    }
    
    // 구매는 POST 요청으로 처리하는 것이 일반적이므로, doPost 메서드를 구현하여 doGet을 호출하도록 합니다.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}