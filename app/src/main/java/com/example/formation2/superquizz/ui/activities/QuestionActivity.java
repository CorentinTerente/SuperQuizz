package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.database.QuestionsDatabaseHelper;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.ui.threads.TimerTask;

import java.util.List;

public class QuestionActivity extends AppCompatActivity implements TimerTask.OnDelayTaskListener {

    private TimerTask delayProgressBar = new TimerTask(this);
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView textViewTitle = findViewById(R.id.text_view_question_title);
        QuestionsDatabaseHelper dbHelper = QuestionsDatabaseHelper.getInstance(this);
        Question aQuestion = dbHelper.getQuestion(getIntent().getIntExtra("question",0));
        progressBar = findViewById(R.id.progress_bar);

        LinearLayout questionButtonLayout1 = findViewById(R.id.linear_layout_button_1row);
        LinearLayout questionButtonLayout2 = findViewById(R.id.linear_layout_button_2row);


        textViewTitle.setText(aQuestion.getTitle());


        View.OnClickListener questionButtonListener =  v -> {
            String response = ((Button)v).getText().toString();
            aQuestion.setUserResponse(response);
            aQuestion.setHaveRespond(1);

            SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
            boolean saveAnswer = mSettings.getBoolean("saveAnswer",true);
            if (saveAnswer) {
                dbHelper.updateQuestionAnswered(aQuestion);
            }
            if(aQuestion.verifyResponse(response)) {
                Intent intentSuccess= new Intent(QuestionActivity.this, AnswerActivity.class);
                intentSuccess.putExtra("isCorrect",true);
                startActivity(intentSuccess);

            } else {
                Intent intentFail = new Intent(QuestionActivity.this, AnswerActivity.class);
                intentFail.putExtra("isCorrect",false);
                startActivity(intentFail);

            }
        };

        List<String> propositionsList = aQuestion.getPropositions();

        // for each proposition generate a button with different color
        for(String proposition : propositionsList){

            Button buttonQuestion = new Button(this);
            buttonQuestion.setText(proposition);
            buttonQuestion.setOnClickListener(questionButtonListener);
            buttonQuestion.setWidth(250);
            buttonQuestion.setHeight(250);

            switch(propositionsList.indexOf(proposition)){
                case 0 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.primaryLightColor,null));
                    buttonQuestion.setTextColor(Color.BLACK);
                    questionButtonLayout1.addView(buttonQuestion);
                break;
                case 1 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.secondaryDarkColor,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout1.addView(buttonQuestion);
                    break;
                case 2 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.secondaryDarkColor,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout2.addView(buttonQuestion);
                    break;
                case 3 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout2.addView(buttonQuestion);
            }
        }
    delayProgressBar.execute();

    }

    @Override
    public void onProgressTask(int progress) {
        progressBar.setProgress(progress * 6);
    }

    @Override
    public void onFinishTask() {


        Intent intentFail = new Intent(QuestionActivity.this, AnswerActivity.class);
        intentFail.putExtra("isCorrect",false);
        startActivity(intentFail);
    }

    @Override
    public void onStartingTask() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
}
