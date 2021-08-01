package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegisterUserActivity extends AppCompatActivity {

    private TextInputLayout quiz_user_name;
    private QuizDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        String android_id = getIntent().getStringExtra("user_device_id");

        //initialize database
        databaseHelper = new QuizDatabaseHelper(getApplicationContext());
        databaseHelper.getReadableDatabase();
        quiz_user_name = findViewById(R.id.user_full_name);

        //lottie animation
        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.welcome);


        //button to start the quiz
        Button start = findViewById(R.id.start_button_launch);
        start.setOnClickListener(v -> authorizeUser(android_id));
    }

    private void authorizeUser(String android_id) {
        String user_name = Objects.requireNonNull(quiz_user_name.getEditText()).getText().toString();
        if(user_name.isEmpty() || user_name.length() < 2){
            quiz_user_name.setError("Valid name required to proceed!");
            quiz_user_name.requestFocus();
        }
        else{
            databaseHelper.add_new_user(new User(user_name,android_id));
            Intent startQuiz = new Intent(this, QuizHomeActivity.class);
            startQuiz.putExtra("user_device_id", android_id);
            startActivity(startQuiz);
            finish();
        }
    }
}