package main.com.web.pay.model.dao;

import static main.com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import main.com.web.member.dao.MemberDao;
import main.com.web.member.dto.Member;
import main.com.web.pay.model.dto.Payment;
import main.com.web.reservationdetail.dto.ReservationDetail;
import main.com.web.room.dto.Room;

public class PaymentDao {

    private Properties sql = new Properties();

    {
        String path = MemberDao.class.getResource("/sql/payment_sql.properties").getPath();
        try (FileReader fr = new FileReader(path)) {
            sql.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int insertReservationInfo(Connection conn, Member m, Room r, String checkInDate, String checkOutDate, int roomPeopleNo, String roomRequest, String bedType) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("insertReservationInfo"));
            pstmt.setString(1, r.getRoomType());
            pstmt.setInt(2, r.getRoomPrice());
            pstmt.setString(3, m.getMemberName());
            pstmt.setString(4, m.getMemberPhone());
            pstmt.setDate(5, java.sql.Date.valueOf(checkInDate)); // checkInDate를 java.sql.Date로 변환
            pstmt.setDate(6, java.sql.Date.valueOf(checkOutDate)); // checkOutDate를 java.sql.Date로 변환
            pstmt.setString(7, m.getMemberId());
            pstmt.setInt(8, roomPeopleNo);
            pstmt.setString(9, r.getLocation());
            pstmt.setString(10, roomRequest);
            pstmt.setString(11, bedType);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    public String getLatestReserveNo(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String reserveNo = null;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("getLatestReserveNo"));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reserveNo = rs.getString("reserveno");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return reserveNo;
    }
    
	public int insertReservationDetail(Connection conn, String reserveNo, Room r, String roomRequest, String bedType, String car, int roomPeopleNo) {
		PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("insertReservationDetail"));
            pstmt.setString(1, reserveNo);
            pstmt.setInt(2, r.getRoomNo());
            pstmt.setString(3, roomRequest);
            pstmt.setString(4, car);
            pstmt.setString(5, bedType);
            pstmt.setInt(6, roomPeopleNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
	}

    public int savePaymentInfo(Connection conn, String impUid, String merchantUid, int payPrice, String paymentMethod, String status, String location, String reserveNo) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = conn.prepareStatement(sql.getProperty("savePaymentInfo"));
            pstmt.setString(1, impUid);
            pstmt.setString(2, merchantUid);
            pstmt.setInt(3, payPrice);
            pstmt.setString(4, paymentMethod);
//            pstmt.setString(5, status);
            pstmt.setString(5, location);
            pstmt.setString(6, reserveNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }
    public Payment selectPayment(Connection conn, String reserveNo) {
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	Payment p = null;
    	try {
    		pstmt = conn.prepareStatement(sql.getProperty("selectPayment"));
    		pstmt.setString(1, reserveNo);
    		rs = pstmt.executeQuery();
    		if(rs.next()) p = getPayment(rs);
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		close(rs);
    		close(pstmt);
    	}
    	
    	return p;
    }

	public ReservationDetail selectReserveDetail(Connection conn, String reserveNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationDetail r = null;
		try {
	 		pstmt = conn.prepareStatement(sql.getProperty("selectReservationDetail"));
    		pstmt.setString(1, reserveNo);
    		rs = pstmt.executeQuery();
    		if(rs.next()) r = getReservationDetail(rs);
		}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		close(rs);
    		close(pstmt);
    	}
		return r;
	}
    
    
	private ReservationDetail getReservationDetail(ResultSet rs) throws SQLException{
		return ReservationDetail.builder()
				.reserveNo(rs.getString("reserveno"))
				.roomNo(rs.getInt("roomno"))
				.request(rs.getString("request"))
				.car(rs.getString("car"))
				.bedType(rs.getString("bedtype"))
				.roomPeopleNo(rs.getInt("roompeopleno"))
				.build();
	}

	public Room selectRoom(Connection conn, int roomNo) {
		PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	Room r = null;
    	try {
    		pstmt = conn.prepareStatement(sql.getProperty("selectRoom"));
    		pstmt.setInt(1, roomNo);
    		rs = pstmt.executeQuery();
    		if(rs.next()) r = getRoom(rs);
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		close(rs);
    		close(pstmt);
    	}
		return r;
	}
    
	private Room getRoom(ResultSet rs) throws SQLException{
		return Room.builder()
				.roomNo(rs.getInt("roomno"))
				.roomPrice(rs.getInt("roomprice"))
				.roomAmenity(rs.getString("roomamenity"))
				.roomArea(rs.getFloat("roomarea"))
				.roomType(rs.getString("roomtype"))
				.roomInfo(rs.getString("roominfo"))
				.location(rs.getString("location"))
				.category(rs.getString("category"))
				.hotelService(rs.getString("hotelservice"))
				.roomUrl(rs.getString("roomurl"))
				.build();
	}
	
	private Payment getPayment(ResultSet rs) throws SQLException{
		return Payment.builder()
				.payNo(rs.getString("payno"))
				.payCoupon(rs.getInt("paycoupon"))
				.payMethod(rs.getString("paymethod"))
				.payPrice(rs.getInt("payprice"))
				.payDate(rs.getDate("paydate"))
				.payBank(rs.getString("paybank"))
				.reserveNo(rs.getString("reserveno"))
				.location(rs.getString("location"))
				.merchant_uid(rs.getString("merchant_uid"))
				.build();
	}

	public Member selectKakaoMember(Connection conn, String email) {
		PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	Member m = null;
    	try {
    		pstmt = conn.prepareStatement(sql.getProperty("selectKakaoMember"));
    		pstmt.setString(1, email);
    		rs = pstmt.executeQuery();
    		if(rs.next()) m = getMember(rs);
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		close(rs);
    		close(pstmt);
    	}
		return m;
	}

	private Member getMember(ResultSet rs) throws SQLException{
		return Member.builder()
				.memberCheckNo(rs.getString("membercheckno"))
				.memberEnrollDate(rs.getDate("memberenrolldate"))
				.memberId(rs.getString("memberid"))
				.memberName(rs.getString("membername"))
				.memberNo(rs.getInt("memberno"))
				.memberPhone(rs.getString("memberphone"))
				.memberPwd(rs.getString("memberpwd"))
				.build();
	}

	

}