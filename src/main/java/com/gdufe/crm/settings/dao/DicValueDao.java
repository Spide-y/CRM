package com.gdufe.crm.settings.dao;

import com.gdufe.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
