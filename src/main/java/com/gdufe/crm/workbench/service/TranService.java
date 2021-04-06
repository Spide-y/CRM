package com.gdufe.crm.workbench.service;

import com.gdufe.crm.workbench.domain.Tran;
import com.gdufe.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistory(String tranId);

    boolean changeStage(Tran tran);

    Map<String, Object> getCharts();

    List<Tran> getPage(Tran t);

    Tran editTran(String id);

    int update(Tran tran);

    int delete(String id);
}
