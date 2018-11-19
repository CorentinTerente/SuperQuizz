package com.example.formation2.superquizz.dao.questionDao;

import java.util.ArrayList;
import java.util.List;

import com.example.formation2.superquizz.model.Question;

public class QuestionMemDao implements QuestionDao{
	List<Question> listeQuestion = new ArrayList<>();
	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return listeQuestion;
	}

	@Override
	public void save(Question question) {
		this.listeQuestion.add(question);
		
	}

	@Override
	public void delete(Question question) {
		this.listeQuestion.remove(question);
		
	}
	
}
