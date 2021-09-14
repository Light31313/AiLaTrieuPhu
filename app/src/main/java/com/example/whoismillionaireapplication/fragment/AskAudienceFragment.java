package com.example.whoismillionaireapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whoismillionaireapplication.FragmentViewModel;
import com.example.whoismillionaireapplication.R;

import java.util.Random;

public class AskAudienceFragment extends Fragment {
    private Button btnConfirm2;
    private TextView txtAskAudience;
    private ConstraintLayout clAskAudience;
    private FragmentViewModel fragmentViewModel;
    private int percentA, percentB, percentC, percentD;

    @Override
    public void onResume() {
        super.onResume();
        clAskAudience.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.transion_up));
    }

    @Override
    public void onPause() {
        super.onPause();
        clAskAudience.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.transition_down));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ask_audience, container, false);
        btnConfirm2 = view.findViewById(R.id.btn_confirm2);
        txtAskAudience = view.findViewById(R.id.txt_ask_audience);
        clAskAudience = view.findViewById(R.id.cl_ask_audience);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        initEvent();
    }

    private void initComponent() {
        fragmentViewModel = new ViewModelProvider(requireActivity()).get(FragmentViewModel.class);
    }

    private void initEvent() {
        btnConfirm2.setOnClickListener(view -> getParentFragmentManager().popBackStack());
        fragmentViewModel.getTrueCase().observe(getViewLifecycleOwner(), trueCase -> {

            randomPercent(trueCase);
            String textAskAudience = getString(R.string.audienceChoose, percentA, percentB, percentC, percentD);
            txtAskAudience.setText(textAskAudience);
        });
    }

    private void randomPercent(int trueCase) {
        Random random = new Random();
        if (trueCase == 1) {
            percentA = random.nextInt(60) + 40;
            percentB = random.nextInt(101 - percentA);
            percentC = random.nextInt(101 - percentA - percentB);
            percentD = 100 - percentA - percentB - percentC;
        } else if (trueCase == 2) {
            percentB = random.nextInt(60) + 40;
            percentA = random.nextInt(101 - percentB);
            percentC = random.nextInt(101 - percentB - percentA);
            percentD = 100 - percentB - percentA - percentC;
        } else if (trueCase == 3) {
            percentC = random.nextInt(60) + 40;
            percentB = random.nextInt(101 - percentC);
            percentA = random.nextInt(101 - percentC - percentB);
            percentD = 100 - percentC - percentB - percentA;
        } else {
            percentD = random.nextInt(60) + 40;
            percentB = random.nextInt(101 - percentD);
            percentC = random.nextInt(101 - percentD - percentB);
            percentA = 100 - percentD - percentB - percentC;
        }
    }

}
