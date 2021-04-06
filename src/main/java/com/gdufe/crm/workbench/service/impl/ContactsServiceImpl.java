package com.gdufe.crm.workbench.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.workbench.dao.*;
import com.gdufe.crm.workbench.domain.Contacts;
import com.gdufe.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Resource
    private UserDao userDao;
    @Resource
    private ClueDao dao;
    @Resource
    private ClueActivityRelationDao carDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;


    @Override
    public List<Contacts> getPage(Contacts con) {
        return contactsDao.getPage(con);
    }

    @Override
    public int addCon(Contacts con) {
        return contactsDao.save(con);
    }

    @Override
    public Map<String, Object> editCon(String id) {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.selectUsersList();
        map.put("users",userList);
        Contacts con = contactsDao.getById(id);
        String name = customerDao.getName(con.getCustomerId());
        map.put("con",con);
        map.put("customer",name);
        return map;
    }

    @Override
    public int update(Contacts con) {
        String cusId = customerDao.getId(con.getCustomerId());
        con.setCustomerId(cusId);
        return contactsDao.update(con);
    }

    @Override
    public int deleteCon(String a) {
        return contactsDao.delete(a);
    }
}
