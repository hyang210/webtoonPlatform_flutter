package webtoon.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig; // 🚨 파일 업로드 처리를 위한 어노테이션
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part; // 파일 파트를 처리하는 객체
import webtoon.dao.WebtoonDAO;
import webtoon.vo.WebtoonVO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/WebtoonRegController")
// 파일 업로드 설정: maxFileSize(파일 하나 최대 크기), maxRequestSize(전체 요청 최대 크기)
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 5,   // 5MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class WebtoonRegController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // 결과 플래그
        boolean success = false;
        String fileName = null;
        String thumbnailPath = null;
        
        try {
            // 1. 파일 저장 경로 설정
            // 프로젝트 내의 'uploads' 폴더 절대 경로를 가져옵니다.
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + "uploads";
            
            // uploads 폴더가 없으면 생성
            File fileSaveDir = new File(uploadFilePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }

            // 2. 썸네일 파일 처리
            Part filePart = request.getPart("thumbnailFile");
            fileName = filePart.getSubmittedFileName();

            if (fileName != null && !fileName.isEmpty()) {
                // 파일명 중복 방지를 위해 타임스탬프를 추가 (년월일시분초_원본파일명)
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String saveFileName = timeStamp + "_" + fileName;
                
                // 파일 저장
                filePart.write(uploadFilePath + File.separator + saveFileName);
                
                // DB에 저장할 상대 경로 설정 (webapp/uploads/...)
                thumbnailPath = "uploads" + File.separator + saveFileName;
                
                // 윈도우 환경에서 경로 구분자를 웹에서 쓸 수 있도록 '/'로 변환
                thumbnailPath = thumbnailPath.replace(File.separatorChar, '/');
            }

            // 3. 폼 데이터 추출
            WebtoonVO webtoon = new WebtoonVO();
            
            webtoon.setKoTitle(request.getParameter("koTitle"));
            webtoon.setEnTitle(request.getParameter("enTitle"));
            webtoon.setPlatform(request.getParameter("platform"));
            webtoon.setAuthor(request.getParameter("author"));
            
            webtoon.setGenre(request.getParameter("genre"));
            webtoon.setThumbnailPath(thumbnailPath); // 파일 저장 경로 설정

            // 4. WebtoonDAO를 통해 DB에 저장
            WebtoonDAO dao = new WebtoonDAO();
            success = dao.insertWebtoon(webtoon);

        } catch (Exception e) {
            System.err.println("웹툰 등록 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            success = false;
        }

        // 5. 결과에 따라 webtoonRegForm.jsp로 리다이렉트
        String resultParam = success ? "success" : "fail";
        response.sendRedirect(request.getContextPath() + "/webtoon/webtoonRegForm.jsp?result=" + resultParam);
    }
}