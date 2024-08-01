package com.example.http.Payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSocialResponse {
    Long id;
    String email;
    String birthday;
    String name;
    String provider;
    String gender;

    @Override
    public String toString() {
        return "AccountSocialResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", gender='" + gender + '\'' + "}";
    }
}
