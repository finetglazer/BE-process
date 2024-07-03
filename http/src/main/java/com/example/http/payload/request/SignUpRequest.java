package com.example.http.payload.request;

import com.example.http.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

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
