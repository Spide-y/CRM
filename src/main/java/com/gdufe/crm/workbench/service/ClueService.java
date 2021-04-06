package com.gdufe.crm.workbench.service;

import com.gdufe.crm.workbench.domain.Clue;
import com.gdufe.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {

    int saveClue(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);

    List<Clue> getPage(Clue c);

    int deleteClue(String a);


    Map<String,Object> selectById(String clueId);

    int updateClue(Clue c);
}
