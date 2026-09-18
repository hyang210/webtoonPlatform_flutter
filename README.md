# Webtoon Web Project

JSP와 Servlet을 기반으로 제작한 웹툰 웹 애플리케이션입니다.

사용자는 웹툰을 조회하고 회원가입, 로그인, 장바구니, 구매 등의 기능을 사용할 수 있습니다.
관리자는 관리자 로그인 후 웹툰을 등록하고 삭제할 수 있습니다.

---

## 1. 주요 기능

### 사용자 기능

- 회원가입
- 로그인 / 로그아웃
- 웹툰 목록 조회
- 웹툰 상세 조회
- 장바구니
- 웹툰 구매
- 마이페이지
- 언어 변경

### 관리자 기능

- 관리자 로그인
- 웹툰 등록
- 웹툰 이미지 업로드
- 웹툰 삭제

---

# 2. 개발 환경

| 항목 | 버전 |
|---|---|
| Java | JDK 20 |
| Build Tool | Apache Maven 3.9.x |
| Web Server | Apache Tomcat 10.1.x |
| Servlet API | Jakarta Servlet 6.0.0 |
| JSTL API | Jakarta JSTL 3.0.0 |
| JSTL Implementation | Jakarta JSTL 3.0.1 |
| Database | MySQL 8.0 이상 |
| JDBC | MySQL Connector/J 8.0.33 |
| IDE | Visual Studio Code |
| Language | Java |
| View | JSP |
| Frontend | HTML / CSS / JavaScript |

---

# 3. 프로젝트 구조

```text
webProject/
│
├─ pom.xml
├─ README.md
├─ .gitignore
│
└─ src/
   └─ main/
      ├─ java/
      │  ├─ util/
      │  │  └─ DBManager.java
      │  │
      │  └─ webtoon/
      │     ├─ controller/
      │     ├─ dao/
      │     └─ dto/
      │
      └─ webapp/
         ├─ index.jsp
         │
         ├─ admin/
         │  ├─ adminLogin.jsp
         │  └─ webtoonNewRegForm.jsp
         │
         ├─ cart/
         │  ├─ cart.jsp
         │  └─ cartList.jsp
         │
         ├─ common/
         │  ├─ header.jsp
         │  └─ footer.jsp
         │
         ├─ config/
         │  └─ webtoondb.sql
         │
         ├─ css/
         │  └─ style.css
         │
         ├─ uploads/
         │  └─ 웹툰 이미지 파일
         │
         ├─ user/
         │  ├─ loginForm.jsp
         │  ├─ registerForm.jsp
         │  └─ myPage.jsp
         │
         └─ webtoon/
            ├─ webtoonList.jsp
            ├─ webtoonDetail.jsp
            └─ webtoonRegForm.jsp
```

---

# 4. 사용 라이브러리

본 프로젝트는 Maven을 이용하여 라이브러리를 관리합니다.

### Jakarta Servlet

```text
jakarta.servlet:jakarta.servlet-api:6.0.0
```

### JSTL

```text
jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0
```

```text
org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1
```

### MySQL Connector

```text
com.mysql:mysql-connector-j:8.0.33
```

자세한 의존성 설정은 `pom.xml`에서 확인할 수 있습니다.

---

# 5. 실행에 필요한 프로그램

프로젝트를 처음 실행하는 경우 다음 프로그램을 설치해야 합니다.

1. JDK 20
2. Apache Maven 3.9.x
3. Apache Tomcat 10.1.x
4. MySQL 8.0 이상
5. Visual Studio Code
6. Git

---

# 6. Java 설치 및 확인

JDK 20을 설치합니다.

설치 후 명령 프롬프트에서:

```cmd
java -version
```

그리고:

```cmd
javac -version
```

을 실행합니다.

Maven이 사용하는 Java 버전도 확인합니다.

```cmd
mvn -version
```

다음과 같이 Java 20이 표시되는지 확인합니다.

```text
Java version: 20...
```

> `java -version`은 Java 20인데 `mvn -version`은 Java 17로 나오는 경우 Maven의 `JAVA_HOME` 설정을 확인해야 합니다.

Windows 환경 변수에서 다음과 같이 설정하는 것을 권장합니다.

```text
JAVA_HOME=C:\Program Files\Java\jdk-20
```

그리고 Path에:

```text
%JAVA_HOME%\bin
```

을 등록합니다.

환경 변수 변경 후 기존 CMD와 VS Code를 종료하고 다시 실행합니다.

다시 확인:

```cmd
echo %JAVA_HOME%
java -version
mvn -version
```

---

# 7. Maven 설치

Apache Maven 3.9.x를 설치합니다.

확인:

```cmd
mvn -version
```

정상적으로 설치되었다면 다음과 비슷하게 표시됩니다.

```text
Apache Maven 3.9.x
Maven home: ...
Java version: 20...
```

---

# 8. Tomcat 설치

Apache Tomcat 10.1.x를 설치합니다.

예시:

```text
C:\apache-tomcat-10.1.60
```

Tomcat 폴더 구조:

