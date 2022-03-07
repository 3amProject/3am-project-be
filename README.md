## 프로젝트 명

건강한 야식을 책임지는 새벽세시 프로젝트

---

## 제작기간

2021/12/27 - 2022/2/11

---

## 프로젝트 설명

새벽배송 서비스인 [새벽다섯시 서비스](https://www.5am.co.kr/shop/calendar_shop.php)를 클론코딩하여, 건강한 야식을 제공하는 ‘새벽 세시’ 웹 서비스를 구현하는 프로젝트입니다.

---

## 프로젝트 목표

- 프론트 - 백엔드 간 API 정의서를 작성해 효과적으로 소통하고 일정을 조율해나가는 훈련을 한다.
- 시큐리티 로그인을 통해, 모두에게 열린 페이지와 인증된 사용자만 접근할 수 있는 페이지를 구분한다.
- JWT를 활용한 authentication에 성공한다.
- Axios를 통해 프론트와 통신하여 적절한 response값을 전달하는 api를 설계 및 구현한다.
- 적절하지 않은 요청 시, Validation을 하여 필요한 예외처리나 ResponseDTO를 통해 FAIL 메시지를 프론트에 전달한다.
- 상품 데이터(Product)와 장바구니(Cart), 주문 정보(OrderDetail,Order)의 테이블을 적절하게 설계하여, 상품을 조회하고 장바구니에 담아 주문을 완료하는 로직을 구현한다.
- 회원가입 시 받아온 회원정보와 주문 History를 마이페이지에서 정상적으로 조회 요청하고 DB에서 조회해서 View에 전달한다.

---

## 개발환경

- Programming Language : Java, JavaScript(ES6)
- DBMS : MySQL
- Framework / Library : Spring Boot, MyBatis, React.js
- OS : Windows , MacOS
- Tools : STS4, VSCode, IntelliJ, Git / Github, Notion, Slack

---

## 참여인원

- 프론트엔드 - 김소형
- 백엔드 - 이동은, 전예지

---

## 주요 기능

- JWT / Spring Security 회원가입 / 로그인
- 회원정보 조회 / 수정
- 장바구니 담기 / 조회
- 장바구니에 담긴 상품 수량 추가 / 삭제
- 장바구니에 담긴 상품 주문 (주문정보 페이지 조회와 주문 시 주문정보 DB에 입력)
- 주문 history 조회

---

## 배포
```bash
# 퍼미션 설정
$ sudo chmod 755 ./mvnw

# 배포용 jar 생성
$  ./mvnw clean package

# 컨테이너 실행
$ make up
# 컨테이너 실행 (--force-recreate --build)
$ make upf
# 컨테이너 정지 & 삭제
$ make down

# 도커 시스템 정리
$ docker system prune
```

## front-end
http://localhost:8080
## back-end
http://localhost:8080/api
