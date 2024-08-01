package com.example.http.Service;

public interface Oauth2Service {
    String getInfo(String accessToken, String app);
    String getUrl(String authServer);
    String getAccessToken(String code, String app);
//    String getAccessTokenGoogle(String code);
//    Account saveUser(AccountSocialResponse userInfo);
}
