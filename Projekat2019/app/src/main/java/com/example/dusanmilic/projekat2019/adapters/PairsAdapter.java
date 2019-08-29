package com.example.dusanmilic.projekat2019.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dusanmilic.projekat2019.R;
import com.example.dusanmilic.projekat2019.activities.StatisticsDetailActivity;
import com.example.dusanmilic.projekat2019.database.entities.PairEntity;
import com.example.dusanmilic.projekat2019.database.entities.PlayerEntity;
import com.example.dusanmilic.projekat2019.database.models.PlayerModel;


import java.util.ArrayList;
import java.util.List;

public class PairsAdapter extends RecyclerView.Adapter<PairsAdapter.PairHolder> {
    private Context context;
    private List<PairEntity> pairs = new ArrayList<>();
    private PlayerModel playerModel;

    public PairsAdapter(Context context, PlayerModel playerModel) {
        this.context = context;
        this.playerModel = playerModel;
    }

    public void setPairs(List<PairEntity> pairs){
        this.pairs = pairs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PairHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pair_detail_activity, parent, false);
        return new PairHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PairHolder holder, int position) {
        PlayerEntity player1 = playerModel.find(pairs.get(position).getPlayer1Id());
        PlayerEntity player2 = playerModel.find(pairs.get(position).getPlayer2Id());
        holder.setViews(player1.getName(), pairs.get(position).getPlayer1Wins(), player2.getName(), pairs.get(position).getPlayer2Wins());
    }


    @Override
    public int getItemCount() {
        return pairs.size();
    }

    class PairHolder extends RecyclerView.ViewHolder{
        private TextView player1Name, player2Name, player1Wins, player2Wins;

        public PairHolder(@NonNull View itemView) {
            super(itemView);
            player1Name = itemView.findViewById(R.id.player1NameTextViewPairDetailActivity);
            player2Name = itemView.findViewById(R.id.player2NameTextViewPairDetailActivity);
            player1Wins = itemView.findViewById(R.id.player1WinsTextViewPairDetailActivity);
            player2Wins = itemView.findViewById(R.id.player2WinsTextViewPairDetailActivity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StatisticsDetailActivity.class);
                    intent.putExtra("player1Name", player1Name.getText().toString());
                    intent.putExtra("player2Name", player2Name.getText().toString());
                    intent.putExtra("player1Wins", player1Wins.getText().toString());
                    intent.putExtra("player2Wins", player2Wins.getText().toString());
                    context.startActivity(intent);
                }
            });
        }

        public void setViews(String p1, int p1Wins, String p2, int p2Wins){
            this.player1Name.setText(p1);
            this.player1Wins.setText(String.valueOf(p1Wins));
            this.player2Name.setText(p2);
            this.player2Wins.setText(String.valueOf(p2Wins));
        }
    }
}
