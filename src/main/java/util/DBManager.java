package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 데이터베이스 연결 및 자원 해제를 관리하는 유틸리티 클래스
 * 모든 DAO에서 공통으로 사용됩니다.
 */
public class DBManager {
    
    // DB 연결 정보 (사용자님의 환경에 맞게 수정 필요)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webtoonplatter?serverTimezone=Asia/Seoul";
    private static final String DB_USER = "root"; // 사용자님의 DB 계정
    private static final String DB_PASS = "1234"; // 사용자님의 DB 비밀번호

    // 🚨 MySQL JDBC 드라이버 로드 (클래스 로딩 시 단 한 번만 실행)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * DB 연결(Connection) 객체를 반환합니다.
     * @return Connection 객체
     * @throws SQLException 연결 실패 시 발생
     */
    public static Connection getConnection() throws SQLException {
        // 🚨 WebtoonDAO에 하드코딩된 정보를 사용하지 않고, 이 중앙 관리 정보를 사용하도록 통일합니다.
        // 현재 WebtoonDAO와 ReviewDAO.dao에 분산된 DB 정보도 이 파일로 통일하는 것을 권장합니다.
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    /**
     * DB 자원(Connection, PreparedStatement)을 해제합니다.
     */
    public static void close(Connection conn, PreparedStatement pstmt) {
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            System.err.println("PreparedStatement close error: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Connection close error: " + e.getMessage());
        }
    }

    /**
     * DB 자원(Connection, PreparedStatement, ResultSet)을 해제합니다.
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("ResultSet close error: " + e.getMessage());
        }
        close(conn, pstmt); // Connection과 PreparedStatement는 위의 메서드를 호출하여 해제
    }
}