/**
 * 
 */

const date = new Date(); //현재 날짜
console.log(date); // 
date.toISOString().substring(0,10);
console.log(date.toISOString().substring(0,10));
console.log(typeof date.toISOString().substring(0,10));
const a = new Array();
const $showRoom = document.getElementById("showRoom");
const checkindate1 = document.getElementById("ck1");
const checkindate2 = document.getElementById("ck2");
btn.addEventListener("click",e=>{
    a.pop();
    a.pop();
    checkindate1.innerText="체크인 날짜: ";
    checkindate2.innerText="체크아웃 날짜: ";
  })
  document.addEventListener('DOMContentLoaded', function() {
  
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth',
    selectable: true,
    dateClick: function(info) { //클릭이벤트 
      // console.log(checkindate.innerHTML+=info.dateStr);
    if(info.dateStr<date.toISOString().substring(0,10)){
        alert(`${date.toISOString().substring(0,10)}이후 날짜를 선택해주세요`);}
    else{
      if(a.length<2){
      a.push(info.dateStr);
      console.log(typeof info.dateStr)
      a.sort();
      if(a[0]==undefined){
      }else{
        checkindate1.innerText="체크인 날짜: "+a[0];
      }
      if(a[1]==undefined){ 
      }else{
        checkindate2.innerText="체크아웃 날짜: "+a[1];
      }
    }else{

      alert("초기화 버튼을 눌러주세요");
      
    }
} 
    console.log(info.dateStr); // 시작날짜 
    },
      googleCalendarApiKey: 'AIzaSyCQuuUbjwcSDs4B2RUHNaSZF5zxqE8HucA',
        events: {
        googleCalendarId: 'b9ba28ab7d64f0f31ff2fb95137e51d6ef100eca797093fd5c5d6e36430f4c7e@group.calendar.google.com'
        }
    });
    calendar.render();
  });
btn1.addEventListener("click", e => {
    fetchRooms("Standard", 1);
});

StandardSelect.addEventListener("click", e => {
    fetchRooms("Standard", 1);
});

DeluxeSelect.addEventListener("click", e => {
    fetchRooms("Deluxe", 1);
});

SuiteSelect.addEventListener("click", e => {
    fetchRooms("Suite", 1);
});

