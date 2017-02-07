package com.android.daniel.challengeteam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.daniel.challengeteam.R;
import com.android.daniel.challengeteam.adapter.ChallengesAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView coins;
    private Spinner spnVideoGame;
    private Spinner spnGame;
    private Spinner spnBet;

    private RecyclerView recyclerView;
    private ArrayList<ParseObject> challenges;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ParseUser.getCurrentUser().getString("name"));
        coins = new TextView(this);
        coins.setPadding(570, 1, 1, 1);
        getCoins();
        toolbar.addView(coins);

        //FAB listener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewChallenge();
            }
        });

        // Populating spinner Games through Database
        spnVideoGame = (Spinner) findViewById(R.id.spn_videogame);
        spnGame = (Spinner) findViewById(R.id.spn_game);
        spnBet = (Spinner) findViewById(R.id.spn_coins_bet);


        // Getting challenges to RecyclerView
        challenges = new ArrayList<>();
        try {
            getChallenges(spnVideoGame.getSelectedItem().toString(), spnGame.getSelectedItem().toString(),
                    spnBet.getSelectedItem().toString());

            spnVideoGame.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
            spnGame.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
            spnBet.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
        }catch (Exception e){
            e.printStackTrace();
        }



        // Creating the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_main_2);
        adapter = new ChallengesAdapter(challenges, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_achievements) {

        } else if (id == R.id.nav_logout) {
            ParseUser.logOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCoins() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("idUser", ParseUser.getCurrentUser().getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object.getString("coins") != null) {
                        coins.setText("coins: " + object.getString("coins"));
                    }
                }
            }
        });

    }


    private void getChallenges(String videoGame, String game, String betCoins) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
        query.whereEqualTo("isFinished", false);
        query.whereEqualTo("videoGame", videoGame);
        query.whereEqualTo("game", game);
        query.whereEqualTo("bet", betCoins);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    challenges.clear();
                    if (objects.size() > 0) {
                        for (ParseObject parseObject : objects) {
                            challenges.add(parseObject);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getChallenges(spnVideoGame.getSelectedItem().toString(), spnGame.getSelectedItem().toString(),
                    spnBet.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void goToNewChallenge() {
        Intent intent = new Intent(Main2Activity.this, CreateChallengeActivity.class);
        startActivity(intent);
    }
}
