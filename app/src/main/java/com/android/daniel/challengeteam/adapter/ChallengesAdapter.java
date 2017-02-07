
package com.android.daniel.challengeteam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.daniel.challengeteam.R;
import com.parse.ParseObject;

import java.util.ArrayList;


public class ChallengesAdapter extends RecyclerView.Adapter {

    private ArrayList<ParseObject> challenges;
    private Context context;

    public ChallengesAdapter(ArrayList<ParseObject> challenges, Context context) {
        this.challenges = challenges;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenges_list, parent, false);
        ChallengeViewHolder holder = new ChallengeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChallengeViewHolder viewHolder = (ChallengeViewHolder) holder;

        ParseObject parseObject = challenges.get(position);

        viewHolder.player.setText(parseObject.getString("player1Name"));
        viewHolder.videoGame.setText(parseObject.getString("videoGame"));
        viewHolder.game.setText(parseObject.getString("game"));
        viewHolder.coins.setText("bet: " + parseObject.getString("bet"));

    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }
}