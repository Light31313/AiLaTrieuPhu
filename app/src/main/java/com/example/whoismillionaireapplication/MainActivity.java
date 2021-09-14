package com.example.whoismillionaireapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.whoismillionaireapplication.fragment.AskAudienceFragment;
import com.example.whoismillionaireapplication.fragment.CallMomFragment;
import com.example.whoismillionaireapplication.fragment.QuestionAndAnswerFragment;
import com.example.whoismillionaireapplication.fragment.QuestionFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnPhone, btnAskPeople, btn50_50, btnStop;
    private Animation reverseTransitionAndAppearAnim;
    private MainViewModel viewModel;
    private boolean isChoose5050, isChooseCall, isChooseAsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initComponent();
        initEvent();
        getSupportFragmentManager().beginTransaction().replace(R.id.fc_question, QuestionAndAnswerFragment.class, null, "questionAndAnswer").
                setReorderingAllowed(true).setCustomAnimations(R.anim.transion_up, R.anim.transition_down, R.anim.transion_up, R.anim.transition_down).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fc_level, QuestionFragment.class, null, "questionFragment").commit();

    }

    private void initView() {
        btn50_50 = findViewById(R.id.btn_50_50);
        btnAskPeople = findViewById(R.id.btn_ask_people);
        btnPhone = findViewById(R.id.btn_phone);
        btnStop = findViewById(R.id.btn_stop);
        //imgLogo = findViewById(R.id.img_logo);
    }

    private void initComponent() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        //private ImageView imgLogo;
        Animation transitionAndAppearAnim = AnimationUtils.loadAnimation(this, R.anim.transition_in_and_appear);
        reverseTransitionAndAppearAnim = AnimationUtils.loadAnimation(this, R.anim.reverse_transition_and_appear);

        //imgLogo.startAnimation(rotateAnim);
        btnStop.startAnimation(transitionAndAppearAnim);
        btnPhone.startAnimation(transitionAndAppearAnim);
        btnAskPeople.startAnimation(transitionAndAppearAnim);
        btn50_50.startAnimation(transitionAndAppearAnim);
    }

    private void initEvent() {
        btn50_50.setOnClickListener(this);
        btnAskPeople.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        viewModel.getEnableClickListener().observe(this, enableClickListener -> {
            if (!enableClickListener) {
                btn50_50.setEnabled(false);
                btnAskPeople.setEnabled(false);
                btnPhone.setEnabled(false);
                btnStop.setEnabled(false);
            } else {
                if (!isChoose5050)
                    btn50_50.setEnabled(true);
                if (!isChooseAsk)
                    btnAskPeople.setEnabled(true);
                if (!isChooseCall)
                    btnPhone.setEnabled(true);
                btnStop.setEnabled(true);
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.btn_50_50) {
            isChoose5050 = true;
            btn50_50.setImageResource(R.drawable.percent502);
            viewModel.setEvent5050(true);
            btn50_50.setEnabled(false);
        } else if (id == R.id.btn_phone) {
            isChooseCall = true;
            btnPhone.setImageResource(R.drawable.call2);
            btnPhone.setEnabled(false);
            viewModel.setOtherEvent(true);
            fragmentTransaction.add(R.id.fc_question, CallMomFragment.class, null).addToBackStack(null)
                    .setCustomAnimations(R.anim.transion_up, R.anim.transition_down, R.anim.transion_up, R.anim.transition_down).commit();
        } else if (id == R.id.btn_ask_people) {
            isChooseAsk = true;
            btnAskPeople.setImageResource(R.drawable.help2);
            btnAskPeople.setEnabled(false);
            viewModel.setOtherEvent(true);
            fragmentTransaction.add(R.id.fc_question, AskAudienceFragment.class, null).addToBackStack(null)
                    .setCustomAnimations(R.anim.transion_up, R.anim.transition_down, R.anim.transion_up, R.anim.transition_down).commit();
        } else {
            viewModel.setStopEvent(true);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
        btnStop.startAnimation(reverseTransitionAndAppearAnim);
        btnAskPeople.startAnimation(reverseTransitionAndAppearAnim);
        btnPhone.startAnimation(reverseTransitionAndAppearAnim);
        btn50_50.startAnimation(reverseTransitionAndAppearAnim);
    }
}