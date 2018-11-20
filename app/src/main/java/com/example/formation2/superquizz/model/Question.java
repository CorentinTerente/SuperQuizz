package com.example.formation2.superquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



public class Question implements Parcelable {
	
	private String intitule;
	private ArrayList<String> propositions;
	private String bonneReponse;
	private TypeQuestion typeDeQuestion;
	
	public Question(String intitule) {
		this.intitule = intitule;
		this.propositions = new ArrayList<>();
	}


	protected Question(Parcel in) {
		intitule = in.readString();
		propositions = in.createStringArrayList();
		bonneReponse = in.readString();
	}

	public static final Creator<Question> CREATOR = new Creator<Question>() {
		@Override
		public Question createFromParcel(Parcel in) {
			return new Question(in);
		}

		@Override
		public Question[] newArray(int size) {
			return new Question[size];
		}
	};

	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public ArrayList<String> getPropositions() {
		return propositions;
	}
	public void setPropositions(ArrayList<String> propositions) {
		this.propositions = propositions;
	}
	public String getBonneReponse() {
		return bonneReponse;
	}
	public void setBonneReponse(String bonneReponse) {
		for(String propo : propositions) {
			if(propo.equals(bonneReponse)) {
				this.bonneReponse = bonneReponse;
			}
		}
			
	}
	
	public boolean verifierReponse(String reponse) {
		return bonneReponse.equals(reponse);
	}
	
	public void addProposition(String proposition) {
		this.propositions.add(proposition);
	}



	public TypeQuestion getTypeDeQuestion() {
		return typeDeQuestion;
	}



	public void setTypeDeQuestion(TypeQuestion typeDeQuestion) {
		this.typeDeQuestion = typeDeQuestion;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(intitule);
		dest.writeStringList(propositions);
		dest.writeString(bonneReponse);
	}
}
