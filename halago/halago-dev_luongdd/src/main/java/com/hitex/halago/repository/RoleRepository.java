package com.hitex.halago.repository;

import com.hitex.halago.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, String> {
    @Query("select ro from Role ro where (:name IS NULL OR lower(ro.name) LIKE lower(concat('%', concat(:name, '%'))))")
    List<Role> findAll(@Param("name")String name);

    @Query("Select fun from Role fun where fun.name=:name")
    Role checkRoleName(@Param("name") String name);

    @Query("Select role from Role role where role.idRole=:id")
    Role findRoleById(@Param("id") int id);
}
