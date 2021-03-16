# 꼬리별 [KKo-Ri-Byeol]
## 백엔드 (Clematis)
![Clematis](https://user-images.githubusercontent.com/48639421/111264452-e1ed1b00-866a-11eb-98a1-9fc39db9aaee.png)  

푸시 서비스가 필요해서 사용해본 NHN Cloud의 TOAST 푸시 서비스,  
TOAST 푸시 서비스를 사용하면서 느꼈던 불편한 점을 개선하여  
모두가 사용할 수 있는 푸시 서비스를 만드는 것이 목표인 프로젝트!  

## Function
- 푸시
  - 단일 / 대량 푸시 발송
  - 템플릿 푸시 발송
  - 그룹 푸시 발송
  - 예약 발송
- 사용자
  - 사용자 관리
  - 사용자별 프로젝트 관리
- 푸시 대상자
  - 푸시 대상자 관리
  - 푸시 대상자 그룹화

## Coding Convention
#### Common
- 최대한 Kotlin Convention에 의거하여 코드를 작성한다.

#### Layer
- request/response class는 Controller Layer에서만 사용하며, Service Layer에 넘겨주지 않는다.
- Service Layer는 SRP 원칙을 엄격하게 취급하여 CRUD, Retrofit을 통한 요청 등 하나의 일을 하도록 한다.  
(따라서 TemplateService와 같은 Service 객체를 만들지 않는다.)
- 모든 Request는 validation을 적용하며 Kotlin 자체 에러를 피하기 위해 null check도 한다.

#### Class
- request/response class는 data class로 작성하고 하나의 JSON을 표현할 때는 하나의 class만 사용하도록 static inner class를 사용한다.
- 생성자는 매개변수마다 행을 나누고 트레일링 콤마를 사용한다.  
(단, 매개변수가 하나이고 어노테이션이 없는 경우에는 행을 나누지 않고 트레일링 콤마(trailing comma)도 사용하지 않는다.)
- 확장이 용이한 경우에는 인터페이스를 사용하고, 외부(External) API를 사용하는 경우에는 **반드시** 인터페이스를 사용한다.
- class의 본문이 시작하는 중괄호와 첫 함수 사이는 한 칸 띄운다.  
(단, 함수가 하나일 경우에는 하지 않고 인터페이스는 함수끼리 모두 붙인다.)

#### Entity
- entity class에 절대 data class를 사용하지 않는다.
- 생성자에 있는 var 변수들은 private set이 불가능하므로 var 인스턴스 변수는 선언을 본체에 둔다.
- id가 인조키인 경우 nullable 하고 반드시 클래스 본문에 둔다.
- 본문에 entity attribute가 있더라도 최대한 private set을 지향하고 의미있는 setter를 만든다.

#### Error Handling
- 모든 에러는 code(에러의 종류를 나타내는 고유의 문자 코드), message(에러의 사유), status(HttpStatus)를 가진다.
- Spring Boot에서 관리하는 MethodArgumentNotValidException 에러를 제외한 비지니스 에러는 CommonException으로 관리한다.
- 이에 해당하지 않는 경우 Internal Server Error [500]을 띄우고 서버 로그에는 stack trace를 남긴다.

# Reference
- Kotlin Document [Coding Convention] (https://kotlinlang.org/docs/coding-conventions.html)
- Kotlin Document [Private set field in Constructor] (https://discuss.kotlinlang.org/t/private-setter-for-var-in-primary-constructor/3640)

## External API
- Firebase Cloud Messaging

## Library & Framework
- Spring Boot 2.4.1
- Kotlin 1.4.21
- Gradle 6.7.1
- Spring Security
- Spring Data JPA
- Spring Data Mongo
- Retrofit 2.9.0
- Kotlin Coroutine 1.4.2
- Firebase Admin 6.8.1

## Database
- MySQL
- MongoDB
