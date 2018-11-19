package com.example.formation2.superquizz.services;

import com.example.formation2.superquizz.dao.questionDao.QuestionDao;
import com.example.formation2.superquizz.model.Question;


public class ListerQuestionsService extends MenuService{

	@Override
	public void executeUC( QuestionDao dao) {
		for (Question question : dao.findAll()) {
			System.out.println((dao.findAll().indexOf(question)+1)+")  "+question.getIntitule());
			for(String proposition : question.getPropositions()) {
				System.out.println("	-"+proposition);
			}
		}
		
	}

}
