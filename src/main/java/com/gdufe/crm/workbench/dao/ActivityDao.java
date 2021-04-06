package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.vo.PaginationVO;
import com.gdufe.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int saveActivity(Activity activity);
    int GetTotalByCondition(Map<String, Object> map);
    List<Activity> GetActivityListByCondition(Map<String, Object> map);
    int delete(String[] ids);

    Activity getActivityById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String id);

    List<Activity> getActivityListByName(Map<String,String> map);

    List<Activity> getActivityListByNameTwo(String name);

    String getId(String name);
}
