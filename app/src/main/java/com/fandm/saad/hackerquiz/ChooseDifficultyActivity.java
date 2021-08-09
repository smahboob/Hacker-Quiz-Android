package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Button;
import android.widget.TextView;

import com.fandm.saad.hackerquiz.models.Question;
import com.fandm.saad.hackerquiz.models.User;

import org.parceler.Parcels;

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
        User current_user = Parcels.unwrap(getIntent().getParcelableExtra("current_user"));

        Button easy_btn = findViewById(R.id.easy_button);
        Button medium_btn = findViewById(R.id.med_button);
        Button hard_btn = findViewById(R.id.hard_button);

        easy_btn.setOnClickListener(v -> startQuiz( quiz_type, Question.DIFFICULTY_EASY, current_user));
        medium_btn.setOnClickListener(v -> startQuiz( quiz_type,Question.DIFFICULTY_MEDIUM, current_user));
        hard_btn.setOnClickListener(v -> startQuiz(quiz_type,Question.DIFFICULTY_HARD, current_user));

    }

    private void startQuiz(String category, String difficulty, User current_user) {
        Intent startQuiz = new Intent(this, DisplayQuestionActivity.class);
        startQuiz.putExtra("quiz_type", category);
        startQuiz.putExtra("difficulty_level", difficulty);
        startQuiz.putExtra("current_user", Parcels.wrap(current_user));
        startActivity(startQuiz);
        finishAfterTransition();
    }
}