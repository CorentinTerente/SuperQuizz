package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.formation2.superquizz.R;

public class InfoActivity extends AppCompatActivity {

    private Button buttonDial,buttonMail;

    private  OnClickListener onClickDial = v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0066666666"));
            startActivity(intent);
        };

    private  OnClickListener onClickMail = v -> {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:emailaddress@emailaddress.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        buttonDial = findViewById(R.id.button_dial);
        buttonMail = findViewById(R.id.button_mail);

        buttonMail.setOnClickListener(onClickMail);
        buttonDial.setOnClickListener(onClickDial);
    }

}
