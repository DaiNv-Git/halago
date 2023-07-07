package com.hitex.halago.service.impl;

import com.hitex.halago.model.dao.AccountUser;
import com.hitex.halago.model.dao.FunctionApiDao;
import com.hitex.halago.model.dao.RoleDao;
import com.hitex.halago.model.dao.RoleFunction.RoleFunction;
import com.hitex.halago.model.dao.UserDao;
import com.hitex.halago.model.User;
import com.hitex.halago.repository.UserRepository;
import com.hitex.halago.service.FunctionApiService;
import com.hitex.halago.service.RoleService;
import com.hitex.halago.service.UserSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserSerivce {
    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @PersistenceContext
    EntityManager entityManager;
    @PersistenceContext
    EntityManager entityManager1;
    @Autowired
    UserRepository userrepository;
    @Autowired
    RoleService roleService;
    @Autowired
    FunctionApiService functionApiService;

    @Override
    public UserDao findUser(String username) {
        List<Object[]> result = entityManager.createQuery("select u.id,u.username,u.password,r.name,u.role,u.email from User u,Role r where r.id=u.role  and u.username=:username  and (u.role = 1 or u.role = 3)").setParameter("username", username).getResultList();
        UserDao userDao = null;
        if (!ObjectUtils.isEmpty(result)) {
            userDao = new UserDao();
            Object[] o = result.get(0);
            userDao.setId(Integer.parseInt(o[0].toString()));
            userDao.setUsername(String.valueOf(o[1]));
            userDao.setPassword(String.valueOf(o[2]));
            userDao.setRoles(new String[]{String.valueOf(o[3])});
            userDao.setRole(Integer.parseInt(o[4].toString()));
            userDao.setEmail(String.valueOf(o[5]));
        }
        return userDao;
    }

    @Override
    public UserDao findUserPotal(String username) {
        List<Object[]> resukt = entityManager.createQuery("select u.id,u.username,u.password,r.name,u.role,u.email from User u,Role r where r.id=u.role  and (u.username=:username or u.email=:username) and u.role = 4").setParameter("username", username).getResultList();
        UserDao userDao = null;
        if (!ObjectUtils.isEmpty(resukt)) {
            userDao = new UserDao();
            Object[] o = resukt.get(0);
            userDao.setId(Integer.parseInt(o[0].toString()));
            userDao.setUsername(String.valueOf(o[1]));
            userDao.setPassword(String.valueOf(o[2]));
            userDao.setRoles(new String[]{String.valueOf(o[3])});
            userDao.setRole(Integer.parseInt(o[4].toString()));
            userDao.setEmail(String.valueOf(o[5]));
        }
        return userDao;
    }


    @Override
    public com.hitex.halago.model.dao.User findAccount(String username) {
        User user = userrepository.findUser(username);
        com.hitex.halago.model.dao.User account = null;
        if (!ObjectUtils.isEmpty(user)) {
            account = new com.hitex.halago.model.dao.User();
            BeanUtils.copyProperties(user, account);
        }
        return account;
    }

    @Override
    public User registerUser(User user) {
        try {
            userrepository.save(user);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<AccountUser> getListUser(String name, Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select user.id, user.username, user.password, user.role, role.name,user.created,user.status ");
        builder.append("from User user, Role role Where (:name IS '' OR lower(user.username) LIKE lower(concat('%', concat(:name, '%')))) and user.role=role.idRole ");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("name", name);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> resultList = query.getResultList();
        List<AccountUser> userList = new ArrayList<>();
        for (Object[] objects : resultList) {
            AccountUser accountUser = new AccountUser();
            accountUser.setId((Integer) objects[0]);
            accountUser.setUsername(String.valueOf(objects[1]));
            accountUser.setPassword(String.valueOf(objects[2]));
            accountUser.setRole((Integer) objects[3]);
            accountUser.setRoleName(String.valueOf(objects[4]));
            accountUser.setCreated((Date) objects[5]);
            accountUser.setStatus((Integer) objects[6]);
            if (accountUser.getStatus() == 1) {
                accountUser.setStatusName("Active");
            } else {
                accountUser.setStatusName("InActive");
            }
            userList.add(accountUser);
        }
        return userList;
    }

    @Override
    public int countListUser(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select COUNT(*) ");
        builder.append("from users user, role role Where (:name IS NULL OR lower(user.username) LIKE lower(concat('%', concat(:name, '%')))) and user.role=role.id_role ");
        Query query1 = entityManager1.createNativeQuery(builder.toString());
        query1.setParameter("name", name);
        int resultList = query1.getFirstResult();
        return resultList;
    }

    @Override
    public RoleFunction getFunctionByRole(int role) {
        RoleDao roleDao = roleService.getListFunctionByRoleId(role);
        FunctionApiDao functionApiDao = functionApiService.getListApiByFunctionId(roleDao.getIdFunction().get(0));
        RoleFunction roleFunction = new RoleFunction();
        ArrayList<String> functionNames = new ArrayList<>();
        BeanUtils.copyProperties(roleDao, roleFunction);
        StringBuilder builder = new StringBuilder();
        builder.append("Select fun.functionName ");
        builder.append("from Function fun Where fun.id IN (:idFunction)");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idFunction", roleDao.getIdFunction());
        List<String> resultList = query.getResultList();
        for (String object : resultList) {
            String functionName = object;
            functionNames.add(functionName);
        }
        roleFunction.setFunctionName(functionNames);
        roleFunction.setApiName(getListApi(functionApiDao.getIdApi()));
        return roleFunction;
    }
    private  ArrayList<String> getListApi(ArrayList<Integer> idApi){
        ArrayList<String> listApi = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("Select api.nameApi ");
        builder.append("from Api api Where api.id IN (:idApi)");
        Query query = entityManager.createQuery(builder.toString());
        query.setParameter("idApi", idApi);
        List<String> resultList = query.getResultList();
        for (String object : resultList) {
            String apiName = object;
            listApi.add(apiName);
        }
        return listApi;
    }
}