```text
C:\apache-tomcat-10.1.60\
├─ bin/
├─ conf/
├─ lib/
├─ logs/
├─ webapps/
└─ ...
```

본 프로젝트는 `jakarta.servlet.*`를 사용하므로 Tomcat 10.1 환경을 사용합니다.

---

# 9. MySQL 설치

MySQL 8.0 이상을 설치하고 서버를 실행합니다.

프로젝트에서 사용하는 데이터베이스:

```text
Database: webtoonplatter
Host: localhost
Port: 3306
User: root
```

DB 연결 관련 Java 코드는 `DBManager.java`에서 확인할 수 있습니다.

---

# 10. GitHub에서 프로젝트 다운로드

Git을 사용하는 경우:

```cmd
git clone https://github.com/사용자명/저장소명.git
```

다운로드 후 프로젝트 폴더로 이동합니다.

```cmd
cd webProject
```

---

# 11. Visual Studio Code에서 프로젝트 열기

VS Code에서 반드시 `pom.xml`이 있는 프로젝트 최상위 폴더를 엽니다.

예:

```text
C:\Users\사용자명\Desktop\webProject
```

정상적인 구조:

```text
webProject/
├─ pom.xml
├─ README.md
└─ src/
```

`src` 또는 `src/main/java`만 따로 열지 않습니다.

---

# 12. VS Code Java 확장 프로그램

다음 확장 프로그램 설치를 권장합니다.

```text
Extension Pack for Java
```

Maven 관련 기능을 위해:

```text
Maven for Java
```

도 설치합니다.

설치 후 VS Code를 재시작합니다.

---

# 13. 데이터베이스 설정

## 13.1 데이터베이스 생성

MySQL에 접속한 후 다음 명령어를 실행합니다.

```sql
CREATE DATABASE webtoonplatter
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

데이터베이스 선택:

```sql
USE webtoonplatter;
```

---

## 13.2 테이블 생성

프로젝트에 포함된 SQL 파일:

```text
src/main/webapp/config/webtoondb.sql
```

을 MySQL에서 실행합니다.

SQL 실행 후:

```sql
SHOW TABLES;
```

를 실행하여 테이블이 생성되었는지 확인합니다.

---

# 14. DB 연결 정보

DB 연결 정보는 프로젝트의 `DBManager.java`에서 확인합니다.

일반적인 연결 정보:

```text
Host: localhost
Port: 3306
Database: webtoonplatter
Username: root
```

MySQL 비밀번호는 자신의 로컬 MySQL 환경에 맞게 설정해야 합니다.

실제 비밀번호를 GitHub에 공개하지 않는 것을 권장합니다.

---

# 15. Maven 프로젝트 빌드

반드시 `pom.xml`이 있는 프로젝트 폴더에서 실행합니다.

```cmd
cd C:\Users\사용자명\Desktop\webProject
```

빌드:

```cmd
mvn clean package
```

정상적으로 완료되면:

```text
BUILD SUCCESS
```

가 표시됩니다.

빌드 결과:

```text
target/
└─ webProject.war
```

---

# 16. Tomcat에 WAR 배포

Maven 빌드 후 생성된:

```text
target\webProject.war
```

파일을 Tomcat의 `webapps` 폴더에 복사합니다.

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

기존에 다음 폴더가 있다면 재배포 전에 삭제할 수 있습니다.

```text
C:\apache-tomcat-10.1.60\webapps\webProject\
```

---

# 17. Tomcat 실행

Tomcat의 `bin` 폴더로 이동합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
```

실행:

```cmd
startup.bat
```

---

# 18. 웹 애플리케이션 접속

브라우저에서:

```text
http://localhost:8080/webProject/
```

접속합니다.

메인 페이지에서는 웹툰 목록 페이지로 이동합니다.

### 웹툰 목록

```text
http://localhost:8080/webProject/webtoon/webtoonList.jsp
```

### 로그인

```text
http://localhost:8080/webProject/user/loginForm.jsp
```

### 회원가입

```text
http://localhost:8080/webProject/user/registerForm.jsp
```

### 관리자 로그인

```text
http://localhost:8080/webProject/admin/adminLogin.jsp
```

### 장바구니

```text
http://localhost:8080/webProject/cart/cartList.jsp
```

---

# 19. 전체 실행 순서

```text
1. JDK 20 설치
        ↓
2. Maven 설치
        ↓
3. Tomcat 10.1 설치
        ↓
4. MySQL 설치 및 실행
        ↓
5. GitHub에서 프로젝트 다운로드
        ↓
6. VS Code에서 webProject 폴더 열기
        ↓
7. webtoonplatter 데이터베이스 생성
        ↓
8. webtoondb.sql 실행
        ↓
9. DB 연결 정보 확인
        ↓
10. mvn clean package
        ↓
11. target/webProject.war 생성
        ↓
12. Tomcat/webapps에 WAR 복사
        ↓
13. startup.bat 실행
        ↓
14. http://localhost:8080/webProject/
```

---

# 20. 코드 수정 후 재실행

