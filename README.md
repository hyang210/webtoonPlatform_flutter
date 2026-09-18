# Webtoon Web Project

JSP와 Servlet을 기반으로 구현한 웹툰 웹 애플리케이션입니다.

사용자는 웹툰 목록을 조회하고, 웹툰 상세 정보를 확인하며, 회원가입·로그인·장바구니 등의 기능을 사용할 수 있습니다.

관리자는 관리자 페이지를 통해 웹툰을 등록하고 관리할 수 있습니다.

---

## 1. 주요 기능

### 사용자 기능

- 회원가입
- 로그인 및 로그아웃
- 웹툰 목록 조회
- 웹툰 상세 정보 조회
- 장바구니 추가 및 조회
- 마이페이지

### 관리자 기능

- 관리자 로그인
- 웹툰 등록
- 웹툰 관리

---

## 2. 개발 환경

| 항목 | 버전 |
|---|---|
| Java | JDK 20 이상 권장 |
| Servlet | Jakarta Servlet 6.0 |
| Web Server | Apache Tomcat 10.1 |
| Build Tool | Apache Maven 3.9 이상 |
| Database | MySQL 8.0 이상 |
| Frontend | JSP, HTML, CSS, JavaScript |
| Backend | Java Servlet |
| IDE | Visual Studio Code 또는 Eclipse |

> 본 프로젝트는 `jakarta.servlet.*` 패키지를 사용하므로 Apache Tomcat 10.1 환경을 권장합니다.

---

## 3. 프로젝트 구조

```text
webProject/
├─ pom.xml
├─ README.md
├─ .gitignore
│
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ Java 소스 코드
│     │
│     └─ webapp/
│        ├─ index.jsp
│        │
│        ├─ admin/
│        │  ├─ adminLogin.jsp
│        │  ├─ webtoonNewRegForm.jsp
│        │  └─ ...
│        │
│        ├─ cart/
│        │  ├─ cart.jsp
│        │  ├─ cartList.jsp
│        │  └─ ...
│        │
│        ├─ common/
│        │  ├─ header.jsp
│        │  ├─ footer.jsp
│        │  └─ ...
│        │
│        ├─ config/
│        │  └─ dbconn.jsp
│        │
│        ├─ user/
│        │  ├─ loginForm.jsp
│        │  ├─ registerForm.jsp
│        │  ├─ myPage.jsp
│        │  └─ ...
│        │
│        └─ webtoon/
│           ├─ webtoonList.jsp
│           ├─ webtoonDetail.jsp
│           ├─ webtoonRegForm.jsp
│           └─ ...
│
└─ target/
   └─ webProject.war
```

`target/` 폴더는 Maven 빌드 과정에서 자동으로 생성되는 폴더이므로 GitHub에 업로드하지 않습니다.

---

## 4. 실행 전 필수 설치

프로젝트를 실행하기 전에 다음 프로그램을 설치해야 합니다.

1. JDK 20 이상
2. Apache Maven 3.9 이상
3. Apache Tomcat 10.1
4. MySQL 8.0 이상
5. Git
6. Visual Studio Code 또는 Eclipse

---

## 5. JDK 설치 및 확인

JDK 20 이상을 설치합니다.

설치가 완료되면 명령 프롬프트에서 다음 명령어를 실행합니다.

```cmd
java -version
```

Java 컴파일러도 확인합니다.

```cmd
javac -version
```

예시 출력:

```text
java version "20..."
javac 20...
```

Maven에서 사용하는 Java 버전도 확인해야 합니다.

```cmd
mvn -version
```

출력 결과에 표시되는 `Java version`이 JDK 20 이상인지 확인합니다.

```text
Java version: 20...
```

> `java -version`과 `mvn -version`에서 사용하는 Java 버전이 서로 다를 수 있습니다. Maven 빌드 오류가 발생하면 `mvn -version`의 Java 버전을 먼저 확인해야 합니다.

---

## 6. Maven 설치 및 확인

Apache Maven 3.9 이상을 설치합니다.

설치 후 다음 명령어를 실행합니다.

```cmd
mvn -version
```

