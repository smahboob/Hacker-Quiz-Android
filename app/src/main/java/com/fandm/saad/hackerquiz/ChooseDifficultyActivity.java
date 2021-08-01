package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class ChooseDifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        TextView tv = findViewById(R.id.action_bar_title);
        tv.setText(getResources().getString(R.string.diff_title));


        String quiz_type = getIntent().getStringExtra("quiz_type");
        String device_id = getIntent().getStringExtra("user_data_id");
        Button easy_btn = findViewById(R.id.easy_button);
        Button medium_btn = findViewById(R.id.med_button);
        Button hard_btn = findViewById(R.id.hard_button);

        easy_btn.setOnClickListener(v -> startQuiz( quiz_type,"Easy", device_id));
        medium_btn.setOnClickListener(v -> startQuiz( quiz_type,"Medium", device_id));
        hard_btn.setOnClickListener(v -> startQuiz(quiz_type,"Hard", device_id));

    }

    private void startQuiz(String category, String difficulty, String device_id) {
        Intent startQuiz = new Intent(this, DisplayQuestionActivity.class);
        startQuiz.putExtra("quiz_type", category);
        startQuiz.putExtra("difficulty_level", difficulty);
        startQuiz.putExtra("user_data_id", device_id);
        startActivity(startQuiz);
        finishAfterTransition();
    }
}