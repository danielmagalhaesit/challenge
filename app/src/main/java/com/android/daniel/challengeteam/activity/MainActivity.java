package com.android.daniel.challengeteam.activity;

/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.daniel.challengeteam.R;
import com.android.daniel.challengeteam.adapter.ChallengesAdapter;
import com.android.daniel.challengeteam.model.Challenge;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbarTop;
    private LinearLayout llToolbarTopCoins;
    private LinearLayout llToolbarTopName;
    private TextView playerName;
    private TextView txtviewCoins;
    private TextView coins;
    private RecyclerView recyclerView;

    private ArrayList<ParseObject> challenges;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        challenges = new ArrayList();
        playerName = new TextView(this);
        txtviewCoins = new TextView(this);
        coins = new TextView(this);

        toolbarTop = (Toolbar) findViewById(R.id.top_toolbar);
        llToolbarTopCoins = (LinearLayout) findViewById(R.id.ll_toolbar_top_coins);
        llToolbarTopName = (LinearLayout) findViewById(R.id.ll_toolbar_top_name);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        // Setting the toolbars
        //ParseUser.logOut();
        playerName.setText(ParseUser.getCurrentUser().getString("name"));
        txtviewCoins.setText("Coins:  ");
        getCoins();
        llToolbarTopName.addView(playerName);
        llToolbarTopCoins.addView(txtviewCoins);
        llToolbarTopCoins.addView(coins);
        setSupportActionBar(toolbarTop);

        //Getting Challenges to recycler view
        getChallenges();
//        recyclerView.setHasFixedSize(true);

        adapter = new ChallengesAdapter(challenges, this);

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getChallenges() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
        query.whereEqualTo("isFinished", false);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0){
                        challenges.clear();
                        for (ParseObject parseObject : objects){
                            challenges.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCoins()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("idUser", ParseUser.getCurrentUser().getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null){
                    if(object.getString("coins") != null){
                       coins.setText(object.getString("coins"));
                    }
                }
            }
        });

    }

}
