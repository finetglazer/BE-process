package com.example.http.Service.impl;

import com.example.http.Payload.response.BaseResponse;
import com.example.http.Service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.http.common.Const.getReq;
import static com.example.http.common.Const.postReqWithoutBody;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {
    @Value("${google.service.endpoint}")
    private String GOOGLE_URL;

    @Override
    public BaseResponse<?> getInfo(String token) {
        // Create url
        String url = GOOGLE_URL + "/drive/v3/about?fields=user,storageQuota";

        // Set up headers with token
        ResponseEntity<?> response = getReq(token, url);
        // Return response
        return new BaseResponse<>(response.getBody());
    }

    @Override
    public BaseResponse<?> getApps(String token) {
        // Create url
        String url = GOOGLE_URL + "/drive/v3/apps";

        // Set up headers with token
        ResponseEntity<?> response = getReq(token, url);
        // Return response
        return new BaseResponse<>(response.getBody());
    }

    @Override
    public BaseResponse<?> getDrives(String token) {
        // Create url
        String url = GOOGLE_URL + "/drive/v3/drives";

        // Set up headers with token
        ResponseEntity<?> response = getReq(token, url);
        // Return response
        return new BaseResponse<>(response.getBody());
    }

    @Override
    public BaseResponse<?> createDrive(String token, String name) {
        // Create url
        String url = GOOGLE_URL + "/drive/v3/drives";


        // Set up headers with token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("name", name);
        body.add("requestId", "test-request-id");

        //Create the HttpEntity object
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        //Add param request id
        String requestId = java.util.UUID.randomUUID().toString() ; //uuid
        String fullUrl = url + "?requestId=" + requestId;
        // Return response
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.exchange(fullUrl, HttpMethod.POST, requestEntity, String.class);

        return new BaseResponse<>(response.getBody());
    }

}
