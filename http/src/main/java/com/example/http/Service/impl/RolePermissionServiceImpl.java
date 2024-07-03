package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.RolePermission;
import com.example.http.Model.User;
import com.example.http.Service.RolePermissionService;
import com.example.http.payload.request.RolePermissionUpdate;
import com.example.http.payload.response.BaseResponse;
import com.example.http.repository.AccountRepository;
import com.example.http.repository.RolePermissionRepository;
import com.example.http.repository.UserRepository;
import com.example.http.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, List<String>> getAllPermission() {
        Map<String, List<String>> jsonResponse = new HashMap<>();
        jsonResponse.put("system_role", Arrays.asList("system_role_view", "system_role_viewDetail", "system_role_create", "system_role_update", "system_role_delete"));
        jsonResponse.put("system_user", Arrays.asList("system_user_view", "system_user_viewDetail","system_user_create", "system_user_update", "system_user_delete","system_user_lock"));
       // jsonResponse.put("system_organization", Arrays.asList("system_organization_view", "system_organization_viewDetail","system_organization_approve", "system_organization_update", "system_organization_delete","system_organization_lock"));
       // jsonResponse.put("system_introduceOrg", Arrays.asList("system_introduceOrg_view", "system_introduceOrg_viewDetail","system_introduceOrg_create", "system_introduceOrg_update", "system_introduceOrg_delete"));
        // jsonResponse.put("travel_point", Arrays.asList("travel_point_view", "travel_point_viewDetail", "travel_point_create", "travel_point_update", "travel_point_delete"));
        //jsonResponse.put("travel_event", Arrays.asList("travel_event_view", "travel_event_viewDetail", "travel_event_create", "travel_event_update", "travel_event_delete"));
        jsonResponse.put("view_report", Arrays.asList("view_report_view", "view_report_create"));
       // jsonResponse.put("log_analysis", Arrays.asList("log_analysis_view", "log_analysis_update"));
        return jsonResponse;
    }

    @Override
    public BaseResponse<?> getPermissionByRoleId(Long roleId) {
        try {
            RolePermission rolePermission = rolePermissionRepository.findRolePermissionByRoleId(roleId);
            return new BaseResponse<>(rolePermission.getPermission());
        } catch (Exception e) {
            return new BaseResponse<>("ROLEID.ERROR.NOTEXIST");
        }

    }

    @Override
    public BaseResponse<?> getPermissionByAccountId(Long accountId) {
        Account account = accountRepository.findAccountById(accountId).orElse(null);
        if (account == null) {
            return new BaseResponse<>("ACCOUNTID.ERROR.NOTEXIST");
        }
        User user = userRepository.findUserByUsername(account.getUsername()).orElse(null);
        if (user == null) {
            return new BaseResponse<>("USER.ERROR.NOTEXIST");
        }
        RolePermission rolePermission = rolePermissionRepository.findRolePermissionByRoleId(user.getRoleId());
        if (rolePermission == null) {
            return new BaseResponse<>("ROLEPERMISSION.ERROR.NOTEXIST");
        }
        return new BaseResponse<>(rolePermission.getPermission());
    }

    @Override
    public BaseResponse<?> getPermission(HttpServletRequest request) {
        Account account = jwtUtils.checkJwt(request);
        if (account == null) {
            return new BaseResponse<>("ACCOUNT.ERROR.NOTEXIST");
        }
        return getPermissionByAccountId(account.getId());
    }

    @Override
    public BaseResponse<?> updateRolePermission(RolePermissionUpdate rolePermissionUpdate) {
        RolePermission rolePermission = rolePermissionRepository.findRolePermissionByRoleId(rolePermissionUpdate.getRoleId());
        if (rolePermission == null) {
            return new BaseResponse<>("ROLEID.ERROR.NOTEXIST");
        }
        if (rolePermissionUpdate.getPermission().isEmpty()) {
            rolePermission.setPermission(null);
            rolePermissionRepository.save(rolePermission);
            return new BaseResponse<>("SUCCESS_UPDATE_PERMISSION");
        }
        rolePermission.setPermission(convertMappingJson(rolePermissionUpdate.getPermission().toString()));
        rolePermissionRepository.save(rolePermission);
        return new BaseResponse<>("SUCCESS_UPDATE_PERMISSION");
    }

    private String convertMappingJson(String string){
        String cleanedInput =string.replaceAll("[{}]", "");
        String[] pairs = cleanedInput.split(",");
        Map<String, String> jsonMap = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                jsonMap.put(key, value);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonOutput = objectMapper.writeValueAsString(jsonMap);
            return jsonOutput;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    @Override
//    public BaseResponse<?> getPermissionByAccountId(Long accountId) {
//        RolePermission rolePermission = rolePermissionRepository.findRolePermissionByAccountId(accountId);
//        return new BaseResponse<>(rolePermission.getPermission());
//    }
}
