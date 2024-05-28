package main.com.web.admin.sales.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import main.com.web.admin.reserve.dto.Sales;
import main.com.web.admin.sales.service.AdminSalesService;

/**
 * Servlet implementation class SalesByMonth
 */
@WebServlet("/sales/salesupdate.do")
public class SalesByMonth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesByMonth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        List<Sales> sales = new AdminSalesService().salesByMonth();

        // 조회된 데이터를 request에 설정
        request.setAttribute("sales", sales);

        // JSP 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/sales/sales.jsp").forward(request, response);
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}