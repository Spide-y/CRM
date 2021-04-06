package com.gdufe.crm.workbench.service;

import com.gdufe.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<String> getCustomer(String name);

    List<Customer> getPage(Customer cus);

    int addCustomer(Customer cus);

    int deleteCus(String a);

    Map<String, Object> selectById(String id);

    Customer getById(String id);

    int update(Customer cus);

    String getName(String customerId);
}
