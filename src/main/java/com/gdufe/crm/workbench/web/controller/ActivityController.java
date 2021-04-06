package com.gdufe.crm.workbench.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.ObjectMapperUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.vo.PaginationVO;
import com.gdufe.crm.workbench.domain.Activity;
import com.gdufe.crm.workbench.domain.ActivityRemark;
import com.gdufe.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Resource
    private ActivityService service;
    @Resource
    private UserService Userservice;

    @RequestMapping("/workbench/activity/getUserList.do")
    @ResponseBody
    public void getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("=====getUserList.do=====");
        List<User> list = Userservice.selectUsersList();
        String json = "";
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(list);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }

    @RequestMapping("/workbench/activity/save.do")
    @ResponseBody
    public void saveActivity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("=====+++save.do+++=====");
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        int flag = service.saveActivity(a);
        Map<String ,String> msg = new HashMap<String, String>();
        String json = "";
        msg.put("success","false");
        if (flag!=0){
            System.out.println("插入成功");
            msg.put("success","true");
        }
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(msg);
        System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/activity/pageList.do")
    @ResponseBody
    public void pageList(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====pageList.do=====");

        String name = req.getParameter("name");
        String owner = req.getParameter("owner");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String pageNoStr = req.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = req.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        String json = "";
        PaginationVO<Activity> vo = service.pageList(map);

        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(vo);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }

    @RequestMapping("/workbench/activity/deleteActivity.do")
    @ResponseBody
    public void deleteActivity(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====deleteActivity.do=====");
        String json = "";
        Map<String ,String> msg = new HashMap<String, String>();
        msg.put("success","true");

        String ids[] = req.getParameterValues("id");
        boolean flag = service.delete(ids);
        if (flag){
            ObjectMapper om = new ObjectMapper();
            json = om.writeValueAsString(msg);
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter pw = resp.getWriter();
            pw.println(json);
            pw.flush();
            pw.close();
        }
    }

    @RequestMapping("/workbench/activity/edit.do")
    @ResponseBody
    public void editActivity(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====edit.do=====");
        String id = req.getParameter("id");
        Map<String,Object> map = service.editActivity(id);
        ObjectMapperUtil.getOM(resp,map);
    }

    @RequestMapping("/workbench/activity/update.do")
    @ResponseBody
    public void updateActivity(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        String id = req.getParameter("id");
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)req.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);

        boolean flag = service.update(a);

        ObjectMapperUtil.getOM(resp,flag);

    }

    @RequestMapping("/workbench/activity/detail.do")
    @ResponseBody
    public ModelAndView Detail(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====detail.do=====");
        ModelAndView mv = new ModelAndView();
        String id = req.getParameter("id");
        Activity a = service.detail(id);
        mv.addObject("id",id);
        mv.addObject("a",a);
        mv.setViewName("/workbench/activity/detail");
        return mv;
    }

    @RequestMapping("/workbench/activity/getRemarkList.do")
    @ResponseBody
    public void GetRemark(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====getRemarkList.do=====");
        String activityId = req.getParameter("activityId");
        List<ActivityRemark> remark = service.getRemark(activityId);
        ObjectMapperUtil.getOM(resp,remark);
    }

    @RequestMapping("/workbench/activity/deleteRemark.do")
    @ResponseBody
    public void deleteRemark(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====deleteRemark.do=====");
        String id = req.getParameter("id");
        boolean flag = service.deleteRemark(id);
        ObjectMapperUtil.getOM(resp,flag);
    }

    @RequestMapping("/workbench/activity/saveRemark.do")
    @ResponseBody
    public void saveRemark(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====saveRemark.do=====");
        String id = UUIDUtil.getUUID();
        String noteContent = req.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = req.getParameter("createBy");
        String editFlag = "0";
        String activityId = req.getParameter("activityId");

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        ar.setActivityId(activityId);

        int count = service.saveRemark(ar);
        Map<String,Object> map = new HashMap<String,Object>();
        if (count==1){
            map.put("success",true);
            map.put("ar",ar);
            ObjectMapperUtil.getOM(resp,map);
        }
    }

    @RequestMapping("/workbench/activity/updateRemark.do")
    @ResponseBody
    public void updateRemark(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("=====updateRemark.do=====");
        String id = req.getParameter("id");
        String noteContent = req.getParameter("noteContent");
        String editFlag = "1";
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)req.getSession().getAttribute("user")).getName();

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editFlag);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);

        int flag = service.updateRemark(ar);

        Map<String,Object> map = new HashMap<String, Object>();
        if (flag==1){
            map.put("success",true);
            map.put("ar",ar);
            ObjectMapperUtil.getOM(resp,map);
        }
    }


}
