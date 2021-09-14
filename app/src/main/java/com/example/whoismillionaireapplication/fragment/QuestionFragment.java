package com.example.whoismillionaireapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whoismillionaireapplication.FragmentViewModel;
import com.example.whoismillionaireapplication.R;

public class QuestionFragment extends Fragment {
    private TextView[] txtQuestions;
    private FragmentViewModel viewModel;
    private LinearLayout llLevel;
    private Animation transitionInAnim, transitionOutAnim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        txtQuestions = new TextView[15];
        txtQuestions[0] = view.findViewById(R.id.txt_question1);
        txtQuestions[1] = view.findViewById(R.id.txt_question2);
        txtQuestions[2] = view.findViewById(R.id.txt_question3);
        txtQuestions[3] = view.findViewById(R.id.txt_question4);
        txtQuestions[4] = view.findViewById(R.id.txt_question5);
        txtQuestions[5] = view.findViewById(R.id.txt_question6);
        txtQuestions[6] = view.findViewById(R.id.txt_question7);
        txtQuestions[7] = view.findViewById(R.id.txt_question8);
        txtQuestions[8] = view.findViewById(R.id.txt_question9);
        txtQuestions[9] = view.findViewById(R.id.txt_question10);
        txtQuestions[10] = view.findViewById(R.id.txt_question11);
        txtQuestions[11] = view.findViewById(R.id.txt_question12);
        txtQuestions[12] = view.findViewById(R.id.txt_question13);
        txtQuestions[13] = view.findViewById(R.id.txt_question14);
        txtQuestions[14] = view.findViewById(R.id.txt_question15);
        llLevel = view.findViewById(R.id.ll_level);
        return view;
    }

    public LinearLayout getLlLevel() {
        return llLevel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        initEvent();
    }

    private void initComponent() {
        //set animation
        transitionInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.transition_in);
        transitionOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.transition_out);
        // receive level data from QuestionAndAnswerFragment class
        viewModel = new ViewModelProvider(requireActivity()).get(FragmentViewModel.class);
        viewModel.getLevel().observe(getViewLifecycleOwner(), level -> {
            txtQuestions[level - 1].setBackgroundResource(R.drawable.highscore1);
        });

    }

    private void initEvent() {
        viewModel.getVisible().observe(getViewLifecycleOwner(), isVisible -> {
            if (isVisible) {
                llLevel.startAnimation(transitionInAnim);
                llLevel.setVisibility(View.VISIBLE);
            } else {
                llLevel.startAnimation(transitionOutAnim);
                llLevel.setVisibility(View.GONE);
            }
        });

    }
}