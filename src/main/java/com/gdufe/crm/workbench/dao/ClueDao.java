package com.gdufe.crm.workbench.dao;

import com.gdufe.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {

	int saveClue(Clue c);

	Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);

    List<Clue> getPage(Clue c);

    int update(Clue c);
}
