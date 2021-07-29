package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fandm.saad.hackerquiz.models.User;

import org.parceler.Parcels;

public class QuizHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        //welcome text
        User current_user = Parcels.unwrap(getIntent().getParcelableExtra("user_data"));
        TextView welcomeText = findViewById(R.id.welcomeTextMain);
        String welcome = getResources().getString(R.string.greeting) + ", " + current_user.getFull_name();
        welcomeText.setText(welcome);

        //buttons to start quiz
        Button python_btn = findViewById(R.id.python_quiz_button);
        Button java_btn = findViewById(R.id.java_quiz_button);
        Button cpp_btn = findViewById(R.id.cpp_quiz_button);
        Button oop_btn = findViewById(R.id.oop_quiz_button);

        python_btn.setOnClickListener(v -> startQuiz("python"));
        java_btn.setOnClickListener(v -> startQuiz("java"));
        cpp_btn.setOnClickListener(v -> startQuiz("cpp"));
        oop_btn.setOnClickListener(v -> startQuiz("oop"));
    }

    private void startQuiz(String type) {
        Intent start_quiz = new Intent(this, ChooseDifficultyActivity.class);
        start_quiz.putExtra("quiz_type", type);
        startActivity(start_quiz);
    }
}