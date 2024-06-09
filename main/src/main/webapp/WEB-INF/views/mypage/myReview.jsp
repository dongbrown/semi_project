<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="main.com.web.review.dto.*, main.com.web.enjoy.dto.Cafe, java.util.List"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/css/mypage.css">
<%
	List<Review> reviews = (List<Review>)request.getAttribute("reviews");
	List<Cafe> cafes = (List<Cafe>)request.getAttribute("cafes");
	int no = 1;
%>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">


<section class="sectionflex">
	<aside class="aside">
		<nav>
			<ul>
				<li>
					<h3>MyPage</h3>
					<hr>
				</li>
				<li><a
					href="<%=request.getContextPath()%>/mypage/myReservationPage">예약/결제
						내역</a></li>
				<li><a href="<%=request.getContextPath()%>/mypage/myReviewPage">내가 작성한 리뷰</a></li>
				<li><a
					href="<%=request.getContextPath()%>/mypage/myInquiryListPage">문의
						내역</a></li>
				<li><a href="<%=request.getContextPath()%>/mypage/myInfoPage">개인정보
						수정</a></li>
			</ul>
		</nav>
	</aside>
	<main class="main">
		<span id="title">내가 작성한 리뷰</span>
		<div class="separator"></div>
		<div class="container1"></div>
		<div class="container2">
			<table class="reservationInfo">
				<thead>
					<tr>
						<th>No</th>
						<th>카테고리</th>
						<th>사진</th>
						<th>리뷰내용</th>
					</tr>
				</thead>
				<tbody>
					<% if(reviews == null || reviews.isEmpty()) { %>
					<tr>
						<td colspan="5">조회된 리뷰가 없습니다.</td>
					</tr>
					<% } else {
						for(Review r : reviews) { %>
					<tr>
						<td><%=no %></td>
						<td>
							<%
								boolean cafeFound = false;
								String category = "카테고리 없음"; // 기본값 설정
								String cafeImgHtml = "이미지 없음"; // 기본값 설정
								if(cafes != null) {
									for(Cafe c : cafes) {
										if(c.getCafeNo() == r.getEntityId()) {
											cafeFound = true;
											category = c.getCategory(); // 카테고리 설정
											cafeImgHtml = "<img src=\"" + c.getCafeImg() + "\" alt=\"카페 이미지\" style=\"width:300px; height:200px;\">";
											break;
										}
									}
								}
							%>
							<%=category %>
						</td>
						<td><%= cafeImgHtml %></td>
						<td><%=r.getReviewContent() %></td>
					</tr>
					<%	no++;
						}
					} %>
				</tbody>
			</table>
		</div>
		${pageBar }
	</main>
</section>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
