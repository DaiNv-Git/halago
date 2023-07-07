package com.hitex.halago.service;

import com.hitex.halago.model.Role;
import com.hitex.halago.model.RoleProject;

import java.util.List;

public interface RoleProjectService {

    RoleProject findRole(int id);

    RoleProject insertRoleProject(RoleProject roleProject);

    RoleProject updateRoleProject(RoleProject roleProject);

    List<RoleProject> findAll();

}
