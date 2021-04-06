package com.gdufe.crm.settings.service;

import com.gdufe.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User selectUsers(User user);
    List<User> selectUsersList();
    void login(String loginAct, String loginPwd, String ip);
}
