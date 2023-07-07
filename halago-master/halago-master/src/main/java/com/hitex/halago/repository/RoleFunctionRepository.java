package com.hitex.halago.repository;

import com.hitex.halago.model.RoleFunction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleFunctionRepository extends CrudRepository<RoleFunction,String> {
    @Query("Select role from RoleFunction role where role.idRole=:id")
    RoleFunction findRoleById(@Param("id") int id);
}
