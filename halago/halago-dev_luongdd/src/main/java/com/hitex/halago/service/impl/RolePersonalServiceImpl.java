package com.hitex.halago.service.impl;

import com.hitex.halago.model.RolePersonal;
import com.hitex.halago.repository.RolePersonalRepository;
import com.hitex.halago.service.RolePersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePersonalServiceImpl implements RolePersonalService {
    @Autowired
    private RolePersonalRepository rolePersonalRepository;

    @Override
    public RolePersonal updateRole(RolePersonal rolePersonal) {
        rolePersonalRepository.save(rolePersonal);
        return rolePersonal;
    }

    @Override
    public RolePersonal findPersonalByRole() {
        return null;
    }

    @Override
    public RolePersonal findRolePersonalById(int id) {
        return rolePersonalRepository.findRolePersonal(id);
    }
}
