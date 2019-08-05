package com.juliomakita.oauth2login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserLoggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);

        TextView textView = findViewById(R.id.dynamicText);

        Intent intent = getIntent();
        String accessToken = (String) intent.getSerializableExtra("ACCESS_TOKEN");
        textView.setText(accessToken);
    }
}
