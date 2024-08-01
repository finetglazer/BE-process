package com.example.http.Payload.request;

import com.example.http.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    @ValidPassword
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    @ValidPassword
    private String confirmPassword;

}
