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
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView textViewTitle = findViewById(R.id.text_view_question_title);
        QuestionsDatabaseHelper dbHelper = QuestionsDatabaseHelper.getInstance(this);
        Question aQuestion = dbHelper.getQuestion(getIntent().getIntExtra("question",0));
        progressBar = findViewById(R.id.progress_bar);


        buttonAnswer1 = findViewById(R.id.button_answer1);
        buttonAnswer2 = findViewById(R.id.button_answer2);
        buttonAnswer3 = findViewById(R.id.button_answer3);
        buttonAnswer4 = findViewById(R.id.button_answer4);


        textViewTitle.setText(aQuestion.getTitle());


        View.OnClickListener questionButtonListener =  v -> {
            String response = ((Button)v).getText().toString();
            aQuestion.setUserResponse(response);
            aQuestion.setHaveRespond(1);
            delayProgressBar.cancel(true);
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

        for(String proposition : propositionsList){

            switch(propositionsList.indexOf(proposition)){
                case 0 :
                    buttonAnswer1.setText(proposition);
                    buttonAnswer1.setOnClickListener(questionButtonListener);
                break;
                case 1 :
                    buttonAnswer2.setText(proposition);
                    buttonAnswer2.setOnClickListener(questionButtonListener);

                    break;
                case 2 :
                    buttonAnswer3.setText(proposition);
                    buttonAnswer3.setOnClickListener(questionButtonListener);

                    break;
                case 3 :
                    buttonAnswer4.setText(proposition);
                    buttonAnswer4.setOnClickListener(questionButtonListener);
            }
        }
    delayProgressBar.execute();

    }

    @Override
    public void onProgressTask(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void onFinishTask() {
        if(!delayProgressBar.isCancelled()) {
            Intent intentFail = new Intent(QuestionActivity.this, AnswerActivity.class);
            intentFail.putExtra("isCorrect", false);
            startActivity(intentFail);
        }
    }

    @Override
    public void onStartingTask() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
}
