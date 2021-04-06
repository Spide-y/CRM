package com.gdufe.crm.settings.dao;

import com.gdufe.crm.settings.domain.User;

import java.util.List;

public interface UserDao {
    User selectUsers(User user);
    List<User> selectUsersList();
    void login(String loginAct, String loginPwd, String ip);
}
