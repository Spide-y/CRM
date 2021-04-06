package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory history);

    List<TranHistory> getList(String tranId);
}
