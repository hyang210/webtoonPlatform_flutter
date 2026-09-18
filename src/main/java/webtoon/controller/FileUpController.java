package webtoon.controller;

import webtoon.dao.WebtoonDAO;
import webtoon.vo.WebtoonVO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;     
import jakarta.servlet.http.HttpServlet;           
import jakarta.servlet.http.HttpServletRequest;     
import jakarta.servlet.http.HttpServletResponse;   
import jakarta.servlet.http.Part;                   

@WebServlet("/FileUpController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1MB
    maxFileSize = 1024 * 1024 * 5,       // 5MB
    maxRequestSize = 1024 * 1024 * 10    // 10MB
)
public class FileUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 인코딩 설정 (Servlet 3.0+ 표준)
        request.setCharacterEncoding("UTF-8");

        // 1. 파일 저장 경로 설정 (웹 애플리케이션의 'uploads' 폴더)
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + "uploads";
        
        // 파일 업로드 폴더가 없으면 생성
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String koTitle = request.getParameter("koTitle");
        String enTitle = request.getParameter("enTitle");
        String platform = request.getParameter("platform");
        String author = request.getParameter("author");
        
        String fileName = null;
        String thumbnailPath = null;
        
        try {
            // 2. 파일 파트 획득: <input type="file" name="thumbnailFile">
            Part filePart = request.getPart("thumbnailFile");
            fileName = filePart.getSubmittedFileName(); // 사용자가 업로드한 파일 이름
            
            if (fileName != null && !fileName.isEmpty()) {
                // 3. 파일 저장 로직
                // 파일명 중복 방지를 위해 실제 저장할 파일명에 타임스탬프 등을 추가하는 로직이 필요하지만, 여기서는 간단히 처리
                Path filePath = uploadDir.resolve(fileName);
                
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
                
                // 4. DB에 저장할 상대 경로: /uploads/실제파일명
                thumbnailPath = request.getContextPath() + "/uploads/" + fileName; 
            }
            
            // 5. VO 객체에 데이터 설정
            WebtoonVO webtoon = new WebtoonVO();
            webtoon.setKoTitle(koTitle);
            webtoon.setEnTitle(enTitle);
            webtoon.setPlatform(platform);
            webtoon.setAuthor(author);
            webtoon.setThumbnailPath(thumbnailPath); 

            // 6. DAO를 이용하여 DB에 웹툰 정보 저장 (DB 저장 로직 호출 가정)
            WebtoonDAO dao = new WebtoonDAO();
            // dao.insertWebtoon(webtoon); 
            
            // 7. 성공 메시지 출력 후 목록 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/WebtoonListController?msg=success");
            
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 파일 삭제 로직 추가 가능
            response.sendRedirect(request.getContextPath() + "/admin/webtoonRegForm.jsp?msg=fail");
        }
    }
}