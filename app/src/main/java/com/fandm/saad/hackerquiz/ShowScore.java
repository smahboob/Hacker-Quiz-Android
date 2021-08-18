package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ShowScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

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
        finish_btn.setOnClickListener(v -> {
            finishQuiz();
        });

    }

    private void finishQuiz() {
        finish();
    }
}