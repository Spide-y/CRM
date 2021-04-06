package com.gdufe.crm.workbench.web.controller;

import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.workbench.domain.Contacts;
import com.gdufe.crm.workbench.service.ContactsService;
import com.gdufe.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContactsController {

    @Resource
    ContactsService service;
    @Resource
    UserService userservice;
    @Resource
    CustomerService customerService;

    @RequestMapping("/workbench/contacts/pageList.do")
    @ResponseBody
    public List<Contacts> sayHello(Contacts con){
        List<Contacts> list = service.getPage(con);
        return list;
    }

    @RequestMapping("/workbench/contacts/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> uList = userservice.selectUsersList();
        return uList;
    }

    @RequestMapping("/workbench/contacts/save.do")
    @ResponseBody
    public Map<String,Boolean> save(Contacts con, HttpServletRequest req){
        Map<String,Boolean> map = new HashMap<>();
        con.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        con.setCreateTime(DateTimeUtil.getSysTime());
        int flag = service.addCon(con);
        if (flag!=0){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/workbench/contacts/editCon.do")
    @ResponseBody
    public Map<String,Object> editCon(String id){
        return service.editCon(id);
    }

    @RequestMapping("/workbench/contacts/updateCon.do")
    @ResponseBody
    public Map<String,Boolean> updateCon(Contacts con,HttpServletRequest req){
        Map<String,Boolean> map = new HashMap<>();
        con.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        con.setEditTime(DateTimeUtil.getSysTime());
        int flag = service.update(con);
        if (flag!=0){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/workbench/contacts/delete_con.do")
    @ResponseBody
    public Map<String,Boolean> deleteCon(HttpServletRequest req){
        String[] ids = req.getParameterValues("id");
        Map<String,Boolean> map = new HashMap<>();
        int flag = 0;
        for (String a:ids){
            flag += service.deleteCon(a);
        }
        if (flag== ids.length){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

}