정상적으로 설치되었다면 다음과 비슷한 결과가 출력됩니다.

```text
Apache Maven 3.9.x
Maven home: ...
Java version: 20...
```

Maven은 `pom.xml`에 정의된 라이브러리를 자동으로 다운로드하고 프로젝트를 빌드하는 데 사용됩니다.

---

## 7. Tomcat 설치

Apache Tomcat 10.1을 설치합니다.

예시 설치 경로는 다음과 같습니다.

```text
C:\apache-tomcat-10.1.60
```

Tomcat 설치 폴더에는 다음과 같은 디렉터리가 있어야 합니다.

```text
C:\apache-tomcat-10.1.60\
├─ bin/
├─ conf/
├─ lib/
├─ logs/
├─ webapps/
└─ ...
```

Tomcat 10.1은 Jakarta Servlet을 사용하므로, 프로젝트에서 사용하는 `jakarta.servlet.*` 패키지와 호환됩니다.

---

## 8. MySQL 설치

MySQL 8.0 이상을 설치합니다.

MySQL을 실행한 뒤 다음 정보를 확인합니다.

- MySQL 호스트
- MySQL 포트
- MySQL 사용자 이름
- MySQL 비밀번호
- 프로젝트 데이터베이스 이름

일반적인 기본 설정은 다음과 같습니다.

| 항목 | 기본값 |
|---|---|
| Host | `localhost` |
| Port | `3306` |
| User | `root` |
| Database | 프로젝트에서 사용하는 데이터베이스 이름 |

---

## 9. GitHub에서 프로젝트 다운로드

Git이 설치되어 있다면 다음 명령어를 사용하여 프로젝트를 다운로드할 수 있습니다.

```cmd
git clone https://github.com/사용자명/저장소명.git
```

프로젝트 폴더로 이동합니다.

```cmd
cd 저장소명
```

예시:

```cmd
cd webProject
```

GitHub 페이지에서 직접 다운로드하려면 다음 순서로 진행합니다.

1. GitHub 저장소 접속
2. `Code` 버튼 클릭
3. `Download ZIP` 선택
4. 압축 해제
5. 프로젝트 폴더 열기

---

## 10. 데이터베이스 설정

### 10.1 데이터베이스 생성

MySQL에 접속한 후 프로젝트에서 사용하는 데이터베이스를 생성합니다.

예시:

