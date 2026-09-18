-- src/main/webapp/config/webtoondb.sql

-- 'webtoonplatter' DB에 테이블을 생성합니다.
USE webtoonplatter;

-- 2. 테이블 드롭
DROP TABLE IF EXISTS review_tbl; 
DROP TABLE IF EXISTS cart_tbl;   
DROP TABLE IF EXISTS webtoon_tbl;
DROP TABLE IF EXISTS admin_tbl;
DROP TABLE IF EXISTS user_tbl;


-- 3. 사용자 테이블 (user_tbl)
CREATE TABLE user_tbl (
    userId VARCHAR(50) PRIMARY KEY,
    userPw VARCHAR(255) NOT NULL,
    userName VARCHAR(50) NOT NULL,
    userEmail VARCHAR(100) UNIQUE NOT NULL,
    profilePath VARCHAR(255) DEFAULT NULL,
    regDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    coin INT DEFAULT 0
);

-- 4. 관리자 테이블 (admin_tbl)
CREATE TABLE admin_tbl (
    adminId VARCHAR(50) PRIMARY KEY,
    adminPw VARCHAR(255) NOT NULL
);

-- 초기 관리자 계정 삽입 (ID: admin, PW: 1234)
INSERT INTO admin_tbl (adminId, adminPw) VALUES ('admin', 'ryu5676');


-- 5. 웹툰 정보 테이블 (webtoon_tbl)
CREATE TABLE webtoon_tbl (
    webtoonId INT AUTO_INCREMENT PRIMARY KEY,
    koTitle VARCHAR(100) NOT NULL,
    enTitle VARCHAR(100) NOT NULL,
    platform VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    rating DECIMAL(2, 1) DEFAULT 0.0,
    price INT DEFAULT 100,
    thumbnailPath VARCHAR(255) NOT NULL
);

-- 6. 리뷰 테이블 (review_tbl)
CREATE TABLE review_tbl (
    reviewId INT PRIMARY KEY AUTO_INCREMENT,
    webtoonId INT NOT NULL,    
    userId VARCHAR(50) NOT NULL,     
    reviewContent VARCHAR(1000) NOT NULL,
    regDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 외래 키 설정)
    FOREIGN KEY (webtoonId) REFERENCES webtoon_tbl(webtoonId),
    FOREIGN KEY (userId) REFERENCES user_tbl(userId)
);

-- 7. 장바구니 테이블 (cart_tbl)
CREATE TABLE cart_tbl (
    cartId INT PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(50) NOT NULL,
    webtoonId INT NOT NULL,
    addedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- 복합 유니크 키: 한 사용자가 같은 웹툰을 중복해서 담을 수 없도록 합니다.
    UNIQUE KEY (userId, webtoonId), 
    
    -- 외래 키 설정
    FOREIGN KEY (userId) REFERENCES user_tbl(userId) ON DELETE CASCADE,
    FOREIGN KEY (webtoonId) REFERENCES webtoon_tbl(webtoonId) ON DELETE CASCADE
);