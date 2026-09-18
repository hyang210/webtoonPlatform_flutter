package webtoon.controller;

import webtoon.dao.WebtoonDAO;
import webtoon.vo.WebtoonVO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/WebtoonListController")
public class WebtoonListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. DAO 객체를 생성하여 DB에서 웹툰 목록을 가져옵니다.
        WebtoonDAO dao = new WebtoonDAO();
        List<WebtoonVO> webtoonList = dao.getAllWebtoons();
        
        // 2. 검색 결과 (webtoonList)를 request 객체에 저장하여 View (JSP)로 전달 준비
        request.setAttribute("webtoonList", webtoonList);
        
        // 3. 데이터를 /webtoon/webtoonList.jsp로 포워딩 (화면 출력 위임)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/webtoon/webtoonList.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}