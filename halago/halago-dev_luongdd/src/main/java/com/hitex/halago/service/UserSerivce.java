package com.hitex.halago.service;

import com.hitex.halago.model.dao.AccountUser;
import com.hitex.halago.model.dao.RoleFunction.RoleFunction;
import com.hitex.halago.model.dao.UserDao;
import com.hitex.halago.model.User;

import java.util.List;

public interface UserSerivce {
    UserDao findUser(String username);

    UserDao findUserPotal(String username);

    com.hitex.halago.model.dao.User findAccount(String username);

    User registerUser(User user);

    List<AccountUser> getListUser(String name, Integer pageSize, Integer pageNumber);

    int countListUser(String name);

    RoleFunction getFunctionByRole(int role);
}
