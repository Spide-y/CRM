package com.gdufe.crm.workbench.web.controller;

import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.workbench.domain.Customer;
import com.gdufe.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Resource
    CustomerService service;
    @Resource
    UserService userservice;

    @RequestMapping("/workbench/customer/getUserList.do")
    @ResponseBody
    public List<User> getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<User> uList = userservice.selectUsersList();
        return uList;

    }

    @RequestMapping("/workbench/customer/pageList.do")
    @ResponseBody
    public List<Customer> pageList(Customer cus){
        List<Customer> list = service.getPage(cus);
        return list;
    }

    @RequestMapping("/workbench/customer/save.do")
    @ResponseBody
    public Map<String,Boolean> save(Customer cus,HttpServletRequest req){
        cus.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        Map<String,Boolean> map = new HashMap<>();
        int flag = service.addCustomer(cus);
        if (flag != 0){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/workbench/customer/delete_cus.do")
    @ResponseBody
    public Map<String,Boolean> delete_cus(HttpServletRequest req){
        String[] ids = req.getParameterValues("id");
        Map<String,Boolean> map = new HashMap<>();
        int flag = 0;
        for (String a:ids){
            flag += service.deleteCus(a);
        }
        if (flag== ids.length){
            map.put("success",true);
            return map;
        }else {
            map.put("success",false);
            return map;
        }
    }

    @RequestMapping("/workbench/customer/editCus.do")
    @ResponseBody
    public Map<String,Object> editCus(String id){
        Map<String,Object> map = service.selectById(id);
        return map;
    }

    @RequestMapping("/workbench/customer/updateCus.do")
    @ResponseBody
    public Map<String,Boolean> updateCus(Customer cus,HttpServletRequest req){
        Map<String,Boolean> map = new HashMap<>();
        cus.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        cus.setEditTime(DateTimeUtil.getSysTime());
        int flag = service.update(cus);
        if (flag != 0 ){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/workbench/customer/detail.do")
    public String detail(String id, Model model){
        Customer cus = service.getById(id);
        model.addAttribute("cus",cus);
        return "/workbench/customer/detail";
    }

}
