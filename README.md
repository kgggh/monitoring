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
  * GET
    - 호스트 조회 - 단일 건
      - URL /api/host/{hostName}
        - Response
          -  id
          -  hostName
          -  hostAddress
          -  alive
          -  lastAliveTime


    - 호스트 전체조회
      - URL  /api/host
        - Response
          -  id
          -  hostName
          -  hostAddress
          -  alive
          -  lastAliveTime


  * POST
    - 호스트등록 
      - URL /api/host
        - Requst 
          - hostName
          - hostAddress

          
