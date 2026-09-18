package webtoon.vo;

import java.sql.Timestamp;

/**
 * 사용자 계정 데이터를 저장하고 전달하는 객체 (Value Object)
 * DB의 user_tbl 테이블과 1:1 매핑됩니다. (userId, userPw, userName, userEmail, profilePath, regDate, coin)
 */
public class UserVO {
    private String userId;
    private String userPw; // DB의 userPw 필드와 매핑
    private String userName; // DB의 userName 필드와 매핑
    private String userEmail; // DB의 userEmail 필드와 매핑
    private String profilePath; // DB의 profilePath 필드와 매핑
    private Timestamp regDate; // DB의 regDate 필드와 매핑
    private int coin; // 🚨 구매 기능을 위한 코인 잔액 필드 (DB coin 컬럼과 매핑)

    // 기본 생성자
    public UserVO() {}

    // 모든 필드를 포함한 생성자
    public UserVO(String userId, String userPw, String userName, String userEmail, String profilePath, Timestamp regDate, int coin) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userEmail = userEmail;
        this.profilePath = profilePath;
        this.regDate = regDate;
        this.coin = coin;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    // Note: 메서드 이름은 setUserId, setUserName 등 명확하게 통일했습니다.
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }
    
    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}