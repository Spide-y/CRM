package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> getPage(Contacts con);


    Contacts getById(String id);

    int update(Contacts con);

    int delete(String a);

    String getId(String name);
}
