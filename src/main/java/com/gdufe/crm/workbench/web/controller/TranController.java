package com.gdufe.crm.workbench.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.workbench.domain.Activity;
import com.gdufe.crm.workbench.domain.Customer;
import com.gdufe.crm.workbench.domain.Tran;
import com.gdufe.crm.workbench.domain.TranHistory;
import com.gdufe.crm.workbench.service.ClueService;
import com.gdufe.crm.workbench.service.CustomerService;
import com.gdufe.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TranController {

    @Resource
    private TranService tranService;
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;


    @RequestMapping("/workbench/transaction/pageList.do")
    @ResponseBody
    public List<Tran> pageList(Tran t){
        List<Tran> list = tranService.getPage(t);
        return list;
    }

    @RequestMapping("/workbench/transaction/add.do")
    @ResponseBody
    public ModelAndView getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ModelAndView mv = new ModelAndView();
        List<User> uList = userService.selectUsersList();
        mv.addObject("uList",uList);
        mv.setViewName("/workbench/transaction/save");
        return mv;
    }

    @RequestMapping("/workbench/transaction/getCustomerName.do")
    @ResponseBody
    public void getCustomerName(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("=====getCustomerName=====");
        String name = req.getParameter("name");
        List<String> cList = customerService.getCustomer(name);
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(om.writeValueAsString(cList));
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/transaction/save.do")
    @ResponseBody
    public ModelAndView save(Tran tran,HttpServletRequest req) throws IOException {

        ModelAndView mv = new ModelAndView();
        String customerName = req.getParameter("customerName");
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(((User)req.getSession().getAttribute("user")).getName());
        boolean flag = tranService.save(tran,customerName);
        if (flag){
            mv.setViewName("/workbench/transaction/index");
        }
        return mv;

    }

    @RequestMapping("/workbench/transaction/detail.do")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest req) {

        System.out.println("=====detail.do=====");
        ModelAndView mv = new ModelAndView();
        String id = req.getParameter("id");
        Tran t = tranService.detail(id);
        String stage = t.getStage();
        Map<String,String> map = (Map<String,String>)req.getServletContext().getAttribute("pMap");
        String possibility = map.get(stage);
        t.setPossibility(possibility);
        mv.addObject("t",t);
        mv.setViewName("/workbench/transaction/detail");
        return mv;

    }

    @RequestMapping("/workbench/transaction/getHistory.do")
    @ResponseBody
    public void getHistory(HttpServletRequest req,HttpServletResponse resp) throws IOException {

        System.out.println("=====getHistory.do=====");
        String tranId = req.getParameter("tranId");
        Map<String,String> map = (Map<String,String>)req.getServletContext().getAttribute("pMap");
        List<TranHistory> hList = tranService.getHistory(tranId);
        for (TranHistory aa:hList){
            String stage = aa.getStage();
            String possibility = map.get(stage);
            aa.setPossibility(possibility);
        }
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(om.writeValueAsString(hList));
        pw.flush();
        pw.close();
    }

    @RequestMapping("/workbench/transaction/changeStage.do")
    @ResponseBody
    public void changeStage(HttpServletRequest req,HttpServletResponse resp,Tran tran) throws IOException {

        System.out.println("=====changeStage.do=====");
        tran.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        tran.setEditTime(DateTimeUtil.getSysTime());
        boolean flag = tranService.changeStage(tran);
        Map<String,String> pMap = (Map<String,String>)req.getServletContext().getAttribute("pMap");
        Map<String,Object> map = new HashMap<>();
        String possibility = pMap.get(tran.getStage());
        tran.setPossibility(possibility);
        map.put("t",tran);
        map.put("success",true);
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(om.writeValueAsString(map));
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/chart/transaction/getCharts.do")
    @ResponseBody
    public void getCharts(HttpServletRequest req,HttpServletResponse resp,Tran tran) throws IOException {

        System.out.println("=====getCharts.do=====");
        /**
         * 业务层需要为我们返回
         * total
         * dataList
         *
         * 通过map打包以上信息进行返回
         * */
        Map<String,Object> map = tranService.getCharts();
        ObjectMapper om = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(om.writeValueAsString(map));
        pw.flush();
        pw.close();

    }

    @RequestMapping("/workbench/transaction/edit.do")
    public String edit(String id, Model model,HttpServletRequest req){
        Tran t = tranService.editTran(id);
        List<User> uList = userService.selectUsersList();
        Map<String,String> pMap = (Map<String,String>)req.getServletContext().getAttribute("pMap");
        model.addAttribute("pMap",pMap);
        model.addAttribute("users",uList);
        model.addAttribute("tran",t);
        return "/workbench/transaction/edit";
    }

    @RequestMapping("/workbench/transaction/updateTran.do")
    @ResponseBody
    public Map<String,Boolean> update(Tran tran,HttpServletRequest req){
        Map<String,Boolean> map = new HashMap<>();
        tran.setEditBy(((User)req.getSession().getAttribute("user")).getName());
        int flag = tranService.update(tran);
        if (flag!=0){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/workbench/transaction/delete_tran.do")
    @ResponseBody
    public Map<String,Boolean> delete(HttpServletRequest req){
        Map<String,Boolean> map = new HashMap<>();
        String[] ids = req.getParameterValues("id");
        int flag = 0;
        for (String id:ids){
            flag += tranService.delete(id);
        }
        if (flag == ids.length){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

}
