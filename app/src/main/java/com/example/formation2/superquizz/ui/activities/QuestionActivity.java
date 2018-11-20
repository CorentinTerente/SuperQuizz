package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.model.Question;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private LinearLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView questionTextView = new TextView(this);

        Question laQuestion = getIntent().getParcelableExtra("question");

        layout = findViewById(R.id.linear_layout_question);

        questionTextView.setText(laQuestion.getIntitule());
        questionTextView.setTextSize(25);
        layout.addView(questionTextView);
        

        View.OnClickListener questionButtonListener =  v -> {
            String reponse = ((Button)v).getText().toString();

            if(reponse.equals(laQuestion.getBonneReponse())) {
                Intent intentCorrect = new Intent(QuestionActivity.this, AnswerActivity.class);
                intentCorrect.putExtra("isCorrect",true);
                startActivity(intentCorrect);

            } else {
                Intent intentFaux = new Intent(QuestionActivity.this, AnswerActivity.class);
                intentFaux.putExtra("isCorrect",false);
                startActivity(intentFaux);

            }
        };

        List<String> listeProposition = laQuestion.getPropositions();

        for(String proposition : listeProposition){
            Button buttonQuestion = new Button(this);
            buttonQuestion.setText(proposition);
            buttonQuestion.setOnClickListener(questionButtonListener);
            layout.addView(buttonQuestion);
        }


    }
}
