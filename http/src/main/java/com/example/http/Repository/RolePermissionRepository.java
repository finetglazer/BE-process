package com.example.http.Repository;

import com.example.http.Model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    RolePermission findRolePermissionByRoleId(Long roleId);
//    RolePermission findRolePermissionByAccountId(Long userId);

    @Query(value = "SELECT rp FROM RolePermission rp WHERE rp.roleId = (SELECT u.roleId FROM User u where u.username = (SELECT a.username FROM Account a WHERE a.id = :accountId)) ")
    RolePermission findRolePermissionByAccountId(Long accountId);
}
