package com.gdufe.crm.settings.service;

import com.gdufe.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    public Map<String, List<DicValue>> getAll();
}
