<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="main.com.web.member.dto.*" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<%
    Member m1 = (Member)session.getAttribute("member");
    /* int totalPrice = (int)request.getAttribute("totalPrice"); */
%>

<!-- jQuery -->
<script type="text/javascript"
    src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- iamport.payment.js -->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

<link rel="stylesheet" href="../css/pay.css">

<section>

<div class="container">
    <h1>예약/결제하기</h1>
		<form action="#" method="POST">
			<div style="overflow: hidden;">
				<div style="float: left; width: 30%;">
					<label for="hotel">호텔 이름</label> <input type="text" id="hotel"
						name="hotel" readonly value="Method Hotel Jeju"> <label
						for="checkin">체크인</label> <input type="text" id="checkin"
						name="checkin" readonly value="2024/06/02"> <label
						for="checkout">체크아웃</label> <input type="text" id="checkout"
						name="checkout" readonly value="2024/06/03"> <label
						for="room">객실 타입</label> <input type="text" id="room" name="room"
						readonly value="스탠다드"> <label for="amount">결제 금액</label> <input
						type="text" id="amount" name="amount" readonly value="100,000원">
				</div>
				<div style="float: right; width: 70%;">
					<div class="agreement-container">
						<p>개인정보 수집 동의서</p>
						<p>Method Hotel은 귀하의 소중한 개인정보를 수집, 활용하고자 [개인정보보호법]에 따라 본인의 동의를
							얻고 있습니다.</p>
						<p>이에 아래의 내용과 같이 귀하의 개인정보를 수집, 활용하는데 동의하여 주실 것을 요청드립니다.</p>
						<p>- 수집항목: 이름, 이메일, 주소, 전화번호</p>
						<p>- 수집목적: 서비스 제공, 고객 문의 응대</p>
						<p>- 보유기간: 서비스 이용 종료 후 1년까지</p>
						<p>- 처리방침: 귀하는 개인정보에 대한 권리를 갖고 있으며, 제공한 개인정보의 열람, 정정, 삭제, 처리정지
							요청이 가능합니다.</p>
						<p>- 동의철회: 귀하는 언제든지 동의를 철회할 수 있으며, 동의를 철회하는 경우 제공한 개인정보는
							파기됩니다.</p>
						<p>- 동의거부: 귀하는 개인정보 수집에 대한 동의를 거부할 수 있으나, 이 경우 서비스 이용이 제한될 수
							있습니다.</p>
						<p>동의하지 않을 경우 해당 서비스를 이용하실 수 없습니다.</p>
					</div>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>
					<br>

					<div class="checkbox-container">
						<label for="agree"> <input type="checkbox" id="agree"
							name="agree"> 개인정보 수집에 동의합니다.
						</label>
					</div>
				</div>
			</div>
			<img src="<%=request.getContextPath()%>/images/kakaoPay.png"
				alt="Pay Button" id="kakaoPayImg" width="150" height="100">

		</form>
	</div>
<script>
	$(document)
			.ready(
					function() {
						IMP.init('imp66468378');

						// 이미지를 클릭할 때 실행되는 함수
						$('#kakaoPayImg').click(function(event) {
							event.preventDefault(); // 기본 클릭 동작 방지
							if ($('#agree').prop('checked')) { // 체크박스가 체크되어 있는지 확인
								requestPay();
							} else {
								alert('개인정보 수집에 동의해야 결제가 가능합니다.');
							}
						});

						function requestPay() {
							IMP
									.request_pay(
											{
												pg : 'kakaopay',
												pay_method : 'card',
												merchant_uid : 'p_'
														+ new Date().getTime(), // 결제번호
												name : '주문명: 결제테스트',
												amount : '1000',
												buyer_email : '
<%=m1.getMemberId()%>',
            buyer_name: '<%=m1.getMemberName()%>',
            buyer_tel: '<%=m1.getMemberPhone()%>'
        }, function(rsp) {
            if (rsp.success) {
                console.log(rsp);
                alert('결제가 완료되었습니다.');

                // 서버에 결제 정보 전달
                $.ajax({
                    url: '<%=request.getContextPath()%>/pay/savepayment',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        imp_uid: rsp.imp_uid, // 아임포트 결제 고유 ID
                        merchant_uid: rsp.merchant_uid, // 상점에서 생성한 주문번호
                        payPrice: 1000, //나중에 받아오기
                        paymentMethod: 'kakaopay',
                        status: 'paid',
                        location: '서울',
                        reserveNo: 'S2024129'
                    }),
                    success: function(response) {
                        // response.redirect를 사용하여 페이지 이동
                        window.location.href = '<%=request.getContextPath()%>/pay/paycompletePage?reserveNo=S2024129';
                    },
                    error: function() {
                        alert('결제 정보 저장에 실패하였습니다.');
                    }
                });
            } else {
                alert('결제를 취소하였습니다.');
            }
        });
    }
});
</script>

</section>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>
