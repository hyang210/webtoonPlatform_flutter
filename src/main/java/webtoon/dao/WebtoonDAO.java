package webtoon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import webtoon.vo.WebtoonVO;

/** 웹툰 정보를 담당하는 DAO입니다. */
public class WebtoonDAO {

    public List<WebtoonVO> getAllWebtoons() {
        List<WebtoonVO> webtoonList = new ArrayList<>();
        String sql = "SELECT webtoonId, koTitle, enTitle, platform, author, rating, genre, price, thumbnailPath "
                   + "FROM webtoon_tbl ORDER BY webtoonId DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                webtoonList.add(mapWebtoon(rs));
            }
        } catch (SQLException e) {
            System.err.println("웹툰 목록 조회 중 DB 오류 발생: " + e.getMessage());
        }
        return webtoonList;
    }

    public WebtoonVO getWebtoonById(int webtoonId) {
        String sql = "SELECT webtoonId, koTitle, enTitle, platform, author, rating, genre, price, thumbnailPath "
                   + "FROM webtoon_tbl WHERE webtoonId = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, webtoonId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapWebtoon(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("웹툰 상세 조회 중 DB 오류 발생: " + e.getMessage());
        }
        return null;
    }

    public boolean insertWebtoon(WebtoonVO webtoon) {
        String sql = "INSERT INTO webtoon_tbl "
                   + "(koTitle, enTitle, platform, author, genre, price, thumbnailPath) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, webtoon.getKoTitle());
            pstmt.setString(2, webtoon.getEnTitle());
            pstmt.setString(3, webtoon.getPlatform());
            pstmt.setString(4, webtoon.getAuthor());
            pstmt.setString(5, webtoon.getGenre());
            pstmt.setInt(6, webtoon.getPrice());
            pstmt.setString(7, webtoon.getThumbnailPath());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("웹툰 등록 중 DB 오류 발생: " + e.getMessage());
            return false;
        }
    }

    /** 리뷰와 웹툰 삭제를 하나의 트랜잭션으로 처리합니다. */
    public boolean deleteWebtoon(int webtoonId) {
        String deleteReviewsSql = "DELETE FROM review_tbl WHERE webtoonId = ?";
        String deleteWebtoonSql = "DELETE FROM webtoon_tbl WHERE webtoonId = ?";

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement reviewStmt = conn.prepareStatement(deleteReviewsSql);
                 PreparedStatement webtoonStmt = conn.prepareStatement(deleteWebtoonSql)) {

                reviewStmt.setInt(1, webtoonId);
                reviewStmt.executeUpdate();

                webtoonStmt.setInt(1, webtoonId);
                int affected = webtoonStmt.executeUpdate();

                if (affected == 0) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("웹툰 삭제 중 DB 오류 발생: " + e.getMessage());
            return false;
        }
    }

    private WebtoonVO mapWebtoon(ResultSet rs) throws SQLException {
        WebtoonVO webtoon = new WebtoonVO();
        webtoon.setWebtoonId(rs.getInt("webtoonId"));
        webtoon.setKoTitle(rs.getString("koTitle"));
        webtoon.setEnTitle(rs.getString("enTitle"));
        webtoon.setPlatform(rs.getString("platform"));
        webtoon.setAuthor(rs.getString("author"));
        webtoon.setRating(rs.getDouble("rating"));
        webtoon.setGenre(rs.getString("genre"));
        webtoon.setPrice(rs.getInt("price"));
        webtoon.setThumbnailPath(rs.getString("thumbnailPath"));
        return webtoon;
    }
}
