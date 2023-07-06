package com.hitex.halago.service;

import com.hitex.halago.model.RolePersonal;


public interface RolePersonalService {
    RolePersonal updateRole(RolePersonal rolePersonal);

    RolePersonal findPersonalByRole();

    RolePersonal findRolePersonalById(int id);
}
