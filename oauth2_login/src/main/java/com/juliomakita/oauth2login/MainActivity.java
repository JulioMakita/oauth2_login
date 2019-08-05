package com.juliomakita.oauth2login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.juliomakita.oauth2login.configuration.CommonMethod;
import com.juliomakita.oauth2login.configuration.RetrofitClient;
import com.juliomakita.oauth2login.interfaces.SecurityAPI;
import com.juliomakita.oauth2login.model.SecurityUser;
import com.juliomakita.oauth2login.model.TokenResponse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout mainLayout = (LinearLayout)findViewById(R.id.linear_layout);

                //Hidden Keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

                EditText username = findViewById(R.id.login_username);
                EditText password = findViewById(R.id.login_password);
                final TextView textView = findViewById(R.id.free_text);

                Snackbar.make(view, "Username: " + username.getText() + " Password: " + password.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                SecurityUser securityUser = new SecurityUser();
                securityUser.setUsername(username.getText().toString());
                securityUser.setPassword(password.getText().toString());

                if (CommonMethod.isNetworkAvailable(MainActivity.this)){

                    Retrofit retrofit = RetrofitClient.getRetrofitClient();

                    SecurityAPI securityAPI = retrofit.create(SecurityAPI.class);

                    Call<TokenResponse> call = securityAPI.getToken(securityUser);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {

                            if (response.body() != null) {
                                TokenResponse tokenResponse = (TokenResponse) response.body();
                                Intent intent = new Intent(MainActivity.this, UserLoggedActivity.class);
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra("ACCESS_TOKEN", "Access Token: \n" + tokenResponse.getAccessToken());
                                intent.setType("text/plain");
                                startActivity(intent);

                                //Toast.makeText(MainActivity.this, "Token: " + tokenResponse.getAccessToken(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Network is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }else if (id == R.id.action_register){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
