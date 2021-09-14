package com.example.whoismillionaireapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart, btnScore;
    ImageView imgLogo;
    Animation rotateAnim;
    Animation transitionInAndAppearAnim, reverseTransitionInAndAppearAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initComponent();
        initEvent();
    }

    private void initView() {
        btnStart = findViewById(R.id.btn_start);
        btnScore = findViewById(R.id.btn_score);
        imgLogo = findViewById(R.id.img_logo);

    }

    private void initComponent() {
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imgLogo.startAnimation(rotateAnim);

        transitionInAndAppearAnim = AnimationUtils.loadAnimation(this, R.anim.transition_in_and_appear);
        reverseTransitionInAndAppearAnim = AnimationUtils.loadAnimation(this, R.anim.reverse_transition_and_appear);
    }

    private void initEvent() {
        btnStart.setOnClickListener(this);
        btnScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_start) {
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.btn_score)
            Toast.makeText(this, "Chờ cập nhật nha :(", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnScore.startAnimation(transitionInAndAppearAnim);
        btnStart.startAnimation(transitionInAndAppearAnim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        btnScore.startAnimation(reverseTransitionInAndAppearAnim);
        btnStart.startAnimation(reverseTransitionInAndAppearAnim);
    }
}