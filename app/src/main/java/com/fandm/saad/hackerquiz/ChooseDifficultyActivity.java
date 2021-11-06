package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        initialize();
    }

    private void initialize(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.diff_title));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B4879")));

        String quiz_type = getIntent().getStringExtra("quiz_type");
        User current_user = Parcels.unwrap(getIntent().getParcelableExtra("current_user"));

        Button easy_btn = findViewById(R.id.easy_button);
        Button medium_btn = findViewById(R.id.med_button);
        Button hard_btn = findViewById(R.id.hard_button);

        easy_btn.setOnClickListener(v -> startQuiz( quiz_type, Question.DIFFICULTY_EASY, current_user));
        medium_btn.setOnClickListener(v -> startQuiz( quiz_type,Question.DIFFICULTY_MEDIUM, current_user));
        hard_btn.setOnClickListener(v -> startQuiz(quiz_type,Question.DIFFICULTY_HARD, current_user));
    }

    //activate back button to finish the activity
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void startQuiz(String category, String difficulty, User current_user) {
        Intent startQuiz = new Intent(this, DisplayQuestionActivity.class);
        startQuiz.putExtra("quiz_type", category);
        startQuiz.putExtra("difficulty_level", difficulty);
        startQuiz.putExtra("current_user", Parcels.wrap(current_user));
        startActivity(startQuiz);
//        finishAfterTransition();
    }
}