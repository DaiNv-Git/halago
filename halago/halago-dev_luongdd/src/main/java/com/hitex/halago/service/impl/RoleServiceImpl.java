package com.hitex.halago.service.impl;

import com.hitex.halago.model.DAO.RoleAccount;
import com.hitex.halago.model.DAO.RoleDao;
import com.hitex.halago.model.Role;
import com.hitex.halago.repository.RoleRepository;
import com.hitex.halago.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RoleRepository RoleRepository;
    @Override
    public List<RoleAccount> getListRole(String name, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select role ");
        builder.append("from Role role Where (:name IS '' OR lower(role.name) LIKE lower(concat('%', concat(:name, '%'))))");
        Query query = entityManager.createQuery(builder.toString(), Role.class);
        query.setParameter("name", name);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Role> resultList = query.getResultList();
        List<RoleAccount> roleAccounts = new ArrayList<>();
        for (Role role:resultList) {
            RoleAccount roleAccount = new RoleAccount();
            BeanUtils.copyProperties(role,roleAccount);
            if(roleAccount.getStatus() == 1){
                roleAccount.setStatusName("Active");
            }else{
                roleAccount.setStatusName("InActive");
            }
            roleAccounts.add(roleAccount);
        }
        return roleAccounts;
    }

    @Override
    public RoleDao getListFunctionByRoleId(int id) {
        RoleDao roleDao = new RoleDao();
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Select roleFunc.id,roleFunc.idFunction, roleFunc.idRole, role.name ");
            builder.append("from Role role ,RoleFunction roleFunc where roleFunc.idRole=:idRole and role.idRole = roleFunc.idRole ");
            Query query = entityManager.createQuery(builder.toString());
            query.setParameter("idRole",id);
            List<Object[]> resultList = query.getResultList();

            for (Object[] objects : resultList) {
                roleDao.setId((Integer) objects[0]);
                roleDao.setIdFunction(com.hitex.halago.utils.StringUtils.hashTagToIntArray(String.valueOf(objects[1])));
                roleDao.setIdRole((Integer) objects[2]);
                roleDao.setRoleName(String.valueOf(objects[3]));
            }
        }catch (Exception e){
            logger.info(e.getMessage(),e);
        }
        return roleDao;
    }
}
