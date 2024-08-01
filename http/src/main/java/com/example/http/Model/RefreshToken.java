package com.example.http.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "refresh_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "expiry_date")
    private Date expiryDate;

    @Column(nullable = false, name = "token")
    private String token;

    public RefreshToken(Long userId, String token,  Date expiryDate) {
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.token = token;
    }
}
