package com.hitex.halago.service;

import com.hitex.halago.model.DAO.RoleAccount;
import com.hitex.halago.model.DAO.RoleDao;
import com.hitex.halago.model.Role;

import java.util.List;

public interface RoleService {
    List<RoleAccount> getListRole(String name, Integer pageSize, Integer pageNumber);

    RoleDao getListFunctionByRoleId(int id);
}
