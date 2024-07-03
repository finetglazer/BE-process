package com.example.http.Model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    public Account (String username, String password) {
        this.username = username;
        this.password = password;
    }

}
