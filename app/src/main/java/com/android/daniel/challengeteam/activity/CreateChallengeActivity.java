package com.android.daniel.challengeteam.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.daniel.challengeteam.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CreateChallengeActivity extends AppCompatActivity {

    private List<String> games = new ArrayList<>();
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_game);

        games = new ArrayList<>();
        getGamesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, games);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(2);

    }

    private void getGamesList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Games");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0 ){
                        for (ParseObject game : objects){
                            games.add(game.getString("name"));
                        }
                    }
                }
            }
        });
    }
}
