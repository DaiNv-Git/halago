package com.hitex.halago.repository;

import com.hitex.halago.model.RoleProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleProjectRepository extends CrudRepository<RoleProject, String> {
    @Query("select rp from RoleProject rp where rp.idRoleProject=:id")
    RoleProject findRoleProject(@Param("id") int id);

    @Query("select  rp from RoleProject  rp ")
    List<RoleProject> findAll();
}
