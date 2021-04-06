package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getCharts();

    List<Tran> getPage(Tran t);

    int update(Tran tran);

    int delete(String id);
}
