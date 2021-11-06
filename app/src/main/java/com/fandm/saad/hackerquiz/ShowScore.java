package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class ShowScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom_center);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B4879")));
        TextView tv = findViewById(R.id.action_bar_title_center);
        tv.setText(getResources().getString(R.string.quiz_title));

        //animation view
        LottieAnimationView animationView = findViewById(R.id.animationViewShowScore);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(R.raw.congrats);
        animationView.playAnimation();

        int score = getIntent().getIntExtra("score",0);
        int questionTotal = getIntent().getIntExtra("totalQuestions",0);

        TextView score_tv = findViewById(R.id.score_tv);
        String score_txt = score + "/" + questionTotal;
        score_tv.setText(score_txt);

        Button finish_btn = findViewById(R.id.button_end_quiz);
        finish_btn.setOnClickListener(v -> finishQuiz());

    }

    private void finishQuiz() {
        finish();
    }
}