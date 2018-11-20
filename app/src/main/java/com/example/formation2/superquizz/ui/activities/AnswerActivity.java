package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.formation2.superquizz.R;


public class AnswerActivity extends AppCompatActivity {
    private ImageButton imageButton;

    private View.OnClickListener onClickImageButton = v -> {
            Intent intent = new Intent(AnswerActivity.this,MainActivity.class);
            startActivity(intent);
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(this.getIntent().getBooleanExtra("isCorrect",true)){
            setContentView(R.layout.activity_answer);

        } else {
            setContentView(R.layout.activity_answer_fail);
        }

        imageButton = findViewById(R.id.imagebutton_return);
        imageButton.setOnClickListener(onClickImageButton);
    }

}