```sql
CREATE DATABASE webProject
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

데이터베이스 이름은 프로젝트의 `dbconn.jsp`에 설정된 이름과 동일해야 합니다.

```sql
USE webProject;
```

> 실제 프로젝트에서 사용하는 데이터베이스 이름이 `webProject`와 다르다면, 해당 이름에 맞게 수정해야 합니다.

---

### 10.2 테이블 생성

프로젝트에 데이터베이스 초기화 SQL 파일이 포함되어 있다면 해당 파일을 실행합니다.

예시 파일 위치:

```text
database/schema.sql
```

MySQL Workbench에서 다음 순서로 실행할 수 있습니다.

1. MySQL Workbench 실행
2. MySQL 서버 접속
3. `schema.sql` 파일 열기
4. SQL 문 실행
5. 테이블 생성 여부 확인

테이블 생성 여부는 다음 명령어로 확인할 수 있습니다.

```sql
SHOW TABLES;
```

만약 `schema.sql` 파일이 없다면, 프로젝트에서 사용하는 테이블을 직접 생성해야 합니다.

---

### 10.3 데이터베이스 연결 정보 설정

데이터베이스 연결 정보는 다음 파일에서 확인할 수 있습니다.

```text
src/main/webapp/config/dbconn.jsp
```

일반적인 데이터베이스 연결 정보는 다음과 같은 형태입니다.

```java
String url = "jdbc:mysql://localhost:3306/데이터베이스이름";
String user = "root";
String password = "MySQL비밀번호";
```

본인의 MySQL 환경에 맞게 수정해야 합니다.

예시:

```java
String url = "jdbc:mysql://localhost:3306/webProject?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
String user = "root";
String password = "본인의 MySQL 비밀번호";
```

### 데이터베이스 설정 시 확인할 사항

- MySQL 서버가 실행 중인지 확인
- 데이터베이스 이름이 올바른지 확인
- MySQL 사용자 이름이 올바른지 확인
- MySQL 비밀번호가 올바른지 확인
- 포트 번호가 올바른지 확인
- JDBC URL이 올바른지 확인

### 보안 주의사항

실제 데이터베이스 비밀번호를 GitHub에 업로드하지 않는 것을 권장합니다.

다음과 같은 민감한 파일은 GitHub에 업로드하지 않아야 합니다.

```text
.env
dbconfig.local.jsp
application-local.properties
```

이미 실제 비밀번호가 GitHub에 업로드되었다면, GitHub 파일에서 삭제하는 것뿐만 아니라 MySQL 비밀번호도 변경하는 것이 안전합니다.

---

## 11. Maven으로 프로젝트 빌드

Maven 명령어는 반드시 `pom.xml`이 있는 프로젝트 최상위 폴더에서 실행해야 합니다.

먼저 프로젝트 폴더로 이동합니다.

```cmd
cd C:\Users\사용자명\Desktop\webProject
```

`pom.xml`이 있는지 확인합니다.

```cmd
dir pom.xml
```

그다음 다음 명령어를 실행합니다.

```cmd
mvn clean package
```

테스트를 제외하고 빌드하려면 다음 명령어를 사용할 수 있습니다.

```cmd
mvn clean package -DskipTests
```

빌드가 정상적으로 완료되면 다음과 같은 메시지가 출력됩니다.

```text
BUILD SUCCESS
```

빌드가 완료되면 `target` 폴더 안에 WAR 파일이 생성됩니다.

```text
target/
└─ webProject.war
```

---

## 12. Tomcat에 프로젝트 배포

### 12.1 Tomcat 종료

기존 Tomcat이 실행 중이라면 먼저 종료합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

---

### 12.2 기존 배포 파일 확인

Tomcat의 `webapps` 폴더로 이동합니다.

```text
C:\apache-tomcat-10.1.60\webapps
```

기존에 배포된 프로젝트가 있다면 다음 항목을 확인합니다.

```text
webProject.war
webProject/
```

기존 파일이 있다면 Tomcat을 종료한 후 삭제하거나 새 WAR 파일로 교체합니다.

---

### 12.3 WAR 파일 복사

Maven 빌드로 생성된 WAR 파일을 Tomcat의 `webapps` 폴더에 복사합니다.

원본 파일:

```text
프로젝트폴더\target\webProject.war
```

복사 위치:

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

최종적으로 다음과 같은 형태가 됩니다.

```text
C:\apache-tomcat-10.1.60\webapps\
├─ webProject.war
└─ ...
```

Tomcat은 `webapps` 폴더에 있는 WAR 파일을 자동으로 웹 애플리케이션으로 배포합니다.

WAR 파일 이름이 `webProject.war`인 경우 기본 접속 경로는 다음과 같습니다.

```text
/webProject
```

---

## 13. Tomcat 실행

Tomcat의 `bin` 폴더로 이동합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
```

Tomcat을 실행합니다.

```cmd
startup.bat
```

정상적으로 실행되면 Tomcat 서버가 시작됩니다.

---

## 14. 웹 브라우저 접속

Tomcat이 정상적으로 실행되었다면 웹 브라우저에서 다음 주소로 접속합니다.

### 메인 페이지

```text
http://localhost:8080/webProject/
```

`index.jsp`에서 웹툰 목록 페이지로 이동하도록 설정되어 있다면 메인 주소 접속 시 웹툰 목록 페이지로 이동합니다.

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

## 15. Tomcat 종료

Tomcat을 종료하려면 다음 명령어를 실행합니다.

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

---

## 16. 코드 수정 후 재배포

JSP 또는 Java 코드를 수정한 경우 다음 순서로 다시 배포합니다.

### 16.1 프로젝트 폴더로 이동

```cmd
cd C:\Users\사용자명\Desktop\webProject
```

### 16.2 Maven 재빌드

```cmd
mvn clean package
```

### 16.3 Tomcat 종료

```cmd
cd C:\apache-tomcat-10.1.60\bin
shutdown.bat
```

