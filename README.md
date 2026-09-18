# Webtoon Web Project

JSP와 Servlet을 기반으로 제작한 웹툰 웹 애플리케이션입니다.

사용자는 웹툰을 조회하고 회원가입 및 로그인, 장바구니 등의 기능을 사용할 수 있으며,
관리자는 웹툰을 등록하고 관리할 수 있습니다.

---

## 1. 프로젝트 개요

### 개발 목적

JSP/Servlet 기반의 웹 애플리케이션 개발 및
웹 서버(Tomcat)와 데이터베이스(MySQL)를 연동하는 것을 목적으로 제작한 프로젝트입니다.

### 주요 기능

#### 사용자

- 회원가입
- 로그인
- 로그아웃
- 웹툰 목록 조회
- 웹툰 상세 조회
- 장바구니
- 마이페이지

#### 관리자

- 관리자 로그인
- 웹툰 등록
- 웹툰 관리

---

# 2. 개발 환경

| 항목 | 버전 |
|---|---|
| OS | Windows |
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
      │
      ├─ java/
      │  └─ Java 소스 코드
      │
      └─ webapp/
         │
         ├─ index.jsp
         │
         ├─ admin/
         │  ├─ adminLogin.jsp
         │  ├─ webtoonNewRegForm.jsp
         │  └─ ...
         │
         ├─ cart/
         │  ├─ cart.jsp
         │  ├─ cartList.jsp
         │  └─ ...
         │
         ├─ common/
         │  ├─ header.jsp
         │  ├─ footer.jsp
         │  └─ ...
         │
         ├─ config/
         │  └─ dbconn.jsp
         │
         ├─ user/
         │  ├─ loginForm.jsp
         │  ├─ registerForm.jsp
         │  ├─ myPage.jsp
         │  └─ ...
         │
         └─ webtoon/
            ├─ webtoonList.jsp
            ├─ webtoonDetail.jsp
            ├─ webtoonRegForm.jsp
            └─ ...
```

---

# 4. 사용 라이브러리

본 프로젝트는 Maven을 이용하여 필요한 라이브러리를 관리합니다.

주요 의존성은 다음과 같습니다.

### Jakarta Servlet

```text
jakarta.servlet:jakarta.servlet-api:6.0.0
```

Tomcat 10.1 환경에서 사용하는 Jakarta Servlet API입니다.

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

# 6. Java 설치

JDK 20을 설치합니다.

설치 후 명령 프롬프트에서 다음 명령어를 실행합니다.

```cmd
java -version
```

다음 명령어도 확인합니다.

```cmd
javac -version
```

예시:

```text
java version "20..."
javac 20...
```

---

# 7. Maven 설치

Apache Maven 3.9.x를 설치합니다.

설치 후 다음 명령어를 실행합니다.

```cmd
mvn -version
```

예시:

```text
Apache Maven 3.9.x
Maven home: ...
Java version: 20...
```

중요한 점은 Maven이 사용하는 Java 버전입니다.

반드시 다음 명령어의 결과에서 Java 20이 사용되는지 확인합니다.

```cmd
mvn -version
```

`java -version`과 `mvn -version`에서 사용하는 Java 버전이 서로 다를 수 있으므로,
Maven 빌드 오류가 발생하는 경우 `mvn -version`을 먼저 확인합니다.

---

# 8. Tomcat 설치

Apache Tomcat 10.1을 설치합니다.

예시 설치 경로:

```text
C:\apache-tomcat-10.1.60
```

Tomcat 폴더 구조는 다음과 같습니다.

```text
C:\apache-tomcat-10.1.60\
├─ bin/
├─ conf/
├─ lib/
├─ logs/
├─ webapps/
└─ ...
```

프로젝트에서 `jakarta.servlet.*` 패키지를 사용하기 때문에
Tomcat 10.1 환경을 사용합니다.

---

# 9. MySQL 설치

MySQL 8.0 이상을 설치하고 서버를 실행합니다.

일반적인 MySQL 설정은 다음과 같습니다.

| 설정 | 값 |
|---|---|
| Host | localhost |
| Port | 3306 |
| User | root |
| Database | 프로젝트 설정에 맞는 DB 이름 |

실제 데이터베이스 이름과 계정 정보는 프로젝트의

```text
src/main/webapp/config/dbconn.jsp
```

파일에서 확인합니다.

---

# 10. GitHub에서 프로젝트 가져오기

Git을 사용하는 경우 다음 명령어로 프로젝트를 다운로드할 수 있습니다.

```cmd
git clone https://github.com/사용자명/저장소명.git
```

다운로드 후 프로젝트 폴더로 이동합니다.

```cmd
cd 저장소명
```

예:

```cmd
cd webProject
```

---

# 11. Visual Studio Code에서 프로젝트 열기

VS Code에서 반드시 **프로젝트 최상위 폴더**를 열어야 합니다.

예:

```text
C:\Users\사용자명\Desktop\webProject
```

다음과 같은 구조가 VS Code의 탐색기 최상단에서 보여야 합니다.

```text
webProject/
├─ pom.xml
├─ README.md
└─ src/
```

`src` 폴더나 `src/main/java` 폴더만 따로 열면 안 됩니다.

반드시 `pom.xml`이 있는 폴더를 열어야 합니다.

---

# 12. VS Code Java 확장 프로그램

VS Code에서 다음 확장 프로그램 설치를 권장합니다.

```text
Extension Pack for Java
```

Maven 관련 기능을 사용하기 위해 Maven 확장 기능도 함께 설치합니다.

```text
Maven for Java
```

설치 후 VS Code를 재시작합니다.

---

# 13. 데이터베이스 설정

## 13.1 데이터베이스 생성

MySQL에 접속합니다.

프로젝트에서 사용하는 데이터베이스 이름에 맞게 생성합니다.

예:

```sql
CREATE DATABASE webProject
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

