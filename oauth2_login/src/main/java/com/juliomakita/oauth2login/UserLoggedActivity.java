package com.juliomakita.oauth2login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.juliomakita.oauth2login.model.TokenResponse;

public class UserLoggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);

        TextView textView = findViewById(R.id.dynamicText);

        Intent intent = getIntent();
        TokenResponse accessToken = (TokenResponse) intent.getSerializableExtra("TOKEN_RESPONSE");

        JWT jwt = new JWT(accessToken.getAccessToken());
        Claim username = jwt.getClaims().get("sub");

        textView.setText("You are logged in! " + username.asString());
    }
}
