package com.example.formation2.superquizz.api;

import android.util.JsonReader;
import android.util.Log;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    private final OkHttpClient client = new OkHttpClient();
    private final String KEY_QUESTION_TITLE = "title";
    private final String KEY_QUESTION_ANSWER1 = "answer_1";
    private final String KEY_QUESTION_ANSWER2 = "answer_2";
    private final String KEY_QUESTION_ANSWER3 = "answer_3";
    private final String KEY_QUESTION_ANSWER4 = "answer_4";
    private final String KEY_QUESTION_CORRECT_ANSWER = "correct_answer";
    private final String KEY_QUESTION_IMG_URL = "author_img_url";
    private final String KEY_QUESTION_AUTHOR = "author";
    private final MediaType JSON_TYPE = MediaType.parse("application/json");

    private String baseUrl = "http://192.168.10.38:3000";
    private String localUrl = "http://192.168.10.213:3000";

    private static APIClient sInstance = new APIClient();

    public static APIClient getInstance(){
        return sInstance;
    }

    public void getQuestions(final APIResult<List<Question>> result) {

        Request request = new Request.Builder()
                .url(localUrl+"/questions/")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    List<Question> questions = new ArrayList<>();
                    String responseData = response.body().string();

                    JSONArray jsonArray = new JSONArray(responseData);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonQuestion = jsonArray.getJSONObject(i);
                        Question newQuestion = new Question(jsonQuestion.getString(KEY_QUESTION_TITLE));
                        newQuestion.setQuestionId(jsonQuestion.getInt("id"));

                        newQuestion.addProposition(jsonQuestion.getString(KEY_QUESTION_ANSWER1));
                        newQuestion.addProposition(jsonQuestion.getString(KEY_QUESTION_ANSWER2));
                        newQuestion.addProposition(jsonQuestion.getString(KEY_QUESTION_ANSWER3));
                        newQuestion.addProposition(jsonQuestion.getString(KEY_QUESTION_ANSWER4));
                        newQuestion.setGoodResponse(jsonQuestion.getInt(KEY_QUESTION_CORRECT_ANSWER)-1);

                        questions.add(newQuestion);
                    }

                    result.OnSuccess(questions);
                } catch (JSONException e){

                }

            }
        });

        //TODO : Faire un update
    }

    public void deleteQuestion(final APIResult<Question> result, Question q){
        JSONObject json = new JSONObject();
        try {
            json = parseQuestionToJSON(q);
        } catch (JSONException e){
            Log.e("ERROR","Can't parse question to JSON");
        }

        Request request = new Request.Builder()
                .url(localUrl+"/questions").method("DELETE", RequestBody.create(JSON_TYPE,json.toString()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result.OnSuccess(q);
            }
        });
    }


    public void createQuestion(final APIResult<Question> result,Question q){
        Question newQuestion = new Question();
        JSONObject json = new JSONObject();
        try {
             json = parseQuestionToJSON(q);
        } catch (JSONException e){
            Log.e("ERROR","Can't parse question to JSON");
        }


        Request request = new Request.Builder()
                .url(localUrl+"/questions").method("POST", RequestBody.create(JSON_TYPE,json.toString()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject newJson = new JSONObject(responseData);
                    newQuestion.setTitle(newJson.getString(KEY_QUESTION_TITLE));
                    newQuestion.setGoodResponse(newJson.getInt(KEY_QUESTION_CORRECT_ANSWER));
                    newQuestion.addProposition(newJson.getString(KEY_QUESTION_ANSWER1));
                    newQuestion.addProposition(newJson.getString(KEY_QUESTION_ANSWER2));
                    newQuestion.addProposition(newJson.getString(KEY_QUESTION_ANSWER3));
                    newQuestion.addProposition(newJson.getString(KEY_QUESTION_ANSWER4));
                    newQuestion.setQuestionId(newJson.getInt("id"));

                } catch (JSONException e){

                }
                result.OnSuccess(newQuestion);
            }
        });
    }

    private JSONObject parseQuestionToJSON(Question q) throws JSONException{
        JSONObject json = new JSONObject();
        json.put(KEY_QUESTION_TITLE,q.getTitle());
        json.put(KEY_QUESTION_ANSWER1,q.getPropositions().get(0));
        json.put(KEY_QUESTION_ANSWER2,q.getPropositions().get(1));
        json.put(KEY_QUESTION_ANSWER3,q.getPropositions().get(2));
        json.put(KEY_QUESTION_ANSWER4,q.getPropositions().get(3));
        json.put(KEY_QUESTION_CORRECT_ANSWER,q.getGoodResponse());
        json.put(KEY_QUESTION_AUTHOR,"Corentin");
        json.put(KEY_QUESTION_IMG_URL,"https://3c9sm1yzqy518hwx3f6o4c64-wpengine.netdna-ssl.com/wp-content/uploads/2015/06/hops2-1030x773.jpg");

        return json;
    }

    public interface APIResult<T> {
        void onFailure(IOException e);
        void OnSuccess(T object) throws IOException;
    }
}