### 16.4 WAR 파일 교체

새로 생성된 WAR 파일을 Tomcat의 `webapps` 폴더에 복사합니다.

```text
프로젝트폴더\target\webProject.war
```

복사 위치:

```text
C:\apache-tomcat-10.1.60\webapps\webProject.war
```

필요한 경우 기존에 압축 해제된 폴더도 삭제합니다.

```text
C:\apache-tomcat-10.1.60\webapps\webProject\
```

### 16.5 Tomcat 재실행

```cmd
startup.bat
```

---

## 17. 실행 과정 요약

처음 실행하는 경우 다음 순서대로 진행합니다.

```text
1. JDK 설치
   ↓
2. Maven 설치
   ↓
3. Tomcat 10.1 설치
   ↓
4. MySQL 설치 및 실행
   ↓
5. GitHub에서 프로젝트 다운로드
   ↓
6. MySQL 데이터베이스 생성
   ↓
7. 테이블 생성 SQL 실행
   ↓
8. dbconn.jsp 데이터베이스 정보 설정
   ↓
9. 프로젝트 폴더에서 Maven 빌드
   ↓
10. 생성된 WAR 파일을 Tomcat webapps에 복사
   ↓
11. Tomcat 실행
   ↓
12. 웹 브라우저 접속
```

---

## 18. 한 번에 실행하는 명령어 예시

아래 명령어는 프로젝트를 빌드하고 WAR 파일을 Tomcat에 복사한 뒤 Tomcat을 재실행하는 예시입니다.

> 아래 경로는 예시이므로 자신의 프로젝트 경로와 Tomcat 설치 경로에 맞게 수정해야 합니다.

```cmd
cd C:\Users\사용자명\Desktop\webProject

mvn clean package

copy /Y target\webProject.war C:\apache-tomcat-10.1.60\webapps\webProject.war

cd C:\apache-tomcat-10.1.60\bin

shutdown.bat

timeout /t 3

startup.bat
```

처음 실행할 때는 오류 발생 여부를 확인하기 위해 Maven 빌드와 Tomcat 실행을 각각 진행하는 것을 권장합니다.

---

## 19. 자주 발생하는 오류

### 19.1 `mvn` 명령어를 찾을 수 없는 경우

오류 예시:

```text
'mvn' is not recognized as an internal or external command
```

#### 해결 방법

1. Maven이 설치되어 있는지 확인합니다.
2. Maven의 `bin` 폴더를 시스템 환경 변수 `Path`에 추가합니다.
3. 명령 프롬프트를 다시 실행합니다.
4. 다음 명령어로 확인합니다.

```cmd
mvn -version
```

---

### 19.2 `invalid target release` 오류

오류 예시:

```text
invalid target release: 20
```

#### 원인

Maven이 Java 17 등의 낮은 버전을 사용하고 있는데, `pom.xml`에서는 Java 20으로 컴파일하도록 설정되어 있을 때 발생합니다.

#### 확인 방법

```cmd
mvn -version
```

출력되는 `Java version`을 확인합니다.

#### 해결 방법

Maven이 JDK 20 이상을 사용하도록 `JAVA_HOME`을 설정합니다.

Windows 명령 프롬프트 예시:

```cmd
set "JAVA_HOME=C:\Program Files\Java\jdk-20"
set "PATH=%JAVA_HOME%\bin;%PATH%"
```

다시 확인합니다.

```cmd
mvn -version
```

---

### 19.3 `POM file does not exist` 오류

오류 예시:

```text
POM file ... does not exist
```

#### 원인

`pom.xml`이 없는 폴더에서 Maven 명령어를 실행했을 때 발생합니다.

#### 잘못된 실행 위치

```cmd
cd C:\apache-tomcat-10.1.60\bin
mvn clean package
```

#### 올바른 실행 위치

```cmd
cd C:\Users\사용자명\Desktop\webProject
mvn clean package
```

Maven은 반드시 `pom.xml`이 있는 프로젝트 폴더에서 실행해야 합니다.

---

### 19.4 `localhost:8080` 접속이 되지 않는 경우

다음 사항을 확인합니다.

