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
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogIn;
    private TextView txtGoToRegistration;

    private Button btnFBLogin;
    private Profile userProfile;
    private User user;

    public static final List<String> fbPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ParseUser.logOut();
        // Check if the user is already logged in
        isUserLoggedIn();

        user = new User();

        txtUser = (EditText) findViewById(R.id.txt_email_login);
        txtPassword = (EditText) findViewById(R.id.txt_password_login);
        btnLogIn = (Button) findViewById(R.id.btn_login);
        txtGoToRegistration = (TextView) findViewById(R.id.txt_do_registration);
        btnFBLogin = (Button) findViewById(R.id.btn_FB_login);

        userProfile = Profile.getCurrentProfile();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setEmail(txtUser.getText().toString());
                user.setPassword(txtPassword.getText().toString());

                checkLogin(user.getEmail(), user.getPassword());
            }
        });

        txtGoToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterUser();
            }
        });

        btnFBLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, fbPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            goToRegister2();
                        } else {
                            Log.d("MyApp", "User logged in through Facebook!");
                            //getUserDetailsFromParse();
                            goToRegister2();
                        }

                    }
                });

            }
        });


    }

    private void getUserDetailsFromFB() {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,picture");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Log.d("Response", response.getRawResponse());

                            String email = response.getJSONObject().getString("email");
                            ParseUser parseUser = ParseUser.getCurrentUser();
                            parseUser.setUsername(email);
                            parseUser.setEmail(email);
                            parseUser.saveInBackground();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        ).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void checkLogin(String email, String password) {

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    goToRegister2();
                } else {
                    Toast.makeText(LoginActivity.this, "Error code: " + e.getCode() +
                            " " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void isUserLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null)) {
            goToRegister2();
        }
    }

    private void goToRegister2() {
        Intent intent = new Intent(LoginActivity.this, Register2Activity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegisterUser() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
