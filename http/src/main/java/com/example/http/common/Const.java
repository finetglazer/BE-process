package com.example.http.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class Const {
    public static class STATUS_RESPONSE {
        public static Integer SUCCESS = 1;
        public static Integer ERROR = 0;


    }
    @Value("${minio.url}${minio.bucket}")
    public static String MINIO_PATH;

    public void setMinioPath(String minioUrl) {
        Const.MINIO_PATH = minioUrl;
    }

    public static String PASSWORD_DEFAULT = "123456";
//
//    @NotNull
//    @Contract(pure = true)
//    public static String getPathMinio(String fileName) {
//        return MINIO_PATH + "/" + fileName;
//    }

    public static ResponseEntity<?> postReqWithoutBody(String AccessToken, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public static ResponseEntity<?> getReq(String token, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the request and get the response
        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException e) {
            // Log the error or handle it as needed
            System.err.println("Request failed: " + e.getMessage());
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
    public enum TYPE_USER {
        user,
        userfolder,
        role,
        rolefolder
    }

    public enum ACCOUNT_STATUS {
        activate,
        pending,
        inactive,
    }

    public enum   ORGANIZATION_TYPE {
        organization,
        management,
    }

    public enum GENDER {
        male,
        female
    }
}
