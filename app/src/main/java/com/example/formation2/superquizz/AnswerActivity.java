package com.example.formation2.superquizz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(this.getIntent().getBooleanExtra("isCorrect",true)){
            setContentView(R.layout.activity_answer);
        } else {
            setContentView(R.layout.activity_answer_fail);
        }
    }

}