생성 후:

```sql
USE webProject;
```

> 실제 프로젝트에서 사용하는 DB 이름이 다르다면 해당 이름으로 변경해야 합니다.

---

## 13.2 테이블 생성

프로젝트에서 필요한 테이블을 생성해야 합니다.

프로젝트에 SQL 초기화 파일이 있다면 해당 SQL 파일을 실행합니다.

예:

```text
database/
└─ schema.sql
```

SQL 파일이 없다면 프로젝트에서 사용하는 테이블 구조에 맞게
MySQL에 직접 테이블을 생성해야 합니다.

테이블이 정상적으로 생성되었는지 다음 명령어로 확인할 수 있습니다.

```sql
SHOW TABLES;
```

---

# 14. DB 연결 정보 설정

DB 연결 설정 파일:

```text
src/main/webapp/config/dbconn.jsp
```

일반적인 형태:

```java
String url = "jdbc:mysql://localhost:3306/데이터베이스이름";
String user = "root";
String password = "비밀번호";
```

예:

```java
String url =
    "jdbc:mysql://localhost:3306/webProject?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";

String user = "root";
String password = "본인의 MySQL 비밀번호";
```

실제 환경에 맞게 다음 항목을 수정해야 합니다.

- 데이터베이스 이름
- MySQL 계정
- MySQL 비밀번호
- 포트 번호

---

# 15. 데이터베이스 비밀번호 주의

실제 MySQL 비밀번호를 GitHub에 그대로 업로드하지 않는 것이 좋습니다.

다음과 같은 개인 설정 파일은 Git에 포함하지 않는 것을 권장합니다.

```text
.env
*.local
dbconfig.local.jsp
```

현재 `dbconn.jsp`에 비밀번호가 직접 작성되어 있다면,
GitHub에 공개하기 전에 환경 변수 또는 별도의 로컬 설정 방식으로 분리하는 것을 권장합니다.

이미 실제 DB 비밀번호가 GitHub에 업로드된 경우에는
파일에서 삭제하는 것과 별개로 MySQL 비밀번호도 변경하는 것이 안전합니다.

---

# 16. Maven 프로젝트 빌드

Maven 명령어는 반드시 `pom.xml`이 있는 프로젝트 폴더에서 실행합니다.

예:

```cmd
cd C:\Users\사용자명\Desktop\webProject
```

그다음 빌드합니다.

```cmd
mvn clean package
```

테스트를 제외하고 빌드하려면:

```cmd
mvn clean package -DskipTests
```

정상적으로 완료되면:

```text
BUILD SUCCESS
```

가 출력됩니다.

빌드가 완료되면 다음과 같은 WAR 파일이 생성됩니다.

```text
target/
└─ webProject.war
```

---

# 17. Tomcat에 WAR 배포

