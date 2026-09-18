package webtoon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import webtoon.vo.CartVO;
import webtoon.vo.WebtoonVO;

/** 장바구니 정보를 담당하는 DAO입니다. */
public class CartDAO {
    private static final CartDAO INSTANCE = new CartDAO();

    private CartDAO() {
    }

    public static CartDAO getInstance() {
        return INSTANCE;
    }

    public boolean insertWebtoonToCart(String userId, int webtoonId) {
        String sql = "INSERT INTO cart_tbl (userId, webtoonId) VALUES (?, ?) "
                   + "ON DUPLICATE KEY UPDATE addedDate = CURRENT_TIMESTAMP";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setInt(2, webtoonId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("장바구니 추가 중 DB 오류 발생: " + e.getMessage());
            return false;
        }
    }

    public List<CartVO> getCartItemsByUserId(String userId) {
        List<CartVO> cartList = new ArrayList<>();
        String sql = "SELECT c.cartId, c.userId, c.webtoonId, c.addedDate, "
                   + "w.koTitle, w.enTitle, w.platform, w.author, w.genre, w.rating, w.price, w.thumbnailPath "
                   + "FROM cart_tbl c INNER JOIN webtoon_tbl w ON c.webtoonId = w.webtoonId "
                   + "WHERE c.userId = ? ORDER BY c.addedDate DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WebtoonVO webtoon = new WebtoonVO();
                    webtoon.setWebtoonId(rs.getInt("webtoonId"));
                    webtoon.setKoTitle(rs.getString("koTitle"));
                    webtoon.setEnTitle(rs.getString("enTitle"));
                    webtoon.setPlatform(rs.getString("platform"));
                    webtoon.setAuthor(rs.getString("author"));
                    webtoon.setGenre(rs.getString("genre"));
                    webtoon.setRating(rs.getDouble("rating"));
                    webtoon.setPrice(rs.getInt("price"));
                    webtoon.setThumbnailPath(rs.getString("thumbnailPath"));

                    CartVO cart = new CartVO();
                    cart.setCartId(rs.getInt("cartId"));
                    cart.setUserId(rs.getString("userId"));
                    cart.setWebtoonId(rs.getInt("webtoonId"));
                    cart.setAddedDate(rs.getTimestamp("addedDate"));
                    cart.setWebtoon(webtoon);
                    cartList.add(cart);
                }
            }
        } catch (SQLException e) {
            System.err.println("장바구니 목록 조회 중 DB 오류 발생: " + e.getMessage());
        }
        return cartList;
    }

    public boolean deleteCartItem(String userId, int webtoonId) {
        String sql = "DELETE FROM cart_tbl WHERE userId = ? AND webtoonId = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setInt(2, webtoonId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("장바구니 항목 삭제 중 DB 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
