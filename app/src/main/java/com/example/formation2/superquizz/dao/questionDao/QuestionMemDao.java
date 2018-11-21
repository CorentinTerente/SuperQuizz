package com.example.formation2.superquizz.dao.questionDao;

import java.util.ArrayList;
import java.util.List;

import com.example.formation2.superquizz.model.Question;

public class QuestionMemDao implements QuestionDao{
	private final List<Question> listeQuestion = new ArrayList<>();

	public QuestionMemDao(){
		Question question1 = new Question("Quelle est la capitale de la Fance ?");
		question1.addProposition("Paris");
		question1.addProposition("Orleans");
		question1.addProposition("Nantes");
		question1.setBonneReponse("Paris");

		Question question2 = new Question("Quelle est la capitale de l'Allemagne ?");
		question2.addProposition("Berlin");
		question2.addProposition("Munich");
		question2.addProposition("Strasbourg");
		question2.setBonneReponse("Berlin");

		this.save(question1);
		this.save(question2);

	}
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
