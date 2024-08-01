package com.example.http.Payload.request;

import com.example.http.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;


    @Min(2)
    private Long roleId;

    @NotBlank
    @Size(min = 6, max = 40)
    @ValidPassword
    private String password;
}
