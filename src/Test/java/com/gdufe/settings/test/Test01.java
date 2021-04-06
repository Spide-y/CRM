package com.gdufe.settings.test;

import com.gdufe.crm.workbench.domain.*;
import com.gdufe.crm.workbench.service.ClueService;
import com.gdufe.crm.workbench.service.ContactsService;
import com.gdufe.crm.workbench.service.CustomerService;
import com.gdufe.crm.workbench.service.TranService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class Test01 {


    @Test
    public void Test_01(){

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CustomerService service = (CustomerService) ac.getBean(CustomerService.class);
        String name = service.getName("1d640947ef01489ca8ace0e04e09e451");
        System.out.println(name);


    }

}
