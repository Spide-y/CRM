package com.gdufe.crm.settings.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public List<User> selectUsersList() {
        return userDao.selectUsersList();
    }

    public User selectUsers(User user){
        return userDao.selectUsers(user);
    };
    public void login(String loginAct, String loginPwd, String ip){

    }

}
