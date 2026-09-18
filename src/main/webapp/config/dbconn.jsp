<%-- 
    파일명: dbconn.jsp
    JSP 페이지에서 사용할 데이터베이스 연결 정보를 정의하고 연결 객체를 생성하는 모듈
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%!
    // DB 이름을 'webtoonplatter' (소문자)로 확실히 수정
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/webtoonplatter?serverTimezone=UTC";
    private final String JDBC_USER = "root";
    private final String JDBC_PASS = "1234"; // 실제 비밀번호로 변경
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // DB 연결 객체 (Connection)
    Connection conn = null;

    public void jspInit() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("[DBCONN] MySQL JDBC 드라이버 로드 실패!");
            e.printStackTrace();
        }
    }

    // DB 연결 해제 메서드 (자원 반환)
    public void closeDB(PreparedStatement pstmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch(SQLException e) {}
        try { if (pstmt != null) pstmt.close(); } catch(SQLException e) {}
        try { if (conn != null) conn.close(); } catch(SQLException e) {}
    }
%>

<%
    // 페이지가 호출될 때마다 연결 시도
    try {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            System.out.println("[DBCONN] DB 연결 성공!");
        }
    } catch (SQLException e) {
        System.err.println("[DBCONN] DB 연결 실패: " + e.getMessage());
        response.sendRedirect(request.getContextPath() + "/error.jsp");
    }
%>