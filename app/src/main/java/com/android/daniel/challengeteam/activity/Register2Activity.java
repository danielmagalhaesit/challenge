package com.android.daniel.challengeteam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.daniel.challengeteam.R;
import com.android.daniel.challengeteam.model.Player;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;

public class Register2Activity extends AppCompatActivity {

    private EditText editName;
    private EditText editextPsn;
    private EditText editextXbox;
    private Button button;

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //ParseUser.logOut();
        try {
            userAlreadyHaveEnteredPsnXbox();
        }catch (Exception e){
            e.printStackTrace();
        }


        editName = (EditText) findViewById(R.id.txt_name_reg);
        editextPsn = (EditText) findViewById(R.id.txt_psn_username);
        editextXbox = (EditText) findViewById(R.id.txt_xbox_username);
        button = (Button) findViewById(R.id.btn_reg_psn_xbox);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.length() > 0){
                    if(editextPsn.length() > 0 || editextXbox.length() > 0){
                        savePsnXbox();
                    }else{
                        Toast.makeText(Register2Activity.this, "At least one username is required ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Register2Activity.this, "Name is required ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    if(ParseFacebookUtils.isLinked(ParseUser.getCurrentUser())){
        getUserDetailsFromFB();
    }

    }


    private void savePsnXbox() {
        try {
            player = new Player();
            player.setIdUser(ParseUser.getCurrentUser().getObjectId());
            player.setName(editName.getText().toString());
            player.setPsnUsername(editextPsn.getText().toString());
            player.setXboxUsername(editextXbox.getText().toString());

            TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            player.setCountry(mTelephonyManager.getNetworkCountryIso());

            ParseObject playerParseObject = new ParseObject("Player");
            playerParseObject.put("name", player.getName());
            playerParseObject.put("psnUsername", player.getPsnUsername());
            playerParseObject.put("xboxUsername", player.getXboxUsername());
            playerParseObject.put("idUser", player.getIdUser());
            playerParseObject.put("country", player.getCountry());
            player.setIdPlayer(playerParseObject.getObjectId());
            playerParseObject.saveInBackground();

            ParseQuery query = ParseUser.getQuery();
            query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        object.put("name", player.getName());
                        object.saveInBackground();
                    }
                }
            });

            Toast.makeText(Register2Activity.this, "Success", Toast.LENGTH_SHORT).show();
            goToMainActivity();

        } catch (Exception e) {
            Toast.makeText(Register2Activity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(Register2Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    private void userAlreadyHaveEnteredPsnXbox() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("idUser", ParseUser.getCurrentUser().getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object != null) {
                        if (object.getString("psnUsername") != null ||
                                object.getString("xBoxUsername") != null) {
                                goToMainActivity();
                        }
                    }
                } else {
                }
            }
        });
    }

    private void getUserDetailsFromFB() {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Log.d("Response", response.getRawResponse());
                            editName.setText(response.getJSONObject().getString("name"));
                            editName.setEnabled(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        ).executeAsync();
    }
}
