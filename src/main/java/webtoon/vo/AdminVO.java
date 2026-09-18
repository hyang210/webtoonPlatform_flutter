package webtoon.vo;

/**
 * 관리자 계정 데이터를 저장하고 전달하는 객체 (Value Object)
 * DB의 admin_user 테이블과 1:1 매핑됩니다.
 */
public class AdminVO {
    private String adminId;
    private String password;
    private String name;

    // 생성자 (기본)
    public AdminVO() {}
    
    // Getter 및 Setter 메소드
    
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
}