function fetchRooms(roomType, page) {
    $.ajax({
        type: "get",
        async: true,
        url: '/method_hotel/reservation/date',
        dataType: "json",
        data: {
            checkindate: a[0],
            checkoutdate: a[1],
            roomType: roomType,
            page: page,
            itemsPerPage: 3 // 페이지 당 아이템 수 설정
        },
        success: function (response) {
            const roomList = response.roomList; // 배열
            const totalData = response.totalData; // 페이지바
            while ($showRoom.firstChild) {
                $showRoom.firstChild.remove();
            }
            roomList.forEach((room, i) => {
           		const roomT = room.roomPrice;
           		let getroomImg = "";
           		if(roomT ==100000){
					getroomImg = "-ST.jpg";
				}else if(roomT==200000){
					getroomImg = "-DL.jpg";
				}else{
					getroomImg = "-SW.jpg";
				}
				console.log(room.roomPrice)
				console.log(i);
           		
                const $detail = document.createElement('div');
                $detail.className = "detail";
   
                const $roomCard = document.createElement('div');
                $roomCard.className = "room-card";

                const $roomImg = document.createElement('img');
                $roomImg.className = "room-img";
                $roomImg.src = `./images/rooms/${(i+1)%5+getroomImg}`;

                const $roomInfo = document.createElement('div');
                $roomInfo.className = "room-info";

                const $roomTitle = document.createElement('h3');
                $roomTitle.className = "room-title";
                $roomTitle.textContent = `Room No: ${room.roomNo}`;

                const $roomPrice = document.createElement('p');
                $roomPrice.className = "room-price";
                $roomPrice.textContent = `가격: ${room.roomPrice}`;

                const $roomAmenity = document.createElement('p');
                $roomAmenity.className = "room-amenity";
                $roomAmenity.textContent = `부대시설: ${room.roomAmenity}`;

                const $roomArea = document.createElement('p');
                $roomArea.className = "room-area";
                $roomArea.textContent = `면적: ${room.roomArea}`;

                const $roomInfoText = document.createElement('div');
                $roomInfoText.className = "room-info-text";
                $roomInfoText.textContent = `${room.roomInfo}`;

                const $roomCategory = document.createElement('p');
                $roomCategory.className = "room-category";
                $roomCategory.textContent = `Category: ${room.category}`;

                const $roomService = document.createElement('p');
                $roomService.className = "room-service";
                $roomService.textContent = `서비스: ${room.hotelService}`;
                //$roomInfo.appendChild($roomTitle);
                $roomInfo.appendChild($roomPrice);
                $roomInfo.appendChild($roomAmenity);
                $roomInfo.appendChild($roomArea);
                $roomInfo.appendChild($roomInfoText);
                //$roomInfo.appendChild($roomCategory);
                $roomInfo.appendChild($roomService);
                $roomCard.appendChild($roomImg);
                $roomCard.appendChild($roomInfo);
                // 자차 유무 선택
                const $carOption = document.createElement('div');
                $carOption.className = "car-option";
                $carOption.innerHTML = `
                    <label for="car-${i}">자차 유무:</label>
                    <select id="car-${i}" name="car">
                        <option value="yes">있음</option>
                        <option value="no">없음</option>
                    </select>
                `;
                // 침대 타입 선택
                const $bedType = document.createElement('div');
                $bedType.className = "bed-type";
                $bedType.innerHTML = `
                    <label for="bed-${i}">침대 타입:</label>
                    <select id="bed-${i}" name="bedType">
                    <option value="twin">Twin</option>
                    <option value="double">Double</option>
                    </select>
                `;
                // 인원 선택
                const $peopleCount = document.createElement('div');
                $peopleCount.className = "people-count";
                $peopleCount.innerHTML = `
                    <label for="people-${i}">인 원:</label>
                    <select id="people-${i}" name="roomPeopleNo">
                    <option value="1인">1인</option>
                    <option value="2인">2인</option>
                    <option value="3인">3인</option>
                    <option value="4인">4인</option>
                    </select>
                `;
                // 요청사항 입력
                const $request = document.createElement('div');
                $request.className = "request";
                $request.innerHTML = `
                    <label for="request-${i}">요청사항</label>
                    <input type="text" id="request-${i}" name="request" placeholder="요청사항을 입력하세요">
                `;
                // 예약 버튼 추가
                const $reserveButton = document.createElement('button');
                $reserveButton.className = "reserve-button";
                $reserveButton.textContent = "예약하기";
                $reserveButton.addEventListener('click', (e) => {
            const target = e.target;
            //console.dir(target);
            //console.dir(e.target.previousElementSibling.lastElementChild);
                //console.log(e.target.previousElementSibling.lastElementChild.value); //요청사항 받아옴
                const request = e.target.previousElementSibling.lastElementChild.value;
                //console.log("인원수");
                //console.log(e.target.previousElementSibling.previousElementSibling.lastElementChild.value);//인원수 받아옴
                const roomPeopleNo = e.target.previousElementSibling.previousElementSibling.lastElementChild.value;
                 // 파싱된값 
                //console.log("인원수"+(roomPeopleNo.substring(0,roomPeopleNo.length-1)));
                //참대 타입 
                console.log(e.target.previousElementSibling.previousElementSibling.previousElementSibling.lastElementChild.value);//침대 받아옴
                 // 자차 유무
                const bedType = e.target.previousElementSibling.previousElementSibling.previousElementSibling.lastElementChild.value; // 침대 타입
            //console.log(e.target.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.lastElementChild.value); //자차부분                 
                  const car = e.target.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.lastElementChild.value; //자차 값
                  // 예약 버튼 클릭 이벤트 처리
                //console.dir(e.target);
             //console.log(roomList[i]); // 외부 범위의 roomList에 접근
               const date1 = new Date(a[1]);
               const date0 = new Date(a[0]);
               console.log(date0);
               console.log(date1);
               let diff = Math.abs(date1.getTime() - date0.getTime());
               diff = Math.ceil(diff / (1000 * 60 * 60 * 24));
               console.log("날짜"+diff);
               console.log("room가격"+room.roomPrice);
               console.log("요청사항" + request);
             location.assign(`/method_hotel/pay/paymentPage?roomNo=${roomList[i].roomNo}&checkindate=${a[0]}&checkoutdate=${a[1]}&roomRequest=${request}&bedType=${bedType}&car=${car}&peopelNo=${roomPeopleNo.substring(0,roomPeopleNo.length-1)}&price=${diff*Number(room.roomPrice)}`);
                });
                // detail div에 요소 추가
                $detail.appendChild($carOption);
                $detail.appendChild($bedType);
                $detail.appendChild($peopleCount);
                $detail.appendChild($request);
                $detail.appendChild($reserveButton);
                // roomCard에 detail 추가
                $roomCard.appendChild($detail);
                $showRoom.appendChild($roomCard);
            });
            updatePageBar(page, totalData, roomType);
            console.log(`${a[0]}, ${a[1]} 값 전송 성공`);
        }});
   }
   function updatePageBar(curPage, totalData, roomType) {
    const itemsPerPage = 3;
    const totalPages = Math.ceil(totalData / itemsPerPage);
    const pageLimit = 5;
    let startPage = parseInt((curPage - 1) / pageLimit) * pageLimit + 1;
    let endPage = startPage + pageLimit - 1;
    endPage = totalPages < endPage ? totalPages : endPage;
    let pageUrl = "";
    if (curPage > 1) {
        pageUrl += `<a href='javascript:fetchRooms("${roomType}", ${curPage - 1})'>이전</a>&nbsp;&nbsp;`;
    }
    for (let i = startPage; i <= endPage; i++) {
        if (i === curPage) {
            pageUrl += `<strong>${i}</strong>&nbsp;&nbsp;`;
        } else {
            pageUrl += `<a href='javascript:fetchRooms("${roomType}", ${i})'>${i}</a>&nbsp;&nbsp;`;
        }
    }
    if (endPage < totalPages) {
        pageUrl += `<a href='javascript:fetchRooms("${roomType}", ${endPage + 1})'>다음</a>&nbsp;&nbsp;`;
    }

    document.getElementById("pageBar").innerHTML = pageUrl;
}