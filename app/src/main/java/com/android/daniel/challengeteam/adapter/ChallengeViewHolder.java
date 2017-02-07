package com.android.daniel.challengeteam.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.daniel.challengeteam.R;

public class ChallengeViewHolder extends RecyclerView.ViewHolder {

    final TextView player;
    final TextView videoGame;
    final TextView game;
    final TextView coins;
    final CardView cardView;

    public ChallengeViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardview_challenges);
        player = (TextView) itemView.findViewById(R.id.txt_player_name);
        videoGame = (TextView) itemView.findViewById(R.id.txt_video_game);
        game = (TextView) itemView.findViewById(R.id.txt_game);
        coins = (TextView) itemView.findViewById(R.id.txt_coins);
    }
}
