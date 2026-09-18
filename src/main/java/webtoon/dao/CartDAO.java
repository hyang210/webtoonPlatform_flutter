package webtoon.dao;

import webtoon.vo.WebtoonVO;
import webtoon.vo.CartVO;
import util.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 데이터에 대한 데이터베이스 접근 객체 (DAO)
 */
public class CartDAO {
    
    // 🚨 싱글톤 패턴 적용
    private static CartDAO instance = new CartDAO();
    private CartDAO() {}
    public static CartDAO getInstance() {
        return instance;
    }

    /**
     * 장바구니에 웹툰을 추가합니다. (중복 방지 로직 포함)
     * @param userId 사용자 ID
     * @param webtoonId 웹툰 ID
     * @return 추가 성공 여부 (true/false)
     */
    public boolean insertWebtoonToCart(String userId, int webtoonId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        // INSERT ... ON DUPLICATE KEY UPDATE를 사용하여 중복 방지
        String sql = "INSERT INTO cart_tbl (userId, webtoonId) VALUES (?, ?) "
                   + "ON DUPLICATE KEY UPDATE addedDate = CURRENT_TIMESTAMP"; 

        try {
            // 🚨 디버깅: 입력된 파라미터 출력
            System.out.println("--- CartDAO DEBUG ---");
            System.out.println("SQL: " + sql);
            System.out.println("Param - userId: " + userId);
            System.out.println("Param - webtoonId: " + webtoonId);
            
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, webtoonId);
            
            int affectedRows = pstmt.executeUpdate();
            
            // 🚨 디버깅: 쿼리 실행 결과 출력
            System.out.println("Affected Rows: " + affectedRows);
            System.out.println("---------------------");

            if (affectedRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            // 🚨 에러 발생 시 SQL 상세 정보 출력
            System.err.println("장바구니 추가 중 DB 오류 발생: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Vendor Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }
        return result;
    }
    
    /**
     * 특정 사용자의 장바구니 목록과 웹툰 정보를 함께 조회합니다.
     * @param userId 사용자 ID
     * @return 장바구니에 담긴 웹툰 목록 (List<CartVO>)
     */
    public List<CartVO> getCartItemsByUserId(String userId) {
        List<CartVO> cartList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // cart_tbl의 모든 컬럼(c.*)과 webtoon_tbl의 모든 컬럼(w.*)을 모두 가져옵니다.
        String sql = "SELECT c.cartId, c.userId, c.webtoonId, c.addedDate, " 
                   + "w.koTitle, w.enTitle, w.platform, w.author, w.genre, w.rating, w.thumbnailPath "
                   + "FROM webtoon_tbl w "
                   + "INNER JOIN cart_tbl c ON w.webtoonId = c.webtoonId "
                   + "WHERE c.userId = ? ORDER BY c.addedDate DESC";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // 1. WebtoonVO 객체 생성 및 데이터 매핑
                WebtoonVO webtoon = new WebtoonVO();
                webtoon.setWebtoonId(rs.getInt("webtoonId"));
                webtoon.setKoTitle(rs.getString("koTitle"));
                webtoon.setEnTitle(rs.getString("enTitle"));
                webtoon.setPlatform(rs.getString("platform"));
                webtoon.setAuthor(rs.getString("author"));
                webtoon.setGenre(rs.getString("genre"));
                webtoon.setRating(rs.getDouble("rating"));
                webtoon.setThumbnailPath(rs.getString("thumbnailPath"));
                
                // 2. CartVO 객체 생성 및 데이터 매핑
                CartVO cart = new CartVO();
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getString("userId"));
                cart.setWebtoonId(rs.getInt("webtoonId"));
                cart.setAddedDate(rs.getTimestamp("addedDate"));
                
                // 3. CartVO 내부에 WebtoonVO를 담아서 최종 리스트에 추가
                cart.setWebtoon(webtoon); 
                cartList.add(cart);
            }
        } catch (SQLException e) {
            System.err.println("장바구니 목록 조회 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt, rs);
        }
        return cartList;
    }
    
    /**
     * 장바구니에서 특정 웹툰을 삭제합니다.
     * @param userId 사용자 ID
     * @param webtoonId 웹툰 ID
     * @return 삭제 성공 여부 (true/false)
     */
    public boolean deleteCartItem(String userId, int webtoonId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        String sql = "DELETE FROM cart_tbl WHERE userId = ? AND webtoonId = ?";

        try {
            conn = DBManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, webtoonId);
            
            if (pstmt.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("장바구니 항목 삭제 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBManager.close(conn, pstmt);
        }
        return result;
    }
}