package com.gdufe.crm.workbench.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.workbench.dao.CustomerDao;
import com.gdufe.crm.workbench.domain.Customer;
import com.gdufe.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerDao customerDao;
    @Resource
    private UserDao userDao;

    @Override
    public List<String> getCustomer(String name) {

        return customerDao.getCustomer(name);
    }

    @Override
    public List<Customer> getPage(Customer cus) {
        return customerDao.getPage(cus);
    }

    @Override
    public int addCustomer(Customer cus) {
        cus.setCreateTime(DateTimeUtil.getSysTime());
        cus.setId(UUIDUtil.getUUID());
        return customerDao.save(cus);
    }

    @Override
    public int deleteCus(String a) {
        return customerDao.delete(a);
    }

    @Override
    public Map<String, Object> selectById(String id) {
        Map<String, Object> map = new HashMap<>();
        List<User> uList = userDao.selectUsersList();
        Customer cus = customerDao.getById(id);
        map.put("users",uList);
        map.put("cus",cus);
        return map;
    }

    @Override
    public int update(Customer cus) {
        return customerDao.update(cus);
    }

    @Override
    public Customer getById(String id) {
        return customerDao.getById(id);
    }

    @Override
    public String getName(String customerId) {
        return customerDao.getName(customerId);
    }
}
