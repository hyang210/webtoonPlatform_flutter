package webtoon.vo;

import java.sql.Timestamp;

public class CartVO {
    private int cartId;
    private String userId;
    private int webtoonId;
    private Timestamp addedDate;
    
    // 장바구니 목록 출력을 위해 WebtoonVO 객체를 포함합니다.
    private WebtoonVO webtoon;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWebtoonId() {
        return webtoonId;
    }

    public void setWebtoonId(int webtoonId) {
        this.webtoonId = webtoonId;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Timestamp addedDate) {
        this.addedDate = addedDate;
    }

    public WebtoonVO getWebtoon() {
        return webtoon;
    }

    public void setWebtoon(WebtoonVO webtoon) {
        this.webtoon = webtoon;
    }
}