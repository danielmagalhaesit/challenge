package com.android.daniel.challengeteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.daniel.challengeteam.R;
import com.android.daniel.challengeteam.model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONException;


public class RegisterActivity extends AppCompatActivity {


    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private TextView txtDoLogIn;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = (EditText) findViewById(R.id.txt_email_reg);
        txtPassword = (EditText) findViewById(R.id.txt_password_reg);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtDoLogIn = (TextView) findViewById(R.id.txt_do_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        txtDoLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogInScreen();
            }
        });
    }
    private void registerUser() {

        // Creating user object
        user = new User();
        user.setEmail(txtEmail.getText().toString());
        user.setPassword(txtPassword.getText().toString());

        // Using the email as the username
        final ParseUser parseUser = new ParseUser();
        parseUser.setUsername(user.getEmail());
        parseUser.setEmail(user.getEmail());
        parseUser.setPassword(user.getPassword());

        // Saving data
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    goToLogInScreen();
                }else{
                    Toast.makeText(RegisterActivity.this, "Error code: " + e.getCode() +
                            " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goToLogInScreen(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
