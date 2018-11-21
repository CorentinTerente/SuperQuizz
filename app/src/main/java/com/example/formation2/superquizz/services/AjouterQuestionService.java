package com.example.formation2.superquizz.services;

import com.example.formation2.superquizz.dao.questionDao.*;


@SuppressWarnings("ALL")
public class AjouterQuestionService extends MenuService{

	@SuppressWarnings("unused")
    @Override
	public void executeUC(QuestionDao dao) {
		System.out.println("Question SIMPLE ou BONUS ?");
		String typeQuestion;
		System.out.println("veuillez saisir l'intitule de la question");
		String intitule;
		//Question nouvelleQuestion = new Question();
		//nouvelleQuestion.setTypeDeQuestion(TypeQuestion.valueOf(typeQuestion));
		System.out.println("nombre de questions");
		int nbPropositions;
		//ajoute le nombre de proposition choisi
		//for(int i = 0; i< nbPropositions;i++) {
		//	System.out.println("Saisir la propostion n°"+(i+1));
		//	String proposition;
			//nouvelleQuestion.addProposition(proposition);
		//}
		System.out.println("La bonne reponse est  :");
		String bonneReponse;
		//nouvelleQuestion.setBonneReponse(bonneReponse);
		//dao.save(nouvelleQuestion);
		
	}
	
	
}
