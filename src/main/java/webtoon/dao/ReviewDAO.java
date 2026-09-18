package webtoon.dao;

import webtoon.vo.ReviewVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReviewDAO {
    // JDBC 연결 정보는 프로젝트의 다른 DAO 파일(예: UserDAO)을 참고하여 설정해야 합니다.
    // 여기서는 예시로 필드를 선언합니다.
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webtoonplatter?serverTimezone=Asia/Seoul";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "1234";

    // JDBC 드라이버 로드는 static 블록에서 한 번만 수행됩니다.
    static {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // 연결 객체를 가져오는 헬퍼 메서드 (중복 제거)
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /**
     * 새로운 리뷰 정보를 데이터베이스에 저장합니다.
     * @param review 등록할 ReviewVO 객체
     * @return 등록 성공 여부 (true/false)
     */
    public boolean insertReview(ReviewVO review) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        // review_tbl에 데이터를 삽입하는 SQL문
        // reviewId와 regDate는 DB에서 자동 처리된다고 가정하고 제외할 수 있습니다.
        String sql = "INSERT INTO review_tbl (webtoonId, userId, reviewContent) "
                   + "VALUES (?, ?, ?)";

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);

            // ReviewVO에서 데이터를 가져와 파라미터 설정
            pstmt.setInt(1, review.getWebtoonId());
            pstmt.setString(2, review.getUserId());
            pstmt.setString(3, review.getReviewContent());
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                result = true;
            }
            
        } catch (SQLException e) {
            System.err.println("리뷰 등록 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return result;
    }
    
    /**
     * 특정 웹툰 ID에 해당하는 모든 리뷰 목록을 조회합니다.
     * @param webtoonId 웹툰 고유 번호
     * @return 리뷰 목록 (List<ReviewVO>)
     */
    public List<ReviewVO> getReviewsByWebtoonId(int webtoonId) {
        List<ReviewVO> reviewList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 최신 리뷰가 먼저 보이도록 regDate 기준으로 내림차순 정렬 (DESC)
        String sql = "SELECT reviewId, webtoonId, userId, reviewContent, regDate FROM review_tbl WHERE webtoonId = ? ORDER BY regDate DESC";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, webtoonId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO review = new ReviewVO();
                review.setReviewId(rs.getInt("reviewId"));
                review.setWebtoonId(rs.getInt("webtoonId"));
                review.setUserId(rs.getString("userId"));
                review.setReviewContent(rs.getString("reviewContent"));
                review.setRegDate(rs.getTimestamp("regDate"));
                reviewList.add(review);
            }
        } catch (SQLException e) {
            System.err.println("리뷰 목록 조회 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return reviewList;
    }
}