Java 또는 JSP 코드를 수정한 경우 다음 과정을 진행합니다.

### 1. Maven 재빌드

```cmd
cd C:\Users\사용자명\Desktop\webProject
mvn clean package
```

### 2. Tomcat 종료

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

### 3. WAR 교체

```text
target\webProject.war
```

를:

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

로 복사합니다.

### 4. Tomcat 실행

```cmd
startup.bat
```

### 5. 브라우저 새로고침

```text
http://localhost:8080/webProject/
```

필요한 경우:

```text
Ctrl + F5
```

로 강력 새로고침합니다.

---

# 21. 자주 발생하는 오류

## `mvn` 명령어를 찾을 수 없음

```text
'mvn' is not recognized as an internal or external command
```

Maven 설치 여부와 Path를 확인합니다.

```cmd
mvn -version
```

---

## `invalid target release: 20`

프로젝트는 Java 20을 요구하지만 Maven이 다른 Java 버전을 사용하고 있을 때 발생합니다.

확인:

```cmd
mvn -version
```

`Java version`이 20인지 확인합니다.

필요한 경우:

```cmd
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"
```

다시 확인:

```cmd
mvn -version
```

---

## `POM file does not exist`

`pom.xml`이 없는 폴더에서 Maven을 실행한 경우 발생합니다.

잘못된 위치:

```cmd
cd C:\apache-tomcat-10.1.60\bin
mvn clean package
```

올바른 위치:

```cmd
cd C:\Users\사용자명\Desktop\webProject
mvn clean package
```

---

## VS Code에서 `jakarta.servlet` 오류

다음과 같은 import에 빨간 밑줄이 나타날 수 있습니다.

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

먼저 `pom.xml`의 Jakarta Servlet API 의존성을 확인합니다.

```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>
```

그래도 문제가 지속되면:

```text
Ctrl + Shift + P
```

→

```text
Java: Clean Java Language Server Workspace
```

를 실행하고 VS Code를 재시작합니다.

---

## `localhost:8080` 접속 불가

다음을 확인합니다.

1. Tomcat 실행 여부
2. `startup.bat` 실행 여부
3. WAR 파일이 `webapps`에 존재하는지
4. Tomcat 로그
5. 8080 포트 사용 여부

Tomcat 로그 위치:

```text
C:\apache-tomcat-10.1.60\logs
```

---

## 이미지가 표시되지 않는 경우

웹툰 이미지는 다음 폴더에 저장됩니다.

```text
src/main/webapp/uploads/
```

브라우저에서 직접 이미지 URL을 확인할 수 있습니다.

```text
http://localhost:8080/webProject/uploads/파일명
```

DB의 `thumbnailPath`와 실제 `uploads` 폴더의 파일명이 일치하는지 확인합니다.

---

## MySQL 연결 오류

예:

```text
Communications link failure
```

또는:

```text
Access denied for user
```

다음 항목을 확인합니다.

```text
MySQL 실행 여부
Host
Port
Database
Username
Password
```

프로젝트 데이터베이스:

```text
webtoonplatter
```

기본 포트:

```text
3306
```

---

# 22. GitHub 업로드 시 제외할 파일

`.gitignore`에 다음 항목을 추가하는 것을 권장합니다.

```gitignore
# Maven
target/
build/

# Eclipse
.classpath
.project
.settings/

# VS Code
.vscode/

# Environment / Local settings
.env
*.local

# Logs
*.log

# OS files
.DS_Store
Thumbs.db

# Temporary files
*.tmp
```

---

# 23. 프로젝트 실행 구조

```text
사용자
  ↓
웹 브라우저
  ↓
Tomcat 10.1
  ↓
webProject.war
  ↓
JSP / Servlet
  ↓
MySQL
```

Maven은 다음 작업을 담당합니다.

```text
pom.xml
   ↓
필요한 라이브러리 다운로드
   ↓
Java 컴파일
   ↓
WAR 패키징
   ↓
target/webProject.war
```

---

# 24. 관리자 테스트

관리자 기능을 테스트하려면 관리자 계정으로 로그인합니다.

관리자 로그인:

```text
http://localhost:8080/webProject/admin/adminLogin.jsp
```

로그인 후 다음 기능을 확인할 수 있습니다.

```text
관리자 로그인
    ↓
웹툰 등록
    ↓
이미지 업로드
    ↓
웹툰 목록 확인
    ↓
웹툰 상세 확인
    ↓
웹툰 삭제
```

---

# 25. 주요 기능 테스트

프로젝트 실행 후 다음 순서로 기능을 테스트할 수 있습니다.

```text
메인 페이지
   ↓
웹툰 목록
   ↓
웹툰 상세
   ↓
회원가입
   ↓
로그인
   ↓
마이페이지
   ↓
장바구니
   ↓
구매
   ↓
관리자 로그인
   ↓
웹툰 등록
   ↓
이미지 업로드
   ↓
웹툰 삭제
```

---

# 26. Tomcat 종료

Tomcat을 종료하려면:

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

을 실행합니다.