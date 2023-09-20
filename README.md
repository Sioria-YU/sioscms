# sioscms
- last-update-date : 2023.9.20
1. Summary
   - SpringBoot, SpringMVC, JPA, JSP를 사용하여 구성한 cms 프로젝트
   - 다양한 DB환경에 종속되지 않고 사용 가능한 ORM 기반의 CMS 프로젝트 구성

2. Specs
   - JDK : Eclipse Temurin 17(OpenJDK)
   - SpringBoot : 2.7.9
   - SpringFramework : 5.3.25
   - SpringSecurity : 2.7.9
   - SpringDataJPA :2.8.9
   - Google-Guava : 31.1
   - Default Server : Tomcat 9
   - Default Database : PostgreSQL
   - Lombok : 1.18.26

3. 디렉터리 구성의 이유 -> MVC와 REST
   - 내/외부 연계 API 혹은 프론트,앱 프로젝트와 연결되서 사용할 REST-Controller와 MVC패턴의 Controller-Jsp로 디렉터리를 구분하여 구성

4. apps와 그 외 컨트롤러의 역할 -> MVC와 REST 연결
   - apps : 도메인을 기준으로 구성하며, RESTful API를 제공
   - cms,prjct... : 프로젝트 View를 기반한 MVC 패턴의 Controller, Service 제공

5. 메서드 레벨 권한 적용
   - @Auth(role = Auth.Role.ADMIN) : 관리자 권한 검사
   - @Auth(role = Auth.Role.USER) : 사용자 권한 검사

6. EgovFrame 적용
    - Service 레벨에서 EgovAbstractServiceImpl 상속
    - SpringDataJPA의 경우 적용 규정이 없음

#Application.yml Setting Guide
1. Database
   1. mysql
      - jpa:
          database: mysql
            database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
      - spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
   2. postgres
      - database: postgresql
      - spring:
          datasource:
            driver-class-name: driver-class-name: org.postgresql.Driver
   3. oracle
      - database: oracle
      - spring:
          datasource:
            driver-class-name: oracle.jdbc.driver.OracleDriver
