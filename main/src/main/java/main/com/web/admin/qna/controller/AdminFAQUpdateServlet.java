package main.com.web.admin.qna.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.com.web.admin.qna.service.AdminFAQService;
import main.com.web.qna.dto.FAQ;

@WebServlet("/admin/updateFAQ")
public class AdminFAQUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int faqAllNo = Integer.parseInt(request.getParameter("faqAllNo"));
        String faqCategory = request.getParameter("faqCategory");
        String faqTitle = request.getParameter("faqTitle");
        String faqContent = request.getParameter("faqContent");
        String location = request.getParameter("location");

        FAQ faq = new FAQ(faqAllNo, faqCategory, faqTitle, faqContent, new java.sql.Date(System.currentTimeMillis()), location);

        AdminFAQService service = new AdminFAQService();
        int result = service.updateFAQ(faq);
        if (result > 0) {
            response.sendRedirect(request.getContextPath() + "/admin/FAQList");
        } else {
            request.setAttribute("errorMessage", "FAQ 수정 실패");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}