1. Tomcat이 실행 중인지 확인합니다.
2. `startup.bat`이 정상 실행되었는지 확인합니다.
3. Tomcat 로그를 확인합니다.
4. 8080 포트를 다른 프로그램이 사용하고 있는지 확인합니다.
5. 방화벽에 의해 접속이 차단되지 않았는지 확인합니다.

Tomcat 로그 위치:

```text
C:\apache-tomcat-10.1.60\logs
```

확인할 수 있는 로그 파일 예시:

```text
catalina.log
localhost.log
```

---

### 19.5 `/webProject/` 접속 시 404 오류가 발생하는 경우

다음 사항을 확인합니다.

1. WAR 파일 이름이 `webProject.war`인지 확인합니다.
2. WAR 파일이 Tomcat의 `webapps` 폴더에 있는지 확인합니다.
3. Tomcat이 정상적으로 실행되었는지 확인합니다.
4. `webProject` 폴더가 자동으로 생성되었는지 확인합니다.
5. `src/main/webapp/index.jsp` 파일이 존재하는지 확인합니다.

직접 웹툰 목록 페이지로 접속할 수도 있습니다.

```text
http://localhost:8080/webProject/webtoon/webtoonList.jsp
```

---

### 19.6 데이터베이스 연결 오류

오류 예시:

```text
Communications link failure
```

또는

```text
Access denied for user
```

#### 확인 사항

- MySQL 서버가 실행 중인지 확인
- 데이터베이스 이름 확인
- MySQL 포트 확인
- MySQL 사용자 이름 확인
- MySQL 비밀번호 확인
- `dbconn.jsp`의 JDBC URL 확인
- MySQL Connector/J 의존성 확인

일반적인 JDBC URL 예시:

```java
jdbc:mysql://localhost:3306/webProject?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
```

---

### 19.7 Tomcat 10에서 Servlet 관련 오류가 발생하는 경우

다음 사항을 확인합니다.

- Tomcat 버전이 10.1인지 확인
- 코드의 import가 `jakarta.servlet.*`인지 확인
- `javax.servlet.*`와 `jakarta.servlet.*`를 혼용하지 않았는지 확인
- `pom.xml`의 Servlet API 버전 확인

본 프로젝트는 Jakarta 패키지를 사용합니다.

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

---

## 20. GitHub 업로드 시 제외할 파일

다음 파일과 폴더는 GitHub에 업로드하지 않는 것을 권장합니다.

```gitignore
# Maven build
target/

# Eclipse
.classpath
.project
.settings/

# VS Code
.vscode/

# Environment and local configuration
.env
*.local
dbconfig.local.jsp

# Logs
*.log

# OS generated files
.DS_Store
Thumbs.db

# Temporary files
*.tmp
```

프로젝트 최상위 폴더에 `.gitignore` 파일을 생성하고 위 내용을 추가합니다.

---

## 21. 접속 주소 정리

| 기능 | URL |
|---|---|
| 프로젝트 메인 | `http://localhost:8080/webProject/` |
| 웹툰 목록 | `http://localhost:8080/webProject/webtoon/webtoonList.jsp` |
| 로그인 | `http://localhost:8080/webProject/user/loginForm.jsp` |
| 회원가입 | `http://localhost:8080/webProject/user/registerForm.jsp` |
| 관리자 로그인 | `http://localhost:8080/webProject/admin/adminLogin.jsp` |
| 장바구니 | `http://localhost:8080/webProject/cart/cartList.jsp` |

---

## 22. 참고 사항

- 본 프로젝트는 Java, Maven, Tomcat, MySQL이 설치되어 있어야 실행할 수 있습니다.
- MySQL 데이터베이스와 테이블이 먼저 생성되어 있어야 합니다.
- `dbconn.jsp`의 데이터베이스 연결 정보는 각 실행 환경에 맞게 수정해야 합니다.
- Java 버전은 `pom.xml`에 설정된 버전과 일치해야 합니다.
- Tomcat 10.1 이상 사용을 권장합니다.
- 실제 운영 환경에서는 데이터베이스 비밀번호를 코드에 직접 작성하지 않는 것이 좋습니다.
