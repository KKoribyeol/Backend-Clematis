# 꼬리별 [KKo-Ri-Byeol]
푸시 서비스가 필요해서 사용해본 NHN Cloud의 TOAST 푸시 서비스,  
TOAST 푸시 서비스를 사용하면서 불편한 점을 개선하여  
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
- Request/Response class는 Controller Layer에서만 사용하며, Service Layer에 넘겨주지 않는다.
- Service Layer는 SRP 원칙을 엄격하게 취급하여 CRUD, Retrofit을 통한 요청 등 하나의 일을 하도록 한다.  
따라서 TemplateService와 같은 Service 객체를 만들지 않는다.

#### Class
- Request/Response class는 data class로 작성한다.
- 생성자는 매개변수마다 행을 나누고 트레일링 콤마를 사용한다.  
단, 매개변수가 하나일 경우에는 행을 나누지 않고 트레일링 콤마(trailing comma)도 사용하지 않는다.
- 확장이 용이한 경우에는 인터페이스를 사용하고, 외부(External) API를 사용하는 경우에는 **반드시** 인터페이스를 사용한다.
- 

## External API
- Firebase Cloud Messaging

## Library & Framework
- Spring Boot 2.4.1
- Kotlin 1.4.21
- Gradle 6.7.1
- Retrofit 2.9.0
- Kotlin Coroutine 1.4.2
- Spring Data JPA
- Firebase Admin 6.8.1

## Database
- MySQL
- MongoDB
