package main.com.web.reservation.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Reserve {
	private String reserveNo;
	private String roomType; //타입 지정
	private int payPrice;
	private String memberName;
	private String memberPhone;
	private String memberAddress;
	private Date checkInDate; //check인 날짜
	private Date checkOutDate; // check아웃 날짜 지정
	private String memberId;
	private int roomPeopleNo;
	private String location;
	private String bedType;
	private Date reserveDate;
	private String requestMemo;
	private Date updateReserveDate;
	private String status = "";
		
}
