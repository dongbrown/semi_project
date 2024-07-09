package main.com.web.pay.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {
	private String payNo;
	private int payCoupon;
	private String payMethod;
	private int payPrice;
	private Date payDate;
	private String payBank;
	private String reserveNo;
	private String location;
	private String merchant_uid;
	
	
}
