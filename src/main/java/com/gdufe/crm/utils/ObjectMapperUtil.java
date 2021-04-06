package com.gdufe.crm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ObjectMapperUtil {
    public static void getOM(HttpServletResponse resp, Object obj) throws IOException {
        String json = "";
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(obj);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();

    }
}
