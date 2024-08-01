package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Payload.response.AccountSocialResponse;
import com.example.http.Repository.AccountRepository;
import com.example.http.Service.Oauth2Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.http.common.Const.postReqWithoutBody;

@Service
@RequiredArgsConstructor
public class Oauth2ServiceImpl implements Oauth2Service {
    @Value("https://www.facebook.com/v12.0/dialog/oauth?client_id=%s&redirect_uri=%s&scope=email,public_profile&response_type=code")
    private String link_get_code;

    private final ClientRegistrationRepository clientRegistrationRepository;
    @Value("${google.link.get.authCode}")
    private String google_link_get_authcode;
    @Value("${github.link.get.authCode}")
    private String github_link_get_authcode;
    @Value("${google.link.get.accessToken}")
    private String google_link_get_Accesstoken;
    @Value("${github.link.get.accessToken}")
    private String github_link_get_Accesstoken;
    @Value("${github.link.get.info}")
    private String github_link_get_userInfo;
    @Value("${google.link.get.info}")
    private String google_link_get_userInfo;


    @Override
    public String getInfo(String AccessToken, String app) {
        String url = app.equals("google") ? google_link_get_userInfo : github_link_get_userInfo;
        ResponseEntity<String> response = (ResponseEntity<String>) postReqWithoutBody(AccessToken, url);
        return response.getBody();
    }


    @Override
    public String getUrl(String authServer) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(authServer);
        String clientId = clientRegistration.getClientId();
        String redirectUri = clientRegistration.getRedirectUri(); // The redirect_uri should match what you've registered in your GitHub app
        String link = authServer.equals("facebook") ? link_get_code : authServer.equals("google") ? google_link_get_authcode : github_link_get_authcode;
        // GitHub authorization URL template
        String url = String.format(
                link,
                clientId,
                redirectUri
        );
        return url;
    }

    @Override
    public String getAccessToken(String code, String app) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(app);
        String clientId = clientRegistration.getClientId();
        String clientSecret = clientRegistration.getClientSecret();
        String redirectUri = clientRegistration.getRedirectUri();

        if (app.equals("google")) {
            String url = "https://oauth2.googleapis.com/token";

            // Prepare the request body
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("code", code);
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("redirect_uri", redirectUri);
            body.add("grant_type", "authorization_code");

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Create the HttpEntity
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            // Send the POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // Check response and parse access token
            try {
                if (response.getStatusCode().is2xxSuccessful()) {
                    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
                    return jsonNode.get("access_token").asText();
                } else {
                    // Handle error response
                    throw new RuntimeException("Error response: " + response.getBody());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error parsing access token response", e);

            }
        } else {
            String url = String.format(github_link_get_Accesstoken, clientId, clientSecret, code, redirectUri);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String accessToken = response.getBody().split("&")[0].split("=")[1];
            return accessToken;

        }

    }

}
