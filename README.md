## Semi-Project 
<img width="800" alt="image" src="https://github.com/dongbrown/semi_project/assets/167401654/6fb1a642-80a9-4141-a175-f322ac2cff25">


예약/결제 기능 및 관리자 페이지가 있는 호텔 웹사이트입니다.
<br>Servlet과 JSP를 활용하여 Native하게 구현한 모델입니다.

구현 URL : [METHOD Hotel](http://14.36.141.71:10079/GDJ79_main_semi/)
<br>
<br>
<br>
<br>
## 📅 개발기간
2023.05.14 ~ 2023.05.29
<br>
<br>

## 👨‍👨‍👧‍👧 팀원
팀장|팀원|팀원|팀원|팀원
| :---: |:---:| :---: | :---: | :---: |
|강요한|김동훈|임성욱|유선정|김해진
| 기능 구현, 화면 구현 | 기능 구현, 화면 구현, DB 설계 및 관리 | 기능 구현, 화면 구현 | 기능 구현, 화면 구현 | 기능 구현, 화면 구현 |


<br>
<br>
[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=dongbrown)](https://github.com/anuraghazra/github-readme-stats)
[![Anurag's GitHub stats](https://github-readme-stats.vercel.app/api?username=dongbrown)](https://github.com/anuraghazra/github-readme-stats)
<br>

## ⚙️ 기술 스택

|  카테고리  |                                                                                             스택                                                                                             |
|:----------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 통합개발환경 | <img src="https://img.shields.io/badge/Eclipse IDE-181717?style=flat&logo=Eclipse IDE" />                                                                                                   |
| 사용한 언어 | <img src="https://img.shields.io/badge/Java-darkblue?style=flat&logo=Java" /> <img src="https://img.shields.io/badge/Javascript-4B4B77?style=flat&logo=Javascript" /> <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery" /> |
| 프론트엔드 | <img src="https://img.shields.io/badge/HTML 5-302683?style=flat&logo=HTML5" /> <img src="https://img.shields.io/badge/CSS 3-1572B6?style=flat&logo=CSS3" />                                                                        |
| 형상관리도구 | <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white" />                                                                                             |
| 데이터베이스 | <img src="https://img.shields.io/badge/OracleDB-F80000?style=flat&logo=Oracle&logoColor=white" />                                                                                           |
| 협업 도구   | <img src="https://img.shields.io/badge/Discord-5865F2?style=flat&logo=discord&logoColor=white" /> <img src="https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=Figma&logoColor=white" /> <img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white" /> |
| API        | <img src="https://img.shields.io/badge/Kakao-FFCD00?style=flat&logo=Kakao&logoColor=white" /> <img src="https://img.shields.io/badge/Portone-darkblue?style=flat" />
| WAS        | <img src="https://img.shields.io/badge/Tomcat 9.0-F8DC75?style=flat&logo=Apache Tomcat&logoColor=black" />                                                                                    |


## 📷 담당한 서비스 화면
### 결제  <br>
(1) 결제 페이지 <br>
![image](https://github.com/dongbrown/semi_project/assets/167401654/e9d37faa-b401-4d4f-82c5-c000bff04977) <br>
- 사용자가 예약페이지에서 선택 및 입력한 예약 정보들을 결제페이지에 출력하여 결제 전 예약정보 확인 <br><br>

(2) 카카오페이 결제화면 <br>
<img width="600" alt="image" src="https://github.com/dongbrown/semi_project/assets/167401654/c4bfe7ce-3e69-406b-8377-7912aea9efc1"> <br>
- QR결제 및 카카오톡을 이용한 결제 기능 <br>
- 결제 성공 시, AJAX를 이용해 서버에 결제 정보를 저장 <br><br>

<img width="600" alt="image" src="https://github.com/dongbrown/semi_project/assets/167401654/44afed05-dee9-48ac-b07e-18ab9266698b"> <br>
- 기간별/결제 상태별 결제 내역 리스트 확인 및 결제 전액•부분 취소 기능 <br><br>

### 마이페이지 <br>
(1) 예약/결제 내역 확인 및 취소 <br> 
<img width="600" alt="image" src="https://github.com/dongbrown/semi_project/assets/167401654/8006fa2b-d084-467e-9a9b-f8dfa15851c5"> <br>

(2) 내가 작성한 리뷰 / 문의 내역 / 개인 정보 수정 <br>
<img width="600" alt="image" src="https://github.com/dongbrown/semi_project/assets/167401654/1fe7f210-0e16-44d5-bdac-3e6b1fb9ff92"> <br>
- 내가 작성한 리뷰
    - 객실 / 식당 / 카페 / 관광지 카테고리별로 내가 작성한 리뷰 확인 기능 (페이징 처리)
- 문의 내역
    - 내가 작성한 문의 내역 리스트 및 제목 클릭 시 문의사항 및 답변 확인 기능
    - 답변 여부에 따른 답변준비중 / 답변완료 분기 처리
- 개인정보 수정
    - Primary Key인 아이디를 제외한 개인 정보 수정 기능
    - 비밀번호 / 비밀번호 확인 일치한지 keyup 이벤트로 유효성 검사 <br><br>
      

## 🔨 업데이트
1. 결제 위•변조 검증
   - 결제 요청은 클라이언트 환경에서 이루어지기 때문에 별도의 검증을 하지 않으면 클라이언트가 스크립트를 조작해 금액을 위 변조하여 결제를 요청할 수 있습니다. 따라서 결제하고자 하는 상품의 금액과 실제로 결제된 금액을 서버에서 검증하는 로직을 추가중입니다.
2. 예약 / 결제 취소 내역 및 쿠폰
   - 예약 테이블과 결제 테이블에 ‘status’라는 컬럼을 추가하여 ‘예약완료/예약취소’ 및 ‘결제완료’/’환불’ 상태를 추가하여 DB에 저장해서 취소내역을 확인하는 기능을 추가중입니다.
3. MyBatis 적용
   - properties로 작성한 SQL문을 MyBatis를 적용하여 mapper.xml로 업데이트중입니다.

