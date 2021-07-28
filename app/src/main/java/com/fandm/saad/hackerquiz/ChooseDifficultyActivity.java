package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ChooseDifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);

        String quiz_type = getIntent().getStringExtra("quiz_type");
        Button easy_btn = findViewById(R.id.easy_button);
        Button medium_btn = findViewById(R.id.med_button);
        Button hard_btn = findViewById(R.id.hard_button);

        easy_btn.setOnClickListener(v -> startQuiz( quiz_type,"Easy"));
        medium_btn.setOnClickListener(v -> startQuiz( quiz_type,"Medium"));
        hard_btn.setOnClickListener(v -> startQuiz(quiz_type,"Hard"));

    }

    private void startQuiz(String category, String difficulty) {
        Intent startQuiz = new Intent(this, DisplayQuestionActivity.class);
        startQuiz.putExtra("quiz_type", category);
        startQuiz.putExtra("difficulty_level", difficulty);
        startActivity(startQuiz);
        finishAfterTransition();
    }
}