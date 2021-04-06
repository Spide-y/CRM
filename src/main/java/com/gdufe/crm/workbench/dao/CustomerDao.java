package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getByCompany(String companyName);

    int save(Customer customer);

    List<String> getCustomer(String name);

    List<Customer> getPage(Customer cus);

    int delete(String a);

    Customer getById(String id);

    int update(Customer cus);

    String getName(String customerId);

    String getId(String name);
}
