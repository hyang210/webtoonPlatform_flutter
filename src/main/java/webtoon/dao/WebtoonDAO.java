package webtoon.dao;

import webtoon.vo.WebtoonVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 웹툰 정보에 대한 데이터베이스 접근 객체 (DAO)
 */
public class WebtoonDAO {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webtoonplatter?serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC 드라이버 로드 실패!");
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /**
     * 모든 웹툰 목록을 데이터베이스에서 조회합니다.
     * @return WebtoonVO 객체의 리스트
     */
    public List<WebtoonVO> getAllWebtoons() {
        List<WebtoonVO> webtoonList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT webtoonId, koTitle, enTitle, platform, author, rating, genre, thumbnailPath FROM webtoon_tbl ORDER BY webtoonId DESC";

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WebtoonVO webtoon = new WebtoonVO();
                webtoon.setWebtoonId(rs.getInt("webtoonId"));
                webtoon.setKoTitle(rs.getString("koTitle"));
                webtoon.setEnTitle(rs.getString("enTitle"));
                webtoon.setPlatform(rs.getString("platform"));
                webtoon.setAuthor(rs.getString("author"));
                webtoon.setRating(rs.getDouble("rating"));
                webtoon.setGenre(rs.getString("genre"));
                webtoon.setThumbnailPath(rs.getString("thumbnailPath"));
                webtoonList.add(webtoon);
            }
        } catch (SQLException e) {
            System.err.println("웹툰 목록 조회 중 DB 오류 발생: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return webtoonList;
    }
    
    /**
     * 특정 웹툰 ID에 해당하는 상세 정보를 DB에서 조회합니다.
     * @param webtoonId 조회할 웹툰 ID
     * @return 조회된 WebtoonVO 객체, 실패 시 null
     */
    public WebtoonVO getWebtoonById(int webtoonId) {
        WebtoonVO webtoon = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT webtoonId, koTitle, enTitle, platform, author, rating, genre, thumbnailPath FROM webtoon_tbl WHERE webtoonId = ?";

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, webtoonId); 

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 조회 성공 시 WebtoonVO 객체 생성 및 데이터 설정
                webtoon = new WebtoonVO();
                webtoon.setWebtoonId(rs.getInt("webtoonId"));
                webtoon.setKoTitle(rs.getString("koTitle"));
                webtoon.setEnTitle(rs.getString("enTitle"));
                webtoon.setPlatform(rs.getString("platform"));
                webtoon.setAuthor(rs.getString("author"));
                webtoon.setRating(rs.getDouble("rating"));
                webtoon.setGenre(rs.getString("genre"));
                webtoon.setThumbnailPath(rs.getString("thumbnailPath"));
            }
        } catch (SQLException e) {
            System.err.println("웹툰 상세 조회 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return webtoon;
    }
    /**
     * 새로운 웹툰 정보를 데이터베이스에 저장합니다.
     * WebtoonRegController에서 호출됩니다.
     * @param webtoon 등록할 WebtoonVO 객체
     * @return 등록 성공 여부 (true/false)
     */
    public boolean insertWebtoon(WebtoonVO webtoon) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        // 테이블 이름: webtoon_tbl 사용
        // DDL 컬럼명: w_id, koTitle, enTitle, platform, author, rating, genre, thumbnailPath, regDate
        String sql = "INSERT INTO webtoon_tbl "
                   + "(koTitle, enTitle, platform, author, genre, thumbnailPath) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // DB 연결 정보는 기존 WebtoonDAO의 static 필드 사용
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);

            // WebtoonVO에서 데이터를 가져와 파라미터 설정
            pstmt.setString(1, webtoon.getKoTitle());
            pstmt.setString(2, webtoon.getEnTitle());
            pstmt.setString(3, webtoon.getPlatform());
            pstmt.setString(4, webtoon.getAuthor());
            pstmt.setString(5, webtoon.getGenre());
            pstmt.setString(6, webtoon.getThumbnailPath());
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("웹툰 등록 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return result;
    }
    
    /**
     * 지정된 ID의 웹툰을 삭제합니다.
     * @param webtoonId 삭제할 웹툰의 ID
     * @return 삭제 성공 여부 (true/false)
     */
    public boolean deleteWebtoon(int webtoonId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        
        // review_tbl에 외래 키 제약 조건(FOREIGN KEY)이 설정되어 있으므로, 
        // 웹툰을 삭제하기 전에 해당 웹툰에 연결된 리뷰를 먼저 삭제해야 합니다.
        // 이 로직은 트랜잭션으로 묶는 것이 이상적이지만, 여기서는 단순 DELETE 문을 순차적으로 실행합니다.
        
        try {
            conn = getConnection();
            
            // 1. 해당 웹툰에 연결된 리뷰 삭제 (외래 키 제약 조건 해결)
            String deleteReviewsSql = "DELETE FROM review_tbl WHERE webtoonId = ?";
            pstmt = conn.prepareStatement(deleteReviewsSql);
            pstmt.setInt(1, webtoonId);
            pstmt.executeUpdate();
            pstmt.close(); // pstmt 재사용을 위해 닫기
            
            // 2. 웹툰 정보 삭제
            String deleteWebtoonSql = "DELETE FROM webtoon_tbl WHERE webtoonId = ?";
            pstmt = conn.prepareStatement(deleteWebtoonSql);
            pstmt.setInt(1, webtoonId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                success = true;
            }
            
        } catch (SQLException e) {
            System.err.println("웹툰 삭제 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        
        return success;
    }
}