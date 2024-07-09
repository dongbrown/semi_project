package main.com.web.pay.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import main.com.web.member.dto.Member;
import main.com.web.pay.model.service.PaymentService;
import main.com.web.room.dto.Room;

@WebServlet("/pay/savepayment")
public class SavePaymentInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            JSONObject json = readJsonFromRequest(request);
            Member member = getMemberFromSession(request.getSession());

            if (member == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
                return;
            }

            String reserveNo = processPayment(json, member);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("reserveNo", reserveNo);
            response.getWriter().write(jsonResponse.toString());

        } catch (JSONException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the payment");
        }
    }

    private JSONObject readJsonFromRequest(HttpServletRequest request) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    private Member getMemberFromSession(HttpSession session) {
        return (Member) session.getAttribute("reserveMember");
    }

    private String processPayment(JSONObject json, Member member) throws Exception {
        String impUid = json.getString("imp_uid");
        String merchantUid = json.getString("merchant_uid");
        int payPrice = json.getInt("payPrice");
        String paymentMethod = json.getString("paymentMethod");
        String status = json.getString("status");
        String location = json.getString("location");
        String bedType = json.getString("bedType");
        int roomPeopleNo = json.getInt("roomPeopleNo");
        int roomNo = json.getInt("roomNo");
        String roomRequest = json.optString("roomRequest", "");
        String car = json.optString("car", "");
        String checkInDate = json.getString("checkInDate");
        String checkOutDate = json.getString("checkOutDate");

        Room room = paymentService.selectRoom(roomNo);

        String reserveNo = paymentService.insertReservationInfo(member, room, checkInDate, checkOutDate, roomPeopleNo, roomRequest, bedType);

        boolean paymentResult = paymentService.savePaymentInfo(impUid, merchantUid, payPrice, paymentMethod, status, location, reserveNo);
        boolean reserveDetailResult = paymentService.insertReservationDetail(reserveNo, room, roomRequest, bedType, car, roomPeopleNo);

        if (!paymentResult || !reserveDetailResult) {
            throw new Exception("Failed to save payment or reservation details");
        }

        return reserveNo;
    }
}