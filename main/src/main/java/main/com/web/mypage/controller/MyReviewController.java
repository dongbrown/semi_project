package main.com.web.mypage.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.com.web.enjoy.dto.Cafe;
import main.com.web.member.dto.Member;
import main.com.web.mypage.service.MyPageService;
import main.com.web.review.dto.Review;

/**
 * Servlet implementation class MyReviewController
 */
@WebServlet("/mypage/myReviewPage")
public class MyReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MyPageService service = new MyPageService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("마이페이지(별점/리뷰 관리) 이동");
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		
		if(loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/member/loginPage"); // 로그인 페이지로 리디렉션
	        return;
		}
		
		int loginMemberNo = loginMember.getMemberNo();
		
		int cPage = 1, numPerpage = 5;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {}
		try {
			numPerpage = Integer.parseInt(request.getParameter("numPerpage"));
		}catch (NumberFormatException e) {}
		
		Map<String, Integer> page = Map.of("cPage", cPage, "numPerpage", numPerpage);
		
		//내가 작성한 리뷰 리스트
		List<Review> reviews = service.selectMyReview(loginMemberNo, page);
		request.setAttribute("reviews", reviews);	
		
		//내가 작성한 리뷰 수
		int totalData = service.selectReviewCount();
		
		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		String pageBar="<ul class='pagination justify-content pagination-sm'>";
		if(pageNo==1) {

			pageBar+="""
					<li class='page-item disabled'>
					<a class='page-link' href='#'>이전</a>
					</li>
					""";
		}else {
			pageBar+="<li class='page-item'>";
			pageBar+="<a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>이전</a></li>";
		}
		
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(cPage==pageNo) {
				pageBar+="<li class='page-item disabled'>";
				pageBar+="<a class='page-link' href='#'>"+pageNo+"</a></li>";
			}else {
				pageBar+="<li class='page-item'>";
				pageBar+="<a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo)+"'>"+pageNo+"</a></li>";
			}
			pageNo++;
		}
		
		if(pageNo>totalPage) {
			pageBar+="""
					<li class='page-item disabled'>
					<a class='page-link' href='#'>다음</a>
					</li>
					""";
		}else {
			pageBar+="<li class='page-item'>";
			pageBar+="<a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo+1)+"'>다음</a></li>";
		}
		pageBar+="</ul>";
		request.setAttribute("pageBar", pageBar);
		
		
		
		
		List<Cafe> cafes = new MyPageService().selectMyCafes(loginMemberNo);
		request.setAttribute("cafes", cafes);
		
		
		request.getRequestDispatcher("/WEB-INF/views/mypage/myReview.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
