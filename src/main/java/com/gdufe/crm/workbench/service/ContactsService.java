package com.gdufe.crm.workbench.service;

import com.gdufe.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    List<Contacts> getPage(Contacts con);

    int addCon(Contacts con);

    Map<String, Object> editCon(String id);

    int update(Contacts con);

    int deleteCon(String a);
}
