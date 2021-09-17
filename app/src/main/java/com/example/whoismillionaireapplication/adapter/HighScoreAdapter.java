package com.example.whoismillionaireapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whoismillionaireapplication.R;
import com.example.whoismillionaireapplication.entity.HighScore;

import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<HighScore> highScores;

    public HighScoreAdapter(Context context, List<HighScore> highScores) {
        this.context = context;
        this.highScores = highScores;
    }

    private static class HighScoreViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtUserScore;

        public HighScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            LinearLayout llUserScore = itemView.findViewById(R.id.ll_user_score);
            txtUserScore = itemView.findViewById(R.id.txt_user_score);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HighScoreViewHolder(inflater.inflate(R.layout.score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HighScore highScore = highScores.get(position);
        HighScoreViewHolder highScoreViewHolder = (HighScoreViewHolder) holder;
        String textUserScore = context.getString(R.string.userScore, highScore.getName(), highScore.getScore());
        highScoreViewHolder.txtUserScore.setText(textUserScore);
    }

    @Override
    public int getItemCount() {
        return Math.min(15, highScores.size());
    }
}
