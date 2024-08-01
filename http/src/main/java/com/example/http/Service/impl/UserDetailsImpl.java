package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.RolePermission;
import com.example.http.Model.User;
import com.example.http.Repository.RolePermissionRepository;
import com.example.http.Repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Component
@Data
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private static UserRepository userRepository;
    private static RolePermissionRepository rolePermissionRepository;

    @Autowired
    public UserDetailsImpl(UserRepository userRepository, RolePermissionRepository rolePermissionRepository) {
        UserDetailsImpl.userRepository = userRepository;
        UserDetailsImpl.rolePermissionRepository = rolePermissionRepository;
    }

    public UserDetailsImpl(Long id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Account account) throws JsonProcessingException {
        User user = userRepository.findUserByUsername(account.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + account.getUsername()));
        RolePermission permissionRole = rolePermissionRepository.findRolePermissionByRoleId(user.getRoleId());
        JsonNode permissions = null;
        ObjectMapper objectMapper = new ObjectMapper();

        if (permissionRole.getPermission() != null)
            permissions = objectMapper.readTree(permissionRole.getPermission());

        List<String> permissionList = new ArrayList<>();

        if (permissions != null) {
            permissionList = jsonNodeToList(permissions);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissionList) {
            if (!ObjectUtils.isEmpty(permission)) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        return new UserDetailsImpl(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                authorities
        );

    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static List<String> jsonNodeToList(JsonNode jsonNode) {
        List<String> arrayList = new ArrayList<>();
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (jsonNode.get(fieldName).asBoolean()) {
                arrayList.add(fieldName);
            }
        }
        return arrayList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }



}
