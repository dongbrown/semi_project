package main.com.web.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	
	private int reviewNo;
	private String reviewContent;
	private int memberNo;
	private String category;
	//
	private int entityId; 

}
