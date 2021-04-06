package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteRemarkByAids(String[] ids);

    List<ActivityRemark> getRemark(String id);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
