package main.com.web.mypage.dao;

import static main.com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import main.com.web.enjoy.dto.Cafe;
import main.com.web.member.dao.MemberDao;
import main.com.web.qna.dto.Inquiry;
import main.com.web.reservation.dto.Reserve;
import main.com.web.review.dto.Review;

public class MyPageDao {
	private Properties sql = new Properties(); // properties 값을 가져온다
	{
		String path = MemberDao.class.getResource("/sql/mypage_sql.properties").getPath();
		try (FileReader fr = new FileReader(path)) { // 패스 경로를 찾아 파일을 받아온다
			sql.load(fr); // properteis에 load해준다.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ReservationInfo 삭제
	public int cancelReservationInfo(Connection conn, String reserveNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteReservation"));
			pstmt.setString(1, reserveNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	// 결제 삭제
	public int cancelPayment(Connection conn, String reserveNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deletePayment"));
			pstmt.setString(1, reserveNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int cancelReservationDetail(Connection conn, String reserveNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteReservationDetail"));
			pstmt.setString(1, reserveNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteInquiry(Connection conn, int inquiryNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteInquiry"));
			pstmt.setInt(1, inquiryNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateMember(Connection conn, String memberId, String newName, String newPassword, String newPhone) {
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateMember"));

			pstmt.setString(1, newName);
			pstmt.setString(2, newPassword);
			pstmt.setString(3, newPhone);
			pstmt.setString(4, memberId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Reserve> selectMyReservation(Connection conn, int cPage, int numPerPage, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reserve> reservations = new ArrayList<Reserve>();

		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMyReservation"));
			pstmt.setString(1, id);
//			pstmt.setInt(2, (cPage - 1) * numPerPage + 1);
//			pstmt.setInt(3, cPage * numPerPage);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reservations.add(MyPageDao.getReservation(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return reservations;
	}

	public List<Review> selectMyReview(SqlSession session, Map<String, Integer> page, int loginMemberNo) {
		RowBounds rb = new RowBounds((page.get("cPage") - 1) * page.get("numPerpage"), page.get("numPerpage"));
		return session.selectList("mypage.selectReviewAll", loginMemberNo, rb);
	}

	public List<Inquiry> selectMyInquries(Connection conn, int loginMemberNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Inquiry> inquiries = new ArrayList<Inquiry>();

		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMyInquiries"));
			pstmt.setInt(1, loginMemberNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				inquiries.add(MyPageDao.getInquiry(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return inquiries;
	}
	public List<Cafe> selectMyCafes(Connection conn, int loginMemberNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cafe> cafes = new ArrayList<Cafe>();
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMyCafes"));
			pstmt.setInt(1, loginMemberNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) cafes.add(MyPageDao.getCafe(rs));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return cafes;
	}

	private static Cafe getCafe(ResultSet rs) throws SQLException{
		return Cafe.builder()
				.cafeNo(rs.getInt("cafeno"))
				.cafeName(rs.getString("cafename"))
				.cafeAddress(rs.getString("cafeaddress"))
				.cafePhone(rs.getString("cafephone"))
				.cafeTime(rs.getString("cafetime"))
				.cafeImg(rs.getString("cafeimg"))
				.cafeLatLong(rs.getString("cafelatlong"))
				.location(rs.getString("location"))
				.category(rs.getString("category"))
				.cafeContent(rs.getString("cafecontent"))
				.build();
	}

	private static Inquiry getInquiry(ResultSet rs) throws SQLException {
		return Inquiry.builder().onToOneInquiryId(rs.getInt("inquiryno")).inquiryType(rs.getString("inquirytype"))
				.title(rs.getString("title")).content(rs.getString("content")).inquiryDate(rs.getDate("inquirydate"))
				.MemberNo(rs.getInt("memberno")).answer(rs.getString("answer")).build();
	}

	private static Review getReview(ResultSet rs) throws SQLException {
		return Review.builder().reviewNo(rs.getInt("reviewno")).reviewContent(rs.getString("reviewcontent"))
				.memberNo(rs.getInt("memberNo")).category("category").entityId(rs.getInt("entityid")).build();
	}

	private static Reserve getReservation(ResultSet rs) throws SQLException {
		return Reserve.builder().reserveNo(rs.getString("reserveno")).location(rs.getString("location"))
				.memberId(rs.getString("memberid")).memberName(rs.getString("membername"))
				.roomType(rs.getString("roomtype")).bedType(rs.getString("bedtype"))
				.checkInDate(rs.getDate("checkindate")).checkOutDate(rs.getDate("checkoutdate"))
				.memberPhone(rs.getString("memberphone")).payPrice(rs.getInt("payprice"))
				.roomPeopleNo(rs.getInt("roompeopleno")).memberAddress(rs.getString("memberaddress"))
				.reserveDate(rs.getDate("reservedate")).build();
	}

	public Inquiry selectInquiryByNo(Connection conn, int inquiryNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Inquiry i = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectInquiryByNo"));
			pstmt.setInt(1, inquiryNo);
			rs = pstmt.executeQuery();
			if (rs.next())
				i = getInquiry(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return i;

	}

	public List<Reserve> searchByReserveNo(Connection conn, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reserve> result = new ArrayList<Reserve>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("searchByReserveNo"));
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(MyPageDao.getReservation(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public List<Reserve> searchByLocation(Connection conn, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reserve> result = new ArrayList<Reserve>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("searchByLocation"));
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(MyPageDao.getReservation(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int selectReservationCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectReservationCount"));
			rs = pstmt.executeQuery();
			rs.next(); 
			result = rs.getInt(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int selectReviewCount(SqlSession session) {
		return session.selectOne("mypage.selectReviewCount");
	}



}