Maven 빌드가 끝나면 생성된 WAR 파일을 Tomcat에 배포합니다.

생성된 WAR:

```text
프로젝트폴더\target\webProject.war
```

Tomcat 배포 위치:

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

---

# 18. Tomcat 실행

Tomcat의 `bin` 폴더로 이동합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
```

Tomcat을 시작합니다.

```cmd
startup.bat
```

Tomcat이 정상적으로 실행되면 웹 애플리케이션에 접속할 수 있습니다.

---

# 19. 웹 애플리케이션 접속

브라우저에서 다음 주소로 접속합니다.

```text
http://localhost:8080/webProject/
```

현재 `index.jsp`에서 웹툰 목록 페이지로 이동하도록 설정되어 있습니다.

웹툰 목록:

```text
http://localhost:8080/webProject/webtoon/webtoonList.jsp
```

로그인:

```text
http://localhost:8080/webProject/user/loginForm.jsp
```

회원가입:

```text
http://localhost:8080/webProject/user/registerForm.jsp
```

관리자 로그인:

```text
http://localhost:8080/webProject/admin/adminLogin.jsp
```

장바구니:

```text
http://localhost:8080/webProject/cart/cartList.jsp
```

---

# 20. 전체 실행 순서

처음 프로젝트를 실행하는 경우 아래 순서대로 진행합니다.

```text
JDK 설치
   ↓
Maven 설치
   ↓
Tomcat 10.1 설치
   ↓
MySQL 설치 및 실행
   ↓
GitHub에서 프로젝트 다운로드
   ↓
VS Code에서 webProject 폴더 열기
   ↓
MySQL 데이터베이스 생성
   ↓
필요한 테이블 생성
   ↓
dbconn.jsp DB 정보 설정
   ↓
mvn clean package
   ↓
target/webProject.war 생성
   ↓
Tomcat/webapps에 WAR 복사
   ↓
startup.bat 실행
   ↓
