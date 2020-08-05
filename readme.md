## 스프링 공부 with 토비의 스프링

1장 1. 초난감 DAO 코드

https://withseungryu.tistory.com/66

### 👉 Database와 연동하기 위해 JDBC를 사용할 것인데, 일반적인 순서는 아래와 같습니다.


* DB 연결을 위한 Connection을 가져온다.
* SQL을 담은 Statement(or PreparedStatement)를 만든다.
* 만들어진 Statement를 실행한다.
* 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 정보를 저장할 오브젝트(User)에 옮겨준다.
* 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소스는 작업을 마친 후 반드시 닫아준다.
* JDBC API가 만들어내는 예외를 잡아서 직접 처리하거나, throws를 선언해 예외가 발생하면 메서드 밖으로 던지게 한다.
