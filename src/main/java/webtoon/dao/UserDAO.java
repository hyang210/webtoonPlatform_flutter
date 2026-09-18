package webtoon.dao;

import webtoon.vo.UserVO;
import java.sql.*;

/**
 * 사용자 정보(로그인, 회원가입, 잔액 관리 등)에 대한 데이터베이스 접근 객체 (DAO)
 */
public class UserDAO {
    // DB 연결 정보는 ReviewDAO.dao를 참고하여 동일하게 가정합니다.
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

    /**
     * DB 연결 객체를 반환합니다.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /**
     * 사용자 ID와 비밀번호를 검증하고, 일치하면 사용자 정보를 반환합니다.
     * 🚨 coin 필드를 포함한 모든 정보 조회로 수정됨.
     * @param userId 로그인 시도 ID
     * @param userPw 로그인 시도 비밀번호
     * @return 인증 성공 시 UserVO 객체, 실패 시 null
     */
    public UserVO loginCheck(String userId, String userPw) {
        UserVO user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        // coin, profilePath, regDate 등 UserVO의 모든 필드를 조회하도록 쿼리 업데이트
        String sql = "SELECT userId, userPw, userName, userEmail, profilePath, regDate, coin FROM user_tbl WHERE userId = ? AND userPw = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw); 

            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserVO();
                user.setUserId(rs.getString("userId"));
                user.setUserPw(rs.getString("userPw"));
                user.setUserName(rs.getString("userName"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setProfilePath(rs.getString("profilePath"));
                user.setRegDate(rs.getTimestamp("regDate"));
                user.setCoin(rs.getInt("coin")); // 🚨 코인 정보 설정
            }
        } catch (SQLException e) {
            System.err.println("로그인 체크 중 DB 오류 발생: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return user;
    }
    
    /**
     * 새로운 사용자 정보를 데이터베이스에 저장합니다.
     * @param user 등록할 UserVO 객체
     * @return 등록 성공 여부 (true/false)
     */
    public boolean insertUser(UserVO user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        // 🚨 수정: coin 컬럼 추가 (기본값은 DB에서 0으로 설정되지만 명시적으로 포함 가능)
        String sql = "INSERT INTO user_tbl (userId, userPw, userName, userEmail, profilePath, coin) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            // UserVO에서 데이터를 가져와 파라미터 설정 (UserVO의 최신 Getter 사용)
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPw()); 
            pstmt.setString(3, user.getUserName()); 
            pstmt.setString(4, user.getUserEmail()); 
            pstmt.setString(5, user.getProfilePath()); 
            pstmt.setInt(6, user.getCoin()); // 초기값 0이거나, VO에서 설정된 값

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("회원가입 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return result;
    }
    
    /**
     * 사용자 ID가 데이터베이스에 이미 존재하는지 확인합니다.
     * @param userId 중복 확인을 시도할 ID
     * @return 중복되면 true (사용 불가능), 중복되지 않으면 false (사용 가능)
     */
    public boolean checkId(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isDuplicate = false;
        
        String sql = "SELECT userId FROM user_tbl WHERE userId = ?";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                isDuplicate = true; 
            }
        } catch (SQLException e) {
            System.err.println("ID 중복 확인 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return isDuplicate;
    }
    
    /**
     * 사용자의 프로필 이미지 경로를 업데이트합니다.
     * @param userId 업데이트할 사용자 ID
     * @param webPath 웹 상의 프로필 이미지 경로
     * @return 업데이트 성공 여부 (true/false)
     */
    public boolean updateProfilePath(String userId, String webPath) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        String sql = "UPDATE user_tbl SET profilePath = ? WHERE userId = ?";
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, webPath);
            pstmt.setString(2, userId);
            
            if (pstmt.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.println("UserDAO 프로필 경로 업데이트 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return result;
    }

    // 장바구니/구매 기능을 위한 코인 관련 DAO 메서드 추가

    /**
     * 특정 사용자의 현재 코인 잔액을 조회합니다.
     * @param userId 코인을 조회할 사용자 ID
     * @return 사용자 코인 잔액. 조회 실패 시 0 반환.
     */
    public int getUserCoin(String userId) {
        String sql = "SELECT coin FROM user_tbl WHERE userId = ?";
        int userCoin = 0;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userCoin = rs.getInt("coin"); 
                }
            }
        } catch (SQLException e) {
            System.err.println("UserDAO 코인 조회 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return userCoin;
    }

    /**
     * 사용자 코인 잔액을 업데이트(차감/충전)합니다.
     * @param userId 업데이트할 사용자 ID
     * @param newCoin 업데이트될 새로운 잔액
     * @return 업데이트 성공 여부 (true/false)
     */
    public boolean updateUserCoin(String userId, int newCoin) {
        String sql = "UPDATE user_tbl SET coin = ? WHERE userId = ?";
        boolean success = false;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newCoin);
            pstmt.setString(2, userId);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println("UserDAO 코인 업데이트 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
}