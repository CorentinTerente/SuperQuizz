package com.example.formation2.superquizz.services;

import com.example.formation2.superquizz.dao.questionDao.QuestionDao;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.model.TypeQuestion;


public class ExecuterQuizzService extends MenuService{

	@Override
	public void executeUC(QuestionDao dao) {
		int nbPoints = 0;
		for(Question laQuestion : dao.findAll()) {
			System.out.println((dao.findAll().indexOf(laQuestion)+1)+")  "+laQuestion.getIntitule());
			for(String proposition : laQuestion.getPropositions()) {
				System.out.println("	-"+proposition);
			}
			System.out.println("votre reponse ?");
			//a changer
			String reponse = "";
			if(laQuestion.verifierReponse(reponse)) {
				if(laQuestion.getTypeDeQuestion().equals(TypeQuestion.SIMPLE)) {
					nbPoints += 1;
				} else {
					nbPoints +=2;
				}
			}
			
		}
		System.out.println("Vos Points : "+nbPoints);
		
	}

}
