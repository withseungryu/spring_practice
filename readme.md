# 스프링 공부 with 토비의 스프링

## 1장 1. 초난감 DAO 코드

https://withseungryu.tistory.com/66

### 👉 Database와 연동하기 위해 JDBC를 사용할 것인데, 일반적인 순서는 아래와 같습니다.


* DB 연결을 위한 Connection을 가져온다.
* SQL을 담은 Statement(or PreparedStatement)를 만든다.
* 만들어진 Statement를 실행한다.
* 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 정보를 저장할 오브젝트(User)에 옮겨준다.
* 작업 중에 생성된 Connection, Statement, ResultSet 같은 리소스는 작업을 마친 후 반드시 닫아준다.
* JDBC API가 만들어내는 예외를 잡아서 직접 처리하거나, throws를 선언해 예외가 발생하면 메서드 밖으로 던지게 한다.

### 🧾 정리 

지금까지 간단한 DAO코드를 만들어 보았습니다.

하지만 이 DAO 클래스에는 객체지향 설계 원칙(SOLID)들 중 개방 폐쇄 원칙에 만족하지 못하고 있습니다.



개방 폐쇠 원칙이란?

 '클래스나 모듈은 확장에는 열려 있어야 하고 변경에는 닫혀 있어야 한다'



즉, 이 Dao 클래스를 다양한 클라이언트들이 사용할 텐데,,,,

모든 클라이언트들이 다 같은 DB connect 방식을 사용하지 않을 것입니다.



만약 모든 클라이언트들의 DB connect 방식이 다르다면, 매번 Dao 객체를 고쳐주어야 할 것입니다.



하지만 대부분의 개발자들은 고객에게 소스를 직접 공개하고 싶지 않아 하기 때문에,

클라이언트들이 원하는 DB connection 방식을 아래와 같이 요구에 맞게 생성해줘야 합니다.
