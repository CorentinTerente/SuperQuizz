package com.example.formation2.superquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



@SuppressWarnings({"WeakerAccess", "unused"})
public class Question implements Parcelable {

	private long questionId;
	private String title;
	private ArrayList<String> propositions;
	private int goodResponse;
	private TypeQuestion questionType;
	private String userResponse;
	
	public Question(String title) {
		this.title = title;
		this.propositions = new ArrayList<>();
	}


	protected Question(Parcel in) {
		title = in.readString();
		propositions = in.createStringArrayList();
		goodResponse = in.readInt();
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
	public int getGoodResponse() {
		return goodResponse;
	}
	public void setGoodResponse(int goodResponse) {
                    this.goodResponse = goodResponse;
			
	}

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String userResponse) {
        this.userResponse = userResponse;
    }

	public boolean verifyResponse(String response) {
		return this.getPropositions().get(goodResponse).equals(response);
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
		dest.writeInt(goodResponse);
	}
}
