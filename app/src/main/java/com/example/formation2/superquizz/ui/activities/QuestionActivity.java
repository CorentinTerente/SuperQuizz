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

import java.util.List;

public class QuestionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView textViewTitle = findViewById(R.id.text_view_question_title);
        Question aQuestion = getIntent().getParcelableExtra("question");

        LinearLayout questionButtonLayout1 = findViewById(R.id.linear_layout_button_1row);
        LinearLayout questionButtonLayout2 = findViewById(R.id.linear_layout_button_2row);


        textViewTitle.setText(aQuestion.getTitle());


        View.OnClickListener questionButtonListener =  v -> {
            String response = ((Button)v).getText().toString();

            if(response.equals(aQuestion.getGoodResponse())) {
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
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.red,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout1.addView(buttonQuestion);
                break;
                case 1 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.blue,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout1.addView(buttonQuestion);
                    break;
                case 2 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.green,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout2.addView(buttonQuestion);
                    break;
                case 3 :
                    buttonQuestion.setBackgroundColor(getResources().getColor(R.color.purple,null));
                    buttonQuestion.setTextColor(getResources().getColor(R.color.white,null));
                    questionButtonLayout2.addView(buttonQuestion);
            }
        }


    }
}
