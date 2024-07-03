package com.example.http.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@AllArgsConstructor
public class JwtResponse {
    private Integer status;
    private String token;
}
