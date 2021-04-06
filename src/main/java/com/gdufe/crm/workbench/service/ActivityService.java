package com.gdufe.crm.workbench.service;

import com.gdufe.crm.vo.PaginationVO;
import com.gdufe.crm.workbench.domain.Activity;
import com.gdufe.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveActivity(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> editActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemark(String activityId);

    boolean deleteRemark(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByName(Map<String,String> map);

    List<Activity> getActivityListByNameTwo(String name);
}
