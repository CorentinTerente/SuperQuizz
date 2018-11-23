package com.example.formation2.superquizz.api;

import com.example.formation2.superquizz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {

    private final OkHttpClient client = new OkHttpClient();

    private static APIClient sInstance = new APIClient();

    public static APIClient getInstance(){
        return sInstance;
    }

    public void getQuestions(final APIResult<List<Question>> result) {

        Request request = new Request.Builder()
                .url("http://192.168.10.38:3000/questions/")
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
                        Question newQuestion = new Question(jsonQuestion.getString("title"));
                        newQuestion.setQuestionId(jsonQuestion.getInt("id"));

                        newQuestion.addProposition(jsonQuestion.getString("answer_1"));
                        newQuestion.addProposition(jsonQuestion.getString("answer_2"));
                        newQuestion.addProposition(jsonQuestion.getString("answer_3"));
                        newQuestion.addProposition(jsonQuestion.getString("answer_4"));
                        newQuestion.setGoodResponse(jsonQuestion.getInt("correct_answer")-1);

                        questions.add(newQuestion);
                    }

                    result.OnSuccess(questions);
                } catch (JSONException e){

                }

                // TODO : Lire les questions depuis la reponse et les ajouter à la liste


            }
        });

        //TODO : Faire un update
        //TODO : Faire un delete
        //TODO : Faire un Create
    }

    public interface APIResult<T> {
        void onFailure(IOException e);
        void OnSuccess(T object) throws IOException;
    }
}