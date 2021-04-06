package com.gdufe.crm.workbench.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.workbench.dao.*;
import com.gdufe.crm.workbench.domain.Customer;
import com.gdufe.crm.workbench.domain.Tran;
import com.gdufe.crm.workbench.domain.TranHistory;
import com.gdufe.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ContactsDao contactsDao;

    @Override
    public boolean save(Tran tran, String customerName) {

        boolean flag = true;
        Customer c = customerDao.getByCompany(customerName);
        if (c == null){
            c = new Customer();
            c.setId(UUIDUtil.getUUID());
            c.setName(customerName);
            c.setCreateBy(tran.getCreateBy());
            c.setCreateTime(tran.getCreateTime());
            c.setContactSummary(tran.getContactSummary());
            int count1 =customerDao.save(c);
            if (count1!=1){
                flag=false;
            }
        }
        tran.setCustomerId(c.getId());
        int count2 = tranDao.save(tran);
        if (count2!=1){
            flag=false;
        }

        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(tran.getId());
        th.setStage(tran.getStage());
        th.setMoney(tran.getMoney());
        th.setExpectedDate(tran.getExpectedDate());
        th.setCreateTime(tran.getCreateTime());
        th.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(th);
        if (count3!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        return tranDao.detail(id);
    }

    @Override
    public List<TranHistory> getHistory(String tranId) {
        return tranHistoryDao.getList(tranId);
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag = true;

        int count1 = tranDao.changeStage(tran);
        if (count1 != 1) {
            flag = false;
        }

        //生成交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(tran.getEditBy());
        th.setCreateTime(tran.getEditTime());
        th.setExpectedDate(tran.getExpectedDate());
        th.setMoney(tran.getMoney());
        th.setTranId(tran.getId());
        int count2 = tranHistoryDao.save(th);
        if (count2 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        Map<String, Object> map = new HashMap<>();

        //取得total
        int total = tranDao.getTotal();
        System.out.println(total);

        //取得dataList
        List<Map<String,Object>> dataList = tranDao.getCharts();

        //打包成map
        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }

    @Override
    public List<Tran> getPage(Tran t) {
        return tranDao.getPage(t);
    }

    @Override
    public Tran editTran(String id) {
        return tranDao.detail(id);
    }

    @Override
    public int update(Tran tran) {
        tran.setEditTime(DateTimeUtil.getSysTime());
        String cusId = customerDao.getId(tran.getCustomerId());
        tran.setCustomerId(cusId);
        String actId = activityDao.getId(tran.getActivityId());
        tran.setActivityId(actId);
        String conId = contactsDao.getId(tran.getContactsId());
        tran.setContactsId(conId);
        return tranDao.update(tran);
    }

    @Override
    public int delete(String id) {
        return tranDao.delete(id);
    }

}
