package webtoon.dao;

import webtoon.vo.WebtoonVO;
import webtoon.vo.AdminVO;
import java.sql.*;

/**
 * 관리자 기능(웹툰 등록, 관리자 로그인 등)에 대한 데이터베이스 접근 객체 (DAO)
 */
public class AdminDAO {
    // DB 연결 정보는 dbconn.jsp의 최종 설정과 일치
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webtoonplatter?serverTimezone=UTC";
    private static final String JDBC_USER = "root"; // MySQL 사용자 ID
    private static final String JDBC_PASS = "1234"; // MySQL 비밀번호

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC 드라이버 로드 실패!");
            e.printStackTrace();
        }
    }

    /**
     * 새로운 웹툰 정보를 데이터베이스에 등록합니다.
     * @param webtoon 등록할 WebtoonVO 객체
     * @return 등록 성공 여부 (true/false)
     */
    public boolean insertWebtoon(WebtoonVO webtoon) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        // 🛑 수정: 테이블 이름과 컬럼 이름을 webtoon_tbl DDL과 일치시킴
        String sql = "INSERT INTO webtoon_tbl (koTitle, enTitle, platform, author, rating, genre, thumbnailPath) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);
            
            // WebtoonVO에서 데이터를 가져와 파라미터 설정
            pstmt.setString(1, webtoon.getKoTitle());
            pstmt.setString(2, webtoon.getEnTitle());
            pstmt.setString(3, webtoon.getPlatform());
            pstmt.setString(4, webtoon.getAuthor());
            pstmt.setDouble(5, webtoon.getRating()); 
            pstmt.setString(6, webtoon.getGenre());
            pstmt.setString(7, webtoon.getThumbnailPath());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("웹툰 정보 DB 등록 중 오류 발생: " + e.getMessage());
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return result;
    }
    
    /**
     * 관리자 ID와 비밀번호를 검증하고, 일치하면 AdminVO 객체를 반환합니다.
     * @param adminId 로그인 시도 ID
     * @param adminPw 로그인 시도 비밀번호
     * @return 인증 성공 시 AdminVO 객체, 실패 시 null
     */
    public AdminVO adminLoginCheck(String adminId, String adminPw) {
        AdminVO admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        // 1. 테이블 이름을 'admin_tbl'로 수정하여 DDL과 일치시킴
        // 2. SQL 컬럼 이름을 admin_tbl DDL과 일치시킴 (adminId, adminPw)
        // 3. Select 결과를 admin_tbl 컬럼에 맞게 'adminId' 하나만 조회
        String sql = "SELECT adminId FROM admin_tbl WHERE adminId = ? AND adminPw = ?";

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, adminId);
            pstmt.setString(2, adminPw);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 인증 성공 시 AdminVO 객체 생성
                admin = new AdminVO();
                // DDL에 'adminId' 컬럼만 있으므로 'name' 대신 'adminId'를 다시 세팅
                admin.setAdminId(rs.getString("adminId"));
                // AdminVO에 name 필드가 있다면, DDL에 name이 없으므로 null을 세팅하거나 이 줄을 삭제해야 합니다.
                // admin.setName(null); 
            }
        } catch (SQLException e) {
            System.err.println("관리자 로그인 체크 중 DB 오류 발생: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return admin;
    }
}