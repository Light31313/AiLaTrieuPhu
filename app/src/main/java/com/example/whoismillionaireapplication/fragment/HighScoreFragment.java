package com.example.whoismillionaireapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whoismillionaireapplication.R;
import com.example.whoismillionaireapplication.adapter.HighScoreAdapter;
import com.example.whoismillionaireapplication.dao.HighScoreDao;
import com.example.whoismillionaireapplication.entity.HighScore;
import com.example.whoismillionaireapplication.utils.ScoreDatabase;

import java.util.Collections;
import java.util.List;

public class HighScoreFragment extends Fragment {
    private ImageButton btnBack;
    private RecyclerView rvScore;
    private ConstraintLayout clScore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.high_score, container, false);
        btnBack = view.findViewById(R.id.btn_back);
        rvScore = view.findViewById(R.id.rv_score);
        clScore = view.findViewById(R.id.cl_score);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        initEvent();
    }

    private void initComponent() {
        HighScoreDao highScoreDao = ScoreDatabase.getInstance(getContext()).highScoreDao();
        List<HighScore> highScores = highScoreDao.getAll();
        Collections.sort(highScores);
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(getContext(), highScores);
        rvScore.setAdapter(highScoreAdapter);
        rvScore.setLayoutManager(new LinearLayoutManager(getContext()));
        clScore.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.transition_in_and_appear));
    }

    private void initEvent() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.reverse_transition_and_appear);
        btnBack.setOnClickListener(view -> {
            getActivity().onBackPressed();
            clScore.startAnimation(animation);
        });
    }
}
