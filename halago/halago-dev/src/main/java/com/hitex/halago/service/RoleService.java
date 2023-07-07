package com.hitex.halago.service;

import com.hitex.halago.model.dao.RoleAccount;
import com.hitex.halago.model.dao.RoleDao;

import java.util.List;

public interface RoleService {
    List<RoleAccount> getListRole(String name, Integer pageSize, Integer pageNumber);

    RoleDao getListFunctionByRoleId(int id);
}
