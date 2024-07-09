package main.com.web.pay.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.com.web.member.dto.Kakao;
import main.com.web.member.dto.Member;
import main.com.web.pay.model.service.PaymentService;
import main.com.web.room.dto.Room;

@WebServlet("/pay/paymentPage")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private PaymentService paymentService = new PaymentService();

    public PaymentController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = getEmailFromSession(session);
        
        if (email == null || email.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/member/loginPage");
            return;
        }

        Member member = paymentService.selectKakaoMember(email);        
        if (member != null) {
            request.setAttribute("member1", member);
            
            try {
                String checkInDate = request.getParameter("checkindate");
                String checkOutDate = request.getParameter("checkoutdate");
                int roomNo = Integer.parseInt(request.getParameter("roomNo"));
                int roomPeopleNo = Integer.parseInt(request.getParameter("peopelNo"));
                String car = request.getParameter("car");
                String bedType = request.getParameter("bedType");
                String roomRequest = request.getParameter("roomRequest");
                String totalPrice = request.getParameter("price");

                Room room = paymentService.selectRoom(roomNo);
                request.setAttribute("room", room);

                request.getRequestDispatcher("/WEB-INF/views/pay/payment.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/member/loginPage");
        }
    }

    private String getEmailFromSession(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            return member.getMemberId();
        }
        Kakao kakaoMember = (Kakao) session.getAttribute("kakaoMember");
        if (kakaoMember != null) {
            return kakaoMember.getAccount_email();
        }
        return null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}