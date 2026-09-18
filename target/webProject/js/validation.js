

// registerForm.jsp의 onsubmit 이벤트에서 호출되는 메인 검사 함수
function validateForm() {
    // 폼 요소 가져오기
    const userId = document.getElementById('userId').value;
    const userPw = document.getElementById('userPw').value;
    const confirmPw = document.getElementById('confirmPw').value;
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    
    let isValid = true;

    // 1. 아이디 유효성 및 중복 검사 (길이, 형식)
    if (!checkIdFormat(userId)) {
        isValid = false;
    }
    // 중복 확인은 일반적으로 서버 측 AJAX를 통해 처리해야 하나, 여기서는 클라이언트 검사만 포함
    
    // 2. 비밀번호 유효성 검사 (길이)
    if (!checkPassword(userPw)) {
        isValid = false;
    }
    
    // 3. 비밀번호 일치 확인
    if (!checkPasswordMatch(userPw, confirmPw)) {
        isValid = false;
    }

    // 4. 이름과 이메일은 기본적으로 required 속성으로 검사되지만, 
    //    여기서 추가적인 패턴 검사를 할 수 있음 (예: 이메일 형식)
    if (!checkEmailFormat(email)) {
        isValid = false;
    }

    if (!isValid) {
        alert("입력 정보를 다시 확인해주세요.");
        return false; // 폼 제출 방지
    }

    return true; // 폼 제출 허용
}

/**
 * 아이디 형식 검사: 4~12자의 영문 소문자/숫자만 허용
 * @param {string} id 검사할 아이디 문자열
 * @returns {boolean} 유효하면 true
 */
function checkIdFormat(id) {
    const idRegex = /^[a-z0-9]{4,12}$/;
    const messageElement = document.getElementById('idMessage');
    
    if (id.length < 4 || id.length > 12) {
        messageElement.textContent = "아이디는 4~12자여야 합니다.";
        return false;
    }
    
    if (!idRegex.test(id)) {
        messageElement.textContent = "아이디는 영문 소문자 또는 숫자만 사용 가능합니다.";
        return false;
    }
    
    messageElement.textContent = ""; // 메시지 초기화
    return true;
}

/**
 * 비밀번호 형식 검사: 최소 8자 이상
 * @param {string} pw 검사할 비밀번호 문자열
 * @returns {boolean} 유효하면 true
 */
function checkPassword(pw) {
    if (pw.length < 8) {
        return false;
    }
    return true;
}

/**
 * 비밀번호와 확인 비밀번호 일치 검사
 * @param {string} pw 비밀번호
 * @param {string} confirmPw 확인 비밀번호
 * @returns {boolean} 일치하면 true
 */
function checkPasswordMatch(pw, confirmPw) {
    const messageElement = document.getElementById('pwMatchMessage');
    
    if (pw !== confirmPw) {
        messageElement.textContent = "비밀번호가 일치하지 않습니다.";
        return false;
    }
    
    messageElement.textContent = "";
    return true;
}

/**
 * 이메일 형식 검사 (간단한 패턴)
 * @param {string} email 검사할 이메일 문자열
 * @returns {boolean} 유효하면 true
 */
function checkEmailFormat(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    if (!emailRegex.test(email)) {
        // 이메일 필드 아래에 에러 메시지 표시 로직 추가 가능
        return false;
    }
    return true;
}

document.addEventListener('DOMContentLoaded', function() {
    const userIdInput = document.getElementById('userId');
    const userPwInput = document.getElementById('userPw');
    const confirmPwInput = document.getElementById('confirmPw');

    if (userIdInput) {
        userIdInput.addEventListener('blur', function() {
            checkIdFormat(this.value);
        });
    }

    if (confirmPwInput) {
        confirmPwInput.addEventListener('keyup', function() {
            checkPasswordMatch(userPwInput.value, this.value);
        });
    }
});