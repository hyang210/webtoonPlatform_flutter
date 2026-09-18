package webtoon.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import webtoon.dao.UserDAO;
import webtoon.vo.UserVO;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
@WebServlet("/ProfileUpController")
public class ProfileUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=needLogin");
            return;
        }

        String uploadRoot = request.getServletContext().getRealPath("/uploads/profile");
        if (uploadRoot == null) {
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
            return;
        }

        File uploadDir = new File(uploadRoot);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
            return;
        }

        Part filePart;
        try {
            filePart = request.getPart("profileFile");
        } catch (ServletException e) {
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
            return;
        }

        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
            return;
        }

        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
            return;
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName())
                .getFileName()
                .toString();

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex).toLowerCase();
        }

        String newFileName = UUID.randomUUID() + extension;
        File savedFile = new File(uploadDir, newFileName);

        try {
            filePart.write(savedFile.getAbsolutePath());

            // DB에는 프로젝트 기준 상대 경로만 저장합니다.
            String webPath = "uploads/profile/" + newFileName;

            UserDAO userDAO = new UserDAO();
            boolean dbUpdateSuccess = userDAO.updateProfilePath(userId, webPath);

            if (!dbUpdateSuccess) {
                savedFile.delete();
                response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
                return;
            }

            session.setAttribute("profilePath", webPath);

            Object loginUserObject = session.getAttribute("loginUser");
            if (loginUserObject instanceof UserVO) {
                UserVO loginUser = (UserVO) loginUserObject;
                loginUser.setProfilePath(webPath);
                session.setAttribute("loginUser", loginUser);
                session.setAttribute("loggedInUser", loginUser);
            }

            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=success");
        } catch (Exception e) {
            e.printStackTrace();
            savedFile.delete();
            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp?uploadResult=fail");
        }
    }
}
