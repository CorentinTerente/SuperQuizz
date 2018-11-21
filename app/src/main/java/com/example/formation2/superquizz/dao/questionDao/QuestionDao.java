package com.example.formation2.superquizz.dao.questionDao;
import java.util.List;

import com.example.formation2.superquizz.model.Question;

@SuppressWarnings("ALL")
public interface QuestionDao {
	List<Question> findAll();
	void save(Question question);
	void delete(Question question);
}
