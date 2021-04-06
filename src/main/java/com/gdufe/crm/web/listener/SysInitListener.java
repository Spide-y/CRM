package com.gdufe.crm.web.listener;

import com.gdufe.crm.settings.domain.DicValue;
import com.gdufe.crm.settings.service.DicService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

@Component
public class SysInitListener implements ServletContextListener {

    /*
    * 该方法用来监听上下文域对象的方法，当服务器启动，上下文域对象创建，
    * 对象创建完毕后执行该方法
    * event: 该参数能够取得监听的对象
    *        监听的是什么对象，就可以通过该参数得到什么对象
    *        例如我们现在监听的是上下文对象
    * */

    public void contextInitialized(ServletContextEvent event){
        System.out.println("上下文域对象创建了");
        ServletContext application = event.getServletContext();
        DicService service = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()).getBean(DicService.class);
        //取数据字典
        Map<String, List<DicValue>> map = service.getAll();
        Set<String> set = map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }

        //处理properties文件步骤:
        //解析properties文件
        //用于处理properties文件
        ResourceBundle rb = ResourceBundle.getBundle("stage2Posibility");
        Enumeration<String> e =  rb.getKeys();
        Map<String,String> pMap = new HashMap<String, String>();
        while (e.hasMoreElements()){

            String key = e.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);

        }
        application.setAttribute("pMap",pMap);

    }
}
