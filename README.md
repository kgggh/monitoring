# monitoring
등록된 호스트들의 활성화 상태 정보를 제공하는 API

### 개발환경
  - java 8
  - mariaDB 10.6
  - gradle 7.1.1
  - springboot 2.5.2
  - swagger2 2.6.1

### 요구사항
  - 등록된 원격 호스트들의 Alive 상태를 모니터링하는 서버를 개발한다.
  - Alive 상태 확인은 InetAddress.isReachable() 사용을 권장한다. (다른 방법도 가능)
  - 호스트 등록 관리 REST API를 제공해야 한다.
  - 서버가 재시작되어도 등록된 호스트들은 유지되어야 한다.
  - 호스트는 최대 100개로 제한한다.
  - 호스트 등록 시 name, address 필드를 제공해야 한다. (name, address 중복 불가)
  - 호스트들의 Alive 상태를 조회하는 REST API를 제공해야 한다.
  - Alive 상태 조회 REST API의 단위는 한 호스트, 그리고 전체 호스트로 가능해야 한다.
  - Alive 상태 조회 REST API의 응답 형식은 json 이며 deserialize 가능해야 한다.
  - Alive 상태 조회 REST API의 응답에는 마지막 Alive 시간을 포함해야 한다.

### REST API 정의
  * swagger 참고 http://localhost:8080/swagger-ui.html
  * 공통 
    - status
      - 200 (OK)
  * GET
    - 호스트 조회 - 단일 건
      - URL/api/host/{hostName}
        - Response
          - id: 번호
          - host_name: 호스트이름
          - host_address: 호스트주소
          - alive: 활성화상태(Y:활성화, N:비활성화)
          - last_alive_time: 마지막 활성화 시간
      - status
        - 404 (NOT_FOUND)
          - 동륵되지 않은 호스트
        
    - 호스트 전체조회
      - URL /api/host
        - Response
          - id: 번호
          - host_name: 호스트이름
          - host_address: 호스트주소
          - alive: 활성화상태(Y:활성화, N:비활성화)
          - last_alive_time: 마지막 활성화 시간
          
  * POST
    - 호스트등록 
      - URL /api/host
        - Requst 
          - host_name: 호스트이름
          - host_address: 호스트주소
      - status
        - 409 (Conflict)
          - 등록된 호스트가 존재
        - 423 (LOCKED) 
          - 최대 100개까지 등록가능
        

### 궁금증
* RestAPI를 현업에서 어떻게 구성하는지 제대로 모름.
* HTTP Status code 프로젝트를 진행하며 정의하기에 애매모호한 부분 존재
  - 자원이 존재하지 않을 시 404(NOT_FOUND)를 쓰는게 적절할지?
  - 중복된 자원이 존재 할시 409(Conflict)를 쓰는게 적절할지?
  - POST 요청시 Maximum일시 423(LOCKED)을 쓰는게 적절할지? 
* 요구사항 중 Alive 상태 조회 REST API의 응답 형식은 json 이며 deserialize 가능해야 한다 -> 애매모호한 부분
  - 역직렬화가 Json TO Java객체를 의미하는지?
    - *이 부분을 제대로 진행 못함.

### 설계
* Table은 monitoring이란 table 하나로 구성 ->host alive 정보만 따로 Table로 설계하려다 간단하게 설계.
* Restful하게 어떻게 구성해야될지?
* Response를 어떻게 구성할지 생각
  - Http Status와 별개로 error_code와 message를 body에 data와 함께 던져주는게 낫다 생각
  - enum class로 errorCode를 관리 할까 생각
    - abstract class(BasicResponse)로 구성 
      - CommonResponse class,ErrorResponse class가 상속받아 child class가 처리하도록 구성   
  - error_code는 빼고 진행 -> message와 data만 담아서 줌. 
 
* monitoring Table 정보
  - id: 번호
  - host_name: 호스트이름
  - host_address: 호스트주소
  - alive: 활성화상태(Y:활성화, N:비활성화)
  - last_alive_time: 마지막 활성화 시간
 
* host들의 connection 상태를 Realtime으로 Checking -> Async MultiThread로 진행하며 job scheduler 돔(  
  - 등록된 호스트 수 만큼 loop를 돔 -> batch성 job이 아니라 판단(Data 집계성 job) 병렬 처리 위해 parallelStream로 진행
    - process 흐름
      - 전체 호스트 조회 -> 하나의 호스트 조회 -> 해당 호스트 Connetion 확인 - > 활성화상태(Y:활성화, N:비활성화)에 따라 update 
        - 처리하려는 데이터가 적어서 for문과 parallelStream.foreach 속도차이를 느끼지 못함.
    - 스케쥴러는 매초마다 check 하기 위해 fixedDelay,fixedRate를 두고 고민하다 job start Time을 기준으로 1초마다 주기적으로 돌게끔 fixedRate 사용
  - isReachable(1000)으로 Setting -> true 떨어질 시 alive: Y, last_alive_time: realtime으로 동기화 진행
  
  
### issue
* API Response charset UTF-8 적용이 안됌
  - Content-type Charset이 사라짐
  - APPLICATION_JSON_UTF8_VALUE이 5.2부터 Deprecated됌

* 해결
  - properties 추가
    - server.servlet.encoding.charset= UTF-8
    - server.servlet.encoding.force-response=true
    - server.servlet.encoding.force-request=true

 
   





