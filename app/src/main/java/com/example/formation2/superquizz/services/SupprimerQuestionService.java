package com.example.formation2.superquizz.services;

import com.example.formation2.superquizz.dao.questionDao.QuestionDao;
import com.example.formation2.superquizz.model.Question;


public class SupprimerQuestionService extends MenuService {

	@Override
	public void executeUC(QuestionDao dao) {
		System.out.println("Saisir le numero de la question");
		int numQuestions = 0;
		Question questionASuppr;
		if (!dao.findAll().isEmpty()) {
			if (numQuestions - 1 < dao.findAll().size()) {
				questionASuppr = dao.findAll().get(numQuestions - 1);
				dao.delete(questionASuppr);
			}


		}

	}
}
