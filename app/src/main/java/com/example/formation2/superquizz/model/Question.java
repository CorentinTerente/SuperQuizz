package com.example.formation2.superquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



@SuppressWarnings({"WeakerAccess", "unused"})
public class Question implements Parcelable {
	
	private String title;
	private ArrayList<String> propositions;
	private String goodResponse;
	private TypeQuestion questionType;
	
	public Question(String title) {
		this.title = title;
		this.propositions = new ArrayList<>();
	}


	protected Question(Parcel in) {
		title = in.readString();
		propositions = in.createStringArrayList();
		goodResponse = in.readString();
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

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getPropositions() {
		return propositions;
	}
	public void setPropositions(ArrayList<String> propositions) {
		this.propositions = propositions;
	}
	public String getGoodResponse() {
		return goodResponse;
	}
	public void setGoodResponse(String goodResponse) {
		for(String proposition : propositions) {
			if(proposition.equals(goodResponse)) {
				this.goodResponse = goodResponse;
			}
		}
			
	}
	
	public boolean verifyResponse(String response) {
		return goodResponse.equals(response);
	}
	
	public void addProposition(String proposition) {
		this.propositions.add(proposition);
	}



	public TypeQuestion getQuestionType() {
		return questionType;
	}



	public void setQuestionType(TypeQuestion questionType) {
		this.questionType = questionType;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeStringList(propositions);
		dest.writeString(goodResponse);
	}
}
