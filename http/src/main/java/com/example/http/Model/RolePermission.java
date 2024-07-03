package com.example.http.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(	name = "role_permissions", uniqueConstraints = {@UniqueConstraint(columnNames = {"roleId"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "roleid")
    private Long roleId;

//    @Column(name = "accountid")
//    private Long accountId;

    @Column(name = "permission")
    private String permission;
}
