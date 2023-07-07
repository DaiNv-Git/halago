package com.hitex.halago.repository;

import com.hitex.halago.model.RolePersonal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolePersonalRepository extends CrudRepository<RolePersonal, String> {
    @Query("select rop from RolePersonal rop where rop.status = 0 or rop.status =1")
    List<RolePersonal> findAll();

    @Query("select rop from RolePersonal rop where  rop.idRolePersonal=:id and rop.status = 0 or rop.status =1")
    RolePersonal findRolePersonal(@Param("id") int id);

    @Query("select rop from RolePersonal rop where rop.idPersonal=:id")
    RolePersonal findPersonal(@Param("id") int id);

    @Query("select  p.idRole from RolePersonal p where p.idPersonal=:id")
    int getRolePersonal(@Param("id") int id);
}
