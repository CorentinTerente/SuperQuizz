package com.example.formation2.superquizz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;




public class Question implements Parcelable {

	private int questionId;
	private String title;
	private ArrayList<String> propositions = new ArrayList<>();
	private int goodResponse;
	private String userResponse;
    private String imageUrl;
    private int haveRespond = 0;
	
	public Question(String title) {
		this.title = title;
		this.propositions = new ArrayList<>();
	}

	public Question(){}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getPropositions() {
		return propositions;
	}
	public int getGoodResponse() {
		return goodResponse;
	}
	public void setGoodResponse(int goodResponse) {
                    this.goodResponse = goodResponse;
			
	}

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getHaveRespond() {
        return haveRespond;
    }

    public void setHaveRespond(int haveRespond) {
        this.haveRespond = haveRespond;
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

}
