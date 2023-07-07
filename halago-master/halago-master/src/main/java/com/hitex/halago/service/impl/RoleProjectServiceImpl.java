package com.hitex.halago.service.impl;

import com.hitex.halago.model.RoleProject;
import com.hitex.halago.repository.RoleProjectRepository;
import com.hitex.halago.service.RoleProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RoleProjectServiceImpl implements RoleProjectService {
    @Autowired
    private RoleProjectRepository roleProjectRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RoleProject findRole(int id) {
        return roleProjectRepository.findRoleProject(id);
    }

    @Override
    public RoleProject insertRoleProject(RoleProject roleProject) {
        roleProjectRepository.save(roleProject);
        return roleProject;
    }

    @Override
    public RoleProject updateRoleProject(RoleProject roleProject) {
        roleProjectRepository.save(roleProject);
        return roleProject;
    }


    @Override
    public List<RoleProject> findAll() {
        return roleProjectRepository.findAll();
    }
}
