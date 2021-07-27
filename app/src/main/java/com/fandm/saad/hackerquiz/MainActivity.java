package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent start_quiz = new Intent(this, DisplayQuestion.class);
        start_quiz.putExtra("quiz_type", type);
        startActivity(start_quiz);
    }
}