package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private User current_user;
    private QuizDatabaseHelper databaseHelper;
    String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom_center);
        TextView tv = findViewById(R.id.action_bar_title_center);
        tv.setText(getResources().getString(R.string.quiz_title));

        //initialize stuff
        initializeViews();

        databaseHelper = new QuizDatabaseHelper(this);
        databaseHelper.getReadableDatabase();

        //get user information
        device_id = getIntent().getStringExtra("user_device_id");
        current_user = databaseHelper.getFullUserInformation(device_id);


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

        python_btn.setOnClickListener(v -> startQuiz("python", current_user));
        java_btn.setOnClickListener(v -> startQuiz("java", current_user));
        cpp_btn.setOnClickListener(v -> startQuiz("cpp", current_user));
        oop_btn.setOnClickListener(v -> startQuiz("oop", current_user));
    }

    @Override
    protected void onResume() {
        super.onResume();
        current_user = databaseHelper.getFullUserInformation(device_id);
        assignValues(current_user);
    }

    private void assignValues(User current_user) {

        python_easy.setProgress((int) (Double.parseDouble(current_user.getPython_score_easy())*100));
        python_med.setProgress((int) (Double.parseDouble(current_user.getPython_score_medium())*100));
        python_hard.setProgress((int) (Double.parseDouble(current_user.getPython_score_hard())*100));

        java_easy.setProgress((int) (Double.parseDouble(current_user.getJava_score_easy()) *100));
        java_med.setProgress((int) (Double.parseDouble(current_user.getJava_score_medium()) *100));
        java_hard.setProgress((int) (Double.parseDouble(current_user.getJava_score_hard()) *100));

        cpp_easy.setProgress((int) (Double.parseDouble(current_user.getCpp_score_easy()) *100));
        cpp_med.setProgress((int) (Double.parseDouble(current_user.getCpp_score_medium()) *100));
        cpp_hard.setProgress((int) (Double.parseDouble(current_user.getCpp_score_hard()) *100));

        oop_easy.setProgress((int) (Double.parseDouble(current_user.getOop_score_easy()) *100));
        oop_med.setProgress((int) (Double.parseDouble(current_user.getOop_score_medium()) *100));
        oop_hard.setProgress((int) (Double.parseDouble(current_user.getOop_score_hard()) *100));


        python_easy_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getPython_score_easy())*100));
        python_med_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getPython_score_medium())*100));
        python_hard_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getPython_score_hard())*100));

        java_easy_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getJava_score_easy()) *100));
        java_med_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getJava_score_medium()) *100));
        java_hard_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getJava_score_hard()) *100));

        cpp_easy_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getCpp_score_easy()) *100));
        cpp_med_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getCpp_score_medium()) *100));
        cpp_hard_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getCpp_score_hard()) *100));

        oop_easy_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getOop_score_easy()) *100));
        oop_med_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getOop_score_medium()) *100));
        oop_hard_text.setText(MessageFormat.format("{0}%", Double.parseDouble(current_user.getOop_score_hard()) *100));

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

    private void startQuiz(String type, User current_user) {
        Intent start_quiz = new Intent(this, ChooseDifficultyActivity.class);
        start_quiz.putExtra("quiz_type", type);
        start_quiz.putExtra("current_user", Parcels.wrap(current_user));
        startActivity(start_quiz);
    }

}