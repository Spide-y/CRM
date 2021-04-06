package com.gdufe.crm.workbench.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.vo.PaginationVO;
import com.gdufe.crm.workbench.dao.ActivityDao;
import com.gdufe.crm.workbench.dao.ActivityRemarkDao;
import com.gdufe.crm.workbench.domain.Activity;
import com.gdufe.crm.workbench.domain.ActivityRemark;
import com.gdufe.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private UserDao udao;
    @Resource
    private ActivityDao dao;
    @Resource
    private ActivityRemarkDao rdao;


    @Override
    public int saveActivity(Activity activity) {
        return dao.saveActivity(activity);
    }
    public PaginationVO<Activity> pageList(Map<String, Object> map){
        PaginationVO vo = new PaginationVO();
        List<Activity>  activityList = dao.GetActivityListByCondition(map);
        int total = dao.GetTotalByCondition(map);
        vo.setTotal(total);
        vo.setDatalist(activityList);
        return vo;
    }
    public boolean delete(String[] ids){

        for (int i=0;i<ids.length;i++){
            System.out.println(ids[i]);
        }
        boolean flag = true;
        //查询出需要删除的备注的数量
        int count1 = rdao.getCountByAids(ids);
        //删除备注，返回受到影响的条数(实际删除的数量)
        int count2 = rdao.deleteRemarkByAids(ids);
        if (count1!=count2){
            flag = false;
        }
        //删除市场活动
        int count3 = dao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> editActivity(String id) {
        //取uList和a打包到Map中
        Activity a = dao.getActivityById(id);
        List<User> uList = udao.selectUsersList();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = true;
        int count = dao.update(a);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        return dao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemark(String activityId) {
        return rdao.getRemark(activityId);
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        if (rdao.deleteRemark(id)!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public int saveRemark(ActivityRemark ar) {
        return rdao.saveRemark(ar);
    }

    @Override
    public int updateRemark(ActivityRemark ar) {
        return rdao.updateRemark(ar);
    }

    @Override
    public List<Activity> getActivityListByClueId(String id) {
        return dao.getActivityListByClueId(id);
    }

    @Override
    public List<Activity> getActivityListByName(Map<String,String> map) {
        return dao.getActivityListByName(map);
    }

    @Override
    public List<Activity> getActivityListByNameTwo(String name) {
        return dao.getActivityListByNameTwo(name);
    }


}
