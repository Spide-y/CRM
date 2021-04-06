package com.gdufe.crm.settings.service.impl;

import com.gdufe.crm.settings.dao.DicTypeDao;
import com.gdufe.crm.settings.dao.DicValueDao;
import com.gdufe.crm.settings.domain.DicType;
import com.gdufe.crm.settings.domain.DicValue;
import com.gdufe.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Resource
    DicTypeDao ddao;
    @Resource
    DicValueDao dvdao;


    public  Map<String, List<DicValue>> getAll(){

        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        //将字典类型列表取出
        List<DicType> dtList = ddao.getTypeList();
        //遍历字典类型列表
        for (DicType dt:dtList){
            //取得每一种类型得字典类型编码
            String code = dt.getCode();
            //根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dvdao.getListByCode(code);
            map.put(code+"List",dvList);
        }

        return map;
    }

}
