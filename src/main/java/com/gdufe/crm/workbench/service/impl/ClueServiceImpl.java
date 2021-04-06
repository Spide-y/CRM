package com.gdufe.crm.workbench.service.impl;

import com.gdufe.crm.settings.dao.UserDao;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.UUIDUtil;
import com.gdufe.crm.workbench.dao.*;
import com.gdufe.crm.workbench.domain.*;
import com.gdufe.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {


    @Resource
    private UserDao userDao;
    @Resource
    private ClueDao dao;
    @Resource
    private ClueActivityRelationDao carDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;




    @Override
    public int saveClue(Clue c) {
        return dao.saveClue(c);
    }

    @Override
    public Clue detail(String id) {
        return dao.detail(id);
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = true;
        int count = carDao.unbund(id);
        if (count==0){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag = true;
        System.out.println(cid);
        for (String aid:aids){
            //取得每一个aid和cid关联
            System.out.println(aid);
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);
            int count = carDao.bund(car);
            if (count==0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {

        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;

        //(1)通过线索id获取线索对象(线索对象中封装了线索的信息)
        Clue clue = dao.getById(clueId);
        //(2)通过线索对象获取客户信息，当该客户不存在的时候，新建客户(根据公司名称精确匹配，判断该客户是否存在)
        String companyName = clue.getCompany();
        Customer customer = customerDao.getByCompany(companyName);
        if (customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(clue.getCompany());
            customer.setDescription(clue.getDescription());
            customer.setCreateBy(clue.getCreateBy());
            customer.setCreateTime(clue.getCreateTime());
            customer.setContactSummary(clue.getContactSummary());
            int count = customerDao.save(customer);
            if (count!=1){
                flag = false;
            }
        }
        //(3)通过线索对象提取联系人信息，保存联系人
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(customer.getId());
        con.setCreateBy(clue.getCreateBy());
        con.setCreateTime(clue.getCreateTime());
        con.setContactSummary(clue.getContactSummary());
        con.setAppellation(clue.getAppellation());
        con.setAddress(clue.getAddress());
        //添加联系人
        int count2 = contactsDao.save(con);
        if (count2!=1){
            flag = false;
        }
        //(4)将线索备注转换到客户备注以及联系人备注
        //查询出与线索关联的备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark cc:clueRemarkList){
            //取出备注信息就好
            String noteContent = cc.getNoteContent();
            //创建客户备注对象，添加备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1){
                flag = false;
            }
            //创建联系人备注对象，添加备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setEditFlag("0");
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1){
                flag = false;
            }

        }
        //(5)“线索和市场活动”的关系转换
        //查询出与该线索有关的市场活动
        List<ClueActivityRelation> relationList = carDao.getListByClueId(clueId);
        //遍历关联记录
        for (ClueActivityRelation aa:relationList){
            String activityId = aa.getActivityId();

            //创建联系人与市场活动的关联关系对象，转移进去
            ContactsActivityRelation contactsRelation = new ContactsActivityRelation();
            contactsRelation.setId(UUIDUtil.getUUID());
            contactsRelation.setActivityId(activityId);
            contactsRelation.setContactsId(con.getId());
            int count5 = contactsActivityRelationDao.save(contactsRelation);
            if (count5 != 1){
                flag = false;
            }
        }
        //(6)如果有创建交易需求，创建一条交易
        if (t!=null){
            //完善t
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(con.getId());
            //添加交易
            int count6 = tranDao.save(t);
            if (count6 != 1 ){
                flag = false;
            }
            //(7)如果创建了交易，则创建一条交易历史
            TranHistory history = new TranHistory();
            history.setId(UUIDUtil.getUUID());
            history.setCreateBy(createBy);
            history.setCreateTime(createTime);
            history.setExpectedDate(t.getExpectedDate());
            history.setMoney(t.getMoney());
            history.setStage(t.getStage());
            history.setTranId(t.getId());
            //添加历史
            int count7 = tranHistoryDao.save(history);
            if (count7 != 1 ){
                flag = false;
            }
        }
        //(8)删除线索备注
        for (ClueRemark ee:clueRemarkList){
            int count8 = clueRemarkDao.delete(ee);
            if (count8 != 1 ){
                flag = false;
            }
        }
        //(9)删除线索和市场活动的关系
        for (ClueActivityRelation ff:relationList){
            int count9 = carDao.delete(ff);
            if (count9 == 0 ){
                flag = false;
            }
        }
        //(10)删除线索
        int count10 = dao.delete(clueId);
        if (count10 == 0 ){
            flag = false;
        }

        return flag;
    }

    @Override
    public List<Clue> getPage(Clue c) {
        return dao.getPage(c);
    }

    @Override
    public int deleteClue(String a) {
        return dao.delete(a);
    }

    @Override
    public Map<String,Object> selectById(String clueId) {

        Map<String,Object> map = new HashMap<>();
        List<User> uList = userDao.selectUsersList();
        Clue c = dao.getById(clueId);
        map.put("users",uList);
        map.put("c",c);
        return map;
    }

    @Override
    public int updateClue(Clue c) {
        return dao.update(c);
    }
}
