package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.User;

import org.parceler.Parcels;

import java.text.MessageFormat;
import java.util.Objects;

public class QuizHomeActivity extends AppCompatActivity {

    private ProgressBar python_easy, python_med, python_hard, java_easy, java_med, java_hard, cpp_easy, cpp_med, cpp_hard, oop_easy, oop_med, oop_hard;
    private Button python_btn, java_btn, cpp_btn, oop_btn;
    private TextView python_easy_text, python_med_text, python_hard_text,  java_easy_text, java_med_text, java_hard_text, cpp_easy_text, cpp_med_text, cpp_hard_text, oop_easy_text, oop_med_text, oop_hard_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        TextView tv = findViewById(R.id.action_bar_title);
        tv.setText(getResources().getString(R.string.quiz_title));

        //initialize stuff
        initializeViews();

        QuizDatabaseHelper databaseHelper = new QuizDatabaseHelper(this);
        databaseHelper.getReadableDatabase();

        //get user information
        String device_id = getIntent().getStringExtra("user_device_id");
        User current_user = databaseHelper.getFullUserInformation(device_id);

        String user_name = current_user.getFull_name();
        int idx = user_name.indexOf(" ");
        if(idx != -1){
            user_name = user_name.substring(0,idx+1);
        }
        if(user_name.length() > 7){
            user_name = user_name + "\n";
        }

        //welcome text
        TextView welcomeText = findViewById(R.id.welcomeTextMain);
        String welcome = getResources().getString(R.string.greeting) + ", " + user_name;
        welcomeText.setText(welcome);


        //assign values
        assignValues(current_user);

        python_btn.setOnClickListener(v -> startQuiz("python", device_id));
        java_btn.setOnClickListener(v -> startQuiz("java", device_id));
        cpp_btn.setOnClickListener(v -> startQuiz("cpp", device_id));
        oop_btn.setOnClickListener(v -> startQuiz("oop", device_id));
    }

    private void assignValues(User current_user) {
        python_easy.setProgress(current_user.getPython_score_easy());
        python_med.setProgress(current_user.getPython_score_medium());
        python_hard.setProgress(current_user.getPython_score_hard());

        java_easy.setProgress(current_user.getJava_score_easy());
        java_med.setProgress(current_user.getJava_score_medium());
        java_hard.setProgress(current_user.getJava_score_hard());

        cpp_easy.setProgress(current_user.getCpp_score_easy());
        cpp_med.setProgress(current_user.getCpp_score_medium());
        cpp_hard.setProgress(current_user.getCpp_score_hard());

        oop_easy.setProgress(current_user.getOop_score_easy());
        oop_med.setProgress(current_user.getOop_score_medium());
        oop_hard.setProgress(current_user.getOop_score_hard());


        python_easy_text.setText(MessageFormat.format("{0}%", current_user.getPython_score_easy()));
        python_med_text.setText(MessageFormat.format("{0}%", current_user.getPython_score_medium()));
        python_hard_text.setText(MessageFormat.format("{0}%", current_user.getPython_score_hard()));

        java_easy_text.setText(MessageFormat.format("{0}%", current_user.getJava_score_easy()));
        java_med_text.setText(MessageFormat.format("{0}%", current_user.getJava_score_medium()));
        java_hard_text.setText(MessageFormat.format("{0}%", current_user.getJava_score_hard()));

        cpp_easy_text.setText(MessageFormat.format("{0}%", current_user.getCpp_score_easy()));
        cpp_med_text.setText(MessageFormat.format("{0}%", current_user.getCpp_score_medium()));
        cpp_hard_text.setText(MessageFormat.format("{0}%", current_user.getCpp_score_hard()));

        oop_easy_text.setText(MessageFormat.format("{0}%", current_user.getOop_score_easy()));
        oop_med_text.setText(MessageFormat.format("{0}%", current_user.getOop_score_medium()));
        oop_hard_text.setText(MessageFormat.format("{0}%", current_user.getOop_score_hard()));

    }

    private void initializeViews() {
        //buttons to start quiz
        python_btn = findViewById(R.id.python_quiz_button);
        java_btn = findViewById(R.id.java_quiz_button);
        cpp_btn = findViewById(R.id.cpp_quiz_button);
        oop_btn = findViewById(R.id.oop_quiz_button);

        //progress bars
        python_easy = findViewById(R.id.python_easy_progress);
        python_med = findViewById(R.id.python_medium_progress);
        python_hard = findViewById(R.id.python_hard_progress);

        java_easy = findViewById(R.id.java_easy_progress);
        java_med = findViewById(R.id.java_medium_progress);
        java_hard = findViewById(R.id.java_hard_progress);

        cpp_easy = findViewById(R.id.cpp_easy_progress);
        cpp_med= findViewById(R.id.cpp_medium_progress);
        cpp_hard = findViewById(R.id.cpp_hard_progress);

        oop_easy = findViewById(R.id.oop_easy_progress);
        oop_med= findViewById(R.id.oop_medium_progress);
        oop_hard = findViewById(R.id.oop_hard_progress);

        //percentage texts
        python_easy_text = findViewById(R.id.python_easy_text);
        python_med_text = findViewById(R.id.python_medium_text);
        python_hard_text = findViewById(R.id.python_hard_text);

        java_easy_text = findViewById(R.id.java_easy_text);
        java_med_text = findViewById(R.id.java_medium_text);
        java_hard_text = findViewById(R.id.java_hard_text);

        cpp_easy_text = findViewById(R.id.cpp_easy_text);
        cpp_med_text = findViewById(R.id.cpp_medium_text);
        cpp_hard_text = findViewById(R.id.cpp_hard_text);

        oop_easy_text = findViewById(R.id.oop_easy_text);
        oop_med_text = findViewById(R.id.oop_medium_text);
        oop_hard_text = findViewById(R.id.oop_hard_text);

    }

    private void startQuiz(String type, String android_id) {
        Intent start_quiz = new Intent(this, ChooseDifficultyActivity.class);
        start_quiz.putExtra("quiz_type", type);
        start_quiz.putExtra("user_device_id", android_id);
        startActivity(start_quiz);
    }

}