http://localhost:8080/webProject/
```

---

# 21. 코드 수정 후 다시 실행하는 방법

Java 또는 JSP 코드를 수정한 경우 WAR 파일을 다시 생성해야 합니다.

## 21.1 Maven 재빌드

프로젝트 폴더에서:

```cmd
cd C:\Users\사용자명\Desktop\webProject
```

다음 명령어 실행:

```cmd
mvn clean package
```

---

## 21.2 Tomcat 종료

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

---

## 21.3 WAR 파일 교체

새롭게 생성된:

```text
target\webProject.war
```

파일을 다음 위치로 복사합니다.

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

기존에 압축 해제된 다음 폴더가 있다면 삭제 후 다시 배포할 수 있습니다.

```text
C:\apache-tomcat-10.1.60\webapps\webProject\
```

---

## 21.4 Tomcat 다시 실행

```cmd
startup.bat
```

브라우저에서 다시 접속합니다.

```text
http://localhost:8080/webProject/
```

---

# 22. 자주 발생하는 오류

## 22.1 `mvn` 명령어를 찾을 수 없는 경우

오류:

```text
'mvn' is not recognized as an internal or external command
```

### 해결

Maven이 설치되어 있는지 확인합니다.

```cmd
mvn -version
```

Maven의 `bin` 폴더가 환경 변수 `Path`에 등록되어 있는지도 확인합니다.

환경 변수 수정 후 명령 프롬프트를 다시 실행합니다.

---

# 23. `invalid target release: 20` 오류

오류:

```text
invalid target release: 20
```

### 원인

프로젝트는 Java 20으로 컴파일하도록 설정되어 있는데
Maven이 Java 17 등의 낮은 버전을 사용하는 경우 발생합니다.

### 확인

```cmd
mvn -version
```

출력되는 Java 버전을 확인합니다.

### 해결

Maven에서 JDK 20을 사용하도록 설정합니다.

예:

```cmd
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"
```

다시 확인:

```cmd
mvn -version
```

Java version이 20인지 확인합니다.

---

# 24. `POM file does not exist` 오류

오류:

```text
POM file ... does not exist
```

### 원인

`pom.xml`이 없는 폴더에서 Maven 명령어를 실행한 경우 발생합니다.

### 잘못된 예

```cmd
cd C:\apache-tomcat-10.1.60\bin
mvn clean package
```

### 올바른 예

```cmd
cd C:\Users\사용자명\Desktop\webProject
mvn clean package
```

Maven은 반드시 `pom.xml`이 있는 프로젝트 폴더에서 실행해야 합니다.

---

# 25. VS Code에서 `jakarta.servlet` 오류가 발생하는 경우

다음과 같은 import에 빨간 밑줄이 표시될 수 있습니다.

```java
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
```

이 경우 먼저 `pom.xml`에 Jakarta Servlet API가 존재하는지 확인합니다.

```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>
```

현재 프로젝트는 해당 의존성을 `pom.xml`에 포함하고 있습니다.

---

## 25.1 VS Code에서 Java 환경 다시 불러오기

`pom.xml`이 정상적으로 설정되어 있는데도 빨간 밑줄이 남아 있다면
VS Code의 Java Language Server가 의존성을 아직 제대로 인식하지 못했을 수 있습니다.

다음 순서로 해결할 수 있습니다.

### 1. `pom.xml` 저장

```text
Ctrl + S
```

### 2. Java Language Server 초기화

VS Code에서:

```text
Ctrl + Shift + P
```

검색:

```text
Java: Clean Java Language Server Workspace
```

실행합니다.

### 3. VS Code 재시작

VS Code를 완전히 종료한 뒤 다시 실행합니다.

### 4. 프로젝트 최상위 폴더 확인

다음과 같이 `pom.xml`이 있는 폴더를 열어야 합니다.

```text
webProject/
├─ pom.xml
├─ README.md
└─ src/
```

---

# 26. Maven 의존성 확인

Maven이 Jakarta Servlet 라이브러리를 정상적으로 인식하는지 확인하려면:

```cmd
mvn dependency:tree
```

출력 결과에서 다음과 같은 항목을 확인할 수 있습니다.

```text
jakarta.servlet:jakarta.servlet-api:jar:6.0.0:provided
```

의존성 다운로드 자체를 확인하려면:

```cmd
mvn dependency:get -Dartifact=jakarta.servlet:jakarta.servlet-api:6.0.0
```

---

# 27. `localhost:8080` 접속이 되지 않는 경우

다음 항목을 순서대로 확인합니다.

1. Tomcat이 실행 중인지 확인
2. `startup.bat` 실행 여부 확인
3. Tomcat 로그 확인
4. 8080 포트를 다른 프로그램이 사용하고 있는지 확인
5. WAR 파일이 `webapps`에 존재하는지 확인

Tomcat 로그 위치:

```text
C:\apache-tomcat-10.1.60\logs
```

주요 로그:

```text
catalina.log
localhost.log
```

---

# 28. `/webProject/`에서 404 오류가 발생하는 경우

다음 항목을 확인합니다.

### WAR 파일 확인

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

### 배포 폴더 확인

```text
C:\apache-tomcat-10.1.60\webapps\webProject\
```

### index.jsp 확인

프로젝트에 다음 파일이 존재해야 합니다.

```text
src/main/webapp/index.jsp
```

현재 `index.jsp`는 프로젝트 접속 시 웹툰 목록 페이지로 이동하도록 설정되어 있습니다.

직접 접속:

```text
http://localhost:8080/webProject/webtoon/webtoonList.jsp
```

---

# 29. 데이터베이스 연결 오류

오류 예시:

```text
Communications link failure
```

또는:

```text
Access denied for user
```

### 확인 사항

- MySQL 서버가 실행 중인지 확인
- 데이터베이스 이름 확인
- MySQL 계정 확인
- 비밀번호 확인
- 포트 번호 확인
- JDBC URL 확인
- MySQL Connector/J가 정상적으로 포함되어 있는지 확인

JDBC URL 예시:

```java
jdbc:mysql://localhost:3306/webProject?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
```

---

# 30. Tomcat 10 관련 주의사항

본 프로젝트는 Tomcat 10.1과 Jakarta Servlet API를 사용합니다.

따라서 Servlet import는 다음과 같이 `jakarta.servlet`을 사용해야 합니다.

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

다음과 같이 `javax.servlet`을 사용하는 코드와 혼용하지 않습니다.

```java
import javax.servlet.*;
```

현재 프로젝트는 Jakarta Servlet 기반으로 작성되어 있으므로
Tomcat 10.1 환경에서 실행하는 것을 권장합니다.

---

# 31. GitHub 업로드 시 제외할 파일

다음 파일들은 GitHub에 업로드하지 않는 것을 권장합니다.

```gitignore
# Maven
target/

