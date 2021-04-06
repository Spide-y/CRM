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
import com.gdufe.crm.workbench.domain.Clue;
import com.gdufe.crm.workbench.domain.Tran;
import com.gdufe.crm.workbench.service.ActivityService;
import com.gdufe.crm.workbench.service.ClueService;
import com.sun.jdi.request.StepRequest;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClueController {

    @Resource
    private ClueService service;
    @Resource
    private UserService Userservice;
    @Resource
    private ActivityService activityservice;

    @RequestMapping("/workbench/clue/pageList.do")
    @ResponseBody
    public List<Clue> getClueList(Clue c){
        List<Clue> list = service.getPage(c);
        return list;
    }

    @RequestMapping("/workbench/clue/getUserList.do")
    @ResponseBody
    public void getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====Clue getUserList=====");
        List<User> uList = Userservice.selectUsersList();
        String json = "";
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(uList);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/save.do")
    @ResponseBody
    public void saveClue(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====saveClue=====");
        String id = UUIDUtil.getUUID();
        String fullname  = req.getParameter("fullname");
        String appellation  = req.getParameter("appellation");
        String owner  = req.getParameter("owner");
        String company  = req.getParameter("company");
        String job  = req.getParameter("job");
        String email  = req.getParameter("email");
        String phone  = req.getParameter("phone");
        String website  = req.getParameter("website");
        String mphone  = req.getParameter("mphone");
        String state  = req.getParameter("state");
        String source  = req.getParameter("source");
        String createBy  = ((User)req.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description  = req.getParameter("description");
        String contactSummary  = req.getParameter("contactSummary");
        String nextContactTime  = req.getParameter("nextContactTime");
        String address = req.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        int flag = service.saveClue(c);
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

    @RequestMapping("/workbench/clue/detail.do")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====detail.do=====");
        ModelAndView mv = new ModelAndView();
        String id = req.getParameter("id");
        Clue c = service.detail(id);
        mv.addObject("id",id);
        mv.addObject("c",c);
        mv.setViewName("/workbench/clue/detail");
        return mv;

    }

    @RequestMapping("/workbench/clue/getActivityListByClueId.do")
    @ResponseBody
    public void getActivityListByClueId(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====getActivityListByClueId=====");
        String id = req.getParameter("clueId");
        List<Activity> aList = activityservice.getActivityListByClueId(id);
        String json = "";
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(aList);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/unbund.do")
    @ResponseBody
    public void unbund(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====unbund=====");
        String id = req.getParameter("id");
        boolean flag = service.unbund(id);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(flag);
        System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/getActivityListByName.do")
    @ResponseBody
    public void getActivityListByName(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====getActivityListByName=====");
        String id = req.getParameter("clueId");
        String name = req.getParameter("aname");
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        List<Activity> aList = activityservice.getActivityListByName(map);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(aList);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/bund.do")
    @ResponseBody
    public void bund(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====bund=====");
        String cid = req.getParameter("cid");
        String aids[] = req.getParameterValues("aid");
        boolean flag = service.bund(cid,aids);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(flag);
        System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/getActivityListByNameTwo.do")
    @ResponseBody
    public void getActivityListByNameTwo(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====getActivityListByNameTwo=====");
        String name = req.getParameter("name");
        List<Activity> aList = activityservice.getActivityListByNameTwo(name);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(aList);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/clue/convert.do")
    @ResponseBody
    public ModelAndView convert(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====convert=====");
        ModelAndView mv = new ModelAndView();
        String clueId = req.getParameter("clueId");
        String flag = req.getParameter("flag");
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        Tran t = null;
        //如果flag等于a则创建交易
        if ("a".equals(flag)){
            t = new Tran();
            //接收交易表参数
            String money = req.getParameter("money");
            String name = req.getParameter("name");
            String expectedDate = req.getParameter("expectedDate");
            String stage = req.getParameter("stage");
            String activityId = req.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setId(id);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
        }

        /**
         * 为业务层传递的参数
         * 1 clueId 确定要转换的线索
         * 2 交易(可能为null)
         * */
        boolean flag1 = service.convert(clueId,t,createBy);

        if (flag1){
            mv.setViewName("/workbench/clue/index");
        }
        return mv;
    }

    @RequestMapping("/workbench/clue/delete_Clue.do")
    @ResponseBody
    public Map<String,Boolean> Delete_Clue(HttpServletRequest req, Model model){
        String[] ids = req.getParameterValues("id");
        Map<String,Boolean> map = new HashMap<>();
        int flag = 0;
        for (String a:ids){
            flag += service.deleteClue(a);
        }
        if (flag== ids.length){
            map.put("success",true);
            return map;
        }else {
            map.put("success",false);
            return map;
        }
    }

    @RequestMapping("/workbench/clue/editClue.do")
    @ResponseBody
    public Map<String,Object> editClue(String clueId){

        Map<String,Object> map = service.selectById(clueId);

        return map;

    }

    @RequestMapping("/workbench/clue/updateClue.do")
    @ResponseBody
    public Map<String,Boolean> update(HttpServletRequest req,Clue c){

        Map<String,Boolean> map = new HashMap<>();
        c.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        c.setEditTime(DateTimeUtil.getSysTime());
        int flag = service.updateClue(c);
        if (flag != 0){
            map.put("success",true);
        }
        System.out.println(c);
        return map;

    }

}
