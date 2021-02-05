## Spring 5 Security OAuth

This module contains articles about Spring 5 OAuth Security

## Relevant articles:

- [Spring Security 5 – OAuth2 Login](https://www.baeldung.com/spring-security-5-oauth2-login)
- [Extracting Principal and Authorities using Spring Security OAuth](https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor)
- [Customizing Authorization and Token Requests with Spring Security 5.1 Client](https://www.baeldung.com/spring-security-custom-oauth-requests)
- [Social Login with Spring Security in a Jersey Application](https://www.baeldung.com/spring-security-social-login-jersey)

## - [Customizing Authorization and Token Requests with Spring Security 5.1 Client](https://www.baeldung.com/spring-security-custom-oauth-requests)

Spring Security 5.1 provides support for customizing OAuth2 authorization and token requests.
## Okta(옥타) 는 SSO(Single Sign On, 통합로그인)와 강력한 보안 인증 서비스를 제공해 여러 업무 도구에 한번에 쉽게 로그인함

### 2. Custom Authorization Request
    we'll customize the OAuth2 authorization request. 
    We can modify standard parameters and add extra parameters to the authorization request as we need.

    To do so, we need to implement our own OAuth2AuthorizationRequestResolver:
    public class CustomAuthorizationRequestResolver 
                implements OAuth2AuthorizationRequestResolver {

     OAuth2AuthorizationRequestRedirectFilter
        DefaultOAuth2AuthorizationRequestResolver  and resolve
    => customizeAuthorizationRequest
   => OAuth2AuthorizationRequestRedirectFilter.doFilterInternal 에서 resolve호출
   => CustomAuthorizationRequestResolver 를 WebSecurityConfigurerAdapter의 configure 에서 authorizationRequestResolver 에 등록
####  @PropertySource("application-oauth2.properties") : SpringOAuthApplications에 config한다. : 8081
####  public class CustomRequestSecurityConfig extends WebSecurityConfigurerAdapter {   

### 3. Customizing Authorization Request Standard Parameters
        OAuth2AuthorizationRequest
      .from(req).state("xyz").build()
### 4. Authorization Request Extra Parameters 권한 요청
        OAuth2AuthorizationRequest
            .from(req)
            .additionalParameters(extraParams)
            .build();
### 4.1. Custom Okta Authorize Request
        #### from -- Okta Authorization Server.
         idp parameter
          https://developer.okta.com/docs/reference/api/oidc/#authorize
### 5. Custom Token Request  사용자정의 토큰 요청
        We can customize the token request by customizing OAuth2AccessTokenResponseClient.
        The default implementation for OAuth2AccessTokenResponseClient is
        DefaultAuthorizationCodeTokenResponseClient.
        config 에서
            .accessTokenResponseClient(accessTokenResponseClient())

        DefaultAuthorizationCodeTokenResponseClient 를 CustomRequestEntityConverter
      accessTokenResponseClient
### 6. Token Request Extra Parameters
       CustomRequestEntityConverter 
   Our Converter transforms OAuth2AuthorizationCodeGrantRequest to a RequestEntity.


### 7. Custom Token Response Handling
### 7.1. LinkedIn Token Response Handling
    LinkedinTokenResponseConverter 
### 8. Conclusion
    in this article, we learned how to customize OAuth2 authorization and token requests by adding or modifying request parameters.