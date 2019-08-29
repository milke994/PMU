package com.example.dusanmilic.projekat2019.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.database.entities.MatchEntity;


import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchHolder> {
    private List<MatchEntity> matches = new ArrayList<>();

    public MatchesAdapter() {
    }

    public void set(List<MatchEntity> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_activity, parent, false);

        return new MatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHolder holder, int position) {
        holder.setPlayer1Score(matches.get(position).getPlayer1Score());
        holder.setPlayer2Score(matches.get(position).getPlayer2Score());
        holder.setTime(matches.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class MatchHolder extends RecyclerView.ViewHolder{

        private TextView player1Score, player2Score, time;

        public MatchHolder(@NonNull View itemView) {
            super(itemView);
            player1Score = itemView.findViewById(R.id.detailActivityTextViewPlayer1Score);
            player2Score = itemView.findViewById(R.id.detailActivityTextViewPlayer2Score);
            time = itemView.findViewById(R.id.detailActivityTextViewTime);
        }

        public void setPlayer1Score(int player1Score){
            this.player1Score.setText(String.valueOf(player1Score));
        }

        public void setPlayer2Score(int player2Score){
            this.player2Score.setText(String.valueOf(player2Score));
        }

        public void setTime(String time){
            this.time.setText(time);
        }
    }
}
