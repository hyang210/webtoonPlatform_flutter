package webtoon.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import webtoon.dao.UserDAO;

// 이 애너테이션은 파일 업로드를 위해 필수입니다.
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/ProfileUpController")
public class ProfileUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 🚨 실제 저장 경로로 변경해야 합니다. (Tomcat 배포 경로 외부 권장)
    private static final String UPLOAD_DIR = "C:\\webtoon_uploads\\profile"; 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String userId = (session != null) ? (String) session.getAttribute("userId") : null;
        
        // 1. 로그인 체크 (myPage.jsp에서 이미 막지만, 안전을 위해 서버에서 다시 체크)
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=needLogin");
            return;
        }

        // 2. 저장 폴더 준비
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uploadResult = "fail"; // 기본 결과는 실패

        try {
            // 3. 파일 파트 가져오기 (myPage.jsp 폼의 name="profileFile"과 일치)
            Part filePart = request.getPart("profileFile");
            String fileName = filePart.getSubmittedFileName();

            if (fileName != null && !fileName.isEmpty()) {
                // 4. 파일명 중복 방지를 위해 사용자 ID와 조합 (또는 UUID 사용)
                String newFileName = userId + "_" + System.currentTimeMillis() + "_" + fileName;
                String filePath = UPLOAD_DIR + File.separator + newFileName;

                // 5. 파일 저장
                filePart.write(filePath);
                
                // 6. DB 및 세션 업데이트
                String webPath = request.getContextPath() + "/profile_images/" + newFileName; // DB에 저장할 웹 경로
                
                UserDAO userDAO = new UserDAO(); 
                boolean dbUpdateSuccess = userDAO.updateProfilePath(userId, webPath);
                
                if (dbUpdateSuccess) {
                    // 2단계: DB 업데이트 성공 시 세션 업데이트
                    session.setAttribute("profilePath", webPath); 
                    uploadResult = "success";
                } else {
                    // DB 업데이트 실패 시 처리
                    uploadResult = "fail"; 
                    System.err.println("DB 프로필 경로 업데이트 실패!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("파일 업로드 처리 중 오류 발생: " + e.getMessage());
            uploadResult = "fail";
        }
        
        // 7. 마이페이지로 결과 리다이렉트 (myPage.jsp에서 param.uploadResult를 체크함)
        response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=" + uploadResult);
    }
}