# Eclipse
.classpath
.project
.settings/

# VS Code
.vscode/

# Environment / Local settings
.env
*.local
dbconfig.local.jsp

# Logs
*.log

# OS files
.DS_Store
Thumbs.db

# Temporary files
*.tmp
```

---

# 32. GitHub에 프로젝트 업로드

처음 GitHub에 업로드하는 경우:

```cmd
git init
```

GitHub 저장소 연결:

```cmd
git remote add origin https://github.com/사용자명/저장소명.git
```

파일 추가:

```cmd
git add .
```

커밋:

```cmd
git commit -m "Initial project setup"
```

기본 브랜치 설정:

```cmd
git branch -M main
```

GitHub 업로드:

```cmd
git push -u origin main
```

---

# 33. GitHub에 업데이트하는 방법

코드를 수정한 후 변경사항을 업로드하려면:

```cmd
git add .
git commit -m "Update project"
git push
```

예:

```cmd
git add .
git commit -m "Fix webtoon list page"
git push
```

---

# 34. 실행에 필요한 환경 요약

다른 사람이 GitHub에서 프로젝트를 받아 실행하기 위해 필요한 환경은 다음과 같습니다.

```text
JDK 20
   +
Maven 3.9.x
   +
Apache Tomcat 10.1.x
   +
MySQL 8.0+
   +
Git
   +
Visual Studio Code
```

프로젝트의 Java 라이브러리는 `pom.xml`을 통해 Maven이 자동으로 다운로드합니다.

따라서 프로젝트를 clone한 뒤 Maven 빌드를 수행하면
필요한 Jakarta Servlet, JSTL, MySQL Connector 등의 라이브러리가 자동으로 준비됩니다.

---

# 35. 가장 빠른 실행 방법

필수 프로그램이 모두 설치되어 있다는 기준으로 다음 순서로 실행할 수 있습니다.

### 1. 프로젝트 다운로드

```cmd
git clone https://github.com/사용자명/저장소명.git
cd webProject
```

### 2. DB 설정

MySQL을 실행한 후 데이터베이스를 생성하고

```text
src/main/webapp/config/dbconn.jsp
```

의 DB 정보를 자신의 환경에 맞게 수정합니다.

### 3. Maven 빌드

```cmd
mvn clean package
```

### 4. WAR 배포

생성된:

```text
target/webProject.war
```

파일을:

```text
C:\apache-tomcat-10.1.60\webapps\
```

에 복사합니다.

### 5. Tomcat 실행

```cmd
cd C:\apache-tomcat-10.1.60\bin
startup.bat
```

### 6. 브라우저 접속

```text
http://localhost:8080/webProject/
```

---

# 36. 프로젝트 접속 URL

| 기능 | URL |
|---|---|
| 메인 | http://localhost:8080/webProject/ |
| 웹툰 목록 | http://localhost:8080/webProject/webtoon/webtoonList.jsp |
| 로그인 | http://localhost:8080/webProject/user/loginForm.jsp |
| 회원가입 | http://localhost:8080/webProject/user/registerForm.jsp |
| 관리자 로그인 | http://localhost:8080/webProject/admin/adminLogin.jsp |
| 장바구니 | http://localhost:8080/webProject/cart/cartList.jsp |

---

# 37. Tomcat 종료

Tomcat을 종료하려면 다음 명령어를 실행합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

---

# 38. 현재 프로젝트의 기본 실행 구조

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

Maven은 다음과 같은 역할을 담당합니다.

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

# 39. 참고

프로젝트를 처음 실행할 때 오류가 발생한다면 다음 항목을 우선 확인합니다.

```text
1. Java 버전
2. Maven Java 버전
3. pom.xml
4. MySQL 실행 여부
5. dbconn.jsp 설정
6. WAR 파일 생성 여부
7. Tomcat webapps 배포 여부
8. Tomcat 로그
```

특히 Maven 빌드 오류가 발생하면 다음 명령어의 결과를 먼저 확인합니다.

```cmd
mvn -version
```

정상적인 Maven 빌드 여부는 다음 명령어로 확인합니다.

```cmd
mvn clean package
```

정상적으로 완료되면:

```text
BUILD SUCCESS
```

가 표시됩니다.
