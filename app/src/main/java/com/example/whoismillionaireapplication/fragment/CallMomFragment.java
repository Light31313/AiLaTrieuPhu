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

public class CallMomFragment extends Fragment {
    private TextView txtCallMom;
    private Button btnConfirm;
    private FragmentViewModel fragmentViewModel;
    private ConstraintLayout clCallMom;

    @Override
    public void onResume() {
        super.onResume();
        clCallMom.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.transion_up));
    }

    @Override
    public void onPause() {
        super.onPause();
        clCallMom.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.transition_down));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_mom, container, false);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        txtCallMom = view.findViewById(R.id.txt_call_mom);
        clCallMom = view.findViewById(R.id.cl_call_mom);
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
        btnConfirm.setOnClickListener(view -> getParentFragmentManager().popBackStack());
        fragmentViewModel.getTrueCase().observe(getViewLifecycleOwner(), trueCase -> {
            char answer = getAnswer(trueCase);
                String textCallMom = getString(R.string.callMom, answer);
                txtCallMom.setText(textCallMom);
        });
    }

    private char getAnswer(int trueCase) {
        if (trueCase == 1)
            return 'A';
        else if (trueCase == 2)
            return 'B';
        else if (trueCase == 3)
            return 'C';
        return 'D';
    }
}
