## Spring Boot Keycloak

This module contains articles about Keycloak in Spring Boot projects.

## Relevant articles:
- [A Quick Guide to Using Keycloak with Spring Boot](https://www.baeldung.com/spring-boot-keycloak)
- [Custom User Attributes with Keycloak](https://www.baeldung.com/keycloak-custom-user-attributes)
- [Customizing the Login Page for Keycloak](https://www.baeldung.com/keycloak-custom-login-page)
- [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)
- [Customizing Themes for Keycloak](https://www.baeldung.com/spring-keycloak-custom-themes)
- [Simple Single Sign-On with Spring Security OAuth2](https://www.baeldung.com/sso-spring-security-oauth2)



http://127.0.0.1:10090/error/index_win.html

http://localhost:8180/auth/realms/SpringBootKeycloak

### openid connect 처리: 
post  http://localhost:8180/auth/realms/SpringBootKeycloak/protocol/openid-connect/token

 

curl -d "client_id=login-app&username=user1&password=user1&grant_type=password" -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8180/auth/realms/SpringBootKeycloak/protocol/openid-connect/token

curl -d "client_id=login-app&username=user1&password=user1&grant_type=password" -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8180/auth/realms/SpringBootKeycloak/protocol/openid-connect/token
