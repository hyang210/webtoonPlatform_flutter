package webtoon.vo;

import java.sql.Timestamp;

public class ReviewVO {
    private int reviewId;      // 리뷰 고유 번호 (PK)
    private int webtoonId;     // 리뷰 대상 웹툰 ID (FK)
    private String userId;     // 리뷰 작성자 ID (FK)
    private String reviewContent; // 리뷰 내용
    private Timestamp regDate; // 리뷰 등록일

    // 기본 생성자
    public ReviewVO() {}

    // Getter와 Setter
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getWebtoonId() {
        return webtoonId;
    }

    public void setWebtoonId(int webtoonId) {
        this.webtoonId = webtoonId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    // 디버깅을 위한 toString (선택 사항)
    @Override
    public String toString() {
        return "ReviewVO [reviewId=" + reviewId + ", webtoonId=" + webtoonId + 
               ", userId=" + userId + ", reviewContent=" + reviewContent + 
               ", regDate=" + regDate + "]";
    }
}