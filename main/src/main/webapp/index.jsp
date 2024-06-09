<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/views/common/header.jsp" %>
  <link href="https://fonts.googleapis.com/css2?family=Libre+Baskerville:ital,wght@0,400;0,700;1,400&display=swap" rel="stylesheet">
 
 	<style>
    .background{
     background-image:url('./imges/2.jpg');
     height: 1200px;
     }

     .texth2{
   	  margin-top : 300px;
      text-align: center; 
      color: white;
	  font-family: Libre Baskerville;
	  font-size: 20px;
   	  letter-spacing: 3px;
     }
     .texth1{
     /* 	font-family: "Libre Baskerville", serif;
    	font-size: 80px; 
    	font-weight: bold; 
    	color: white; 
    	letter-spacing: 5px; 
    	margin: 0;  */
     }
    </style>
 
 
     <div id="mainText">
                  
                </div>`
    <section class="main-section">
                
        <div class="main-content ">
            <!-- 여기에 메인 컨텐츠 추가해주세용 -->
          <a href="<%=request.getContextPath()%>/room/standardroom.do?room=Standard&location=제주">Standard</a>
          <a href="<%=request.getContextPath()%>/room/standardroom.do?room=Deluxe&location=제주">Deluxe</a>
          <a href="<%=request.getContextPath()%>/room/standardroom.do?room=Suite&location=제주">Suite</a>
        </div>
        
    </section>
    <script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
    <script>
   	 $(".main-content").hide(); 
    </script>
    <script src = "<%=request.getContextPath()%>/js/extendIndex.js"></script>
    
<%@ include file="/WEB-INF/views/common/footer.jsp"%>