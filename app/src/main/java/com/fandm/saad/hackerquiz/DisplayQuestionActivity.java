package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.Question;
import com.fandm.saad.hackerquiz.models.User;
import com.google.android.material.snackbar.Snackbar;
import org.parceler.Parcels;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DisplayQuestionActivity extends AppCompatActivity {

    private TextView question_statement;
    private RadioGroup answerRadioGroup;
    private RadioButton answer_1, answer_2, answer_3, answer_4;
    private Button confirm_button;
    private List<Question> questionList;

    private CardView cardView;

    private int questionCounter = 0;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private QuizDatabaseHelper databaseHelper;
    private User current_user;
    private String category, difficulty_level;
    private LottieAnimationView animationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B4879")));
        TextView tv = findViewById(R.id.action_bar_title);
        tv.setText(getResources().getString(R.string.quiz_title));

        //animation
        animationView = findViewById(R.id.animationViewDisplayQuestion);
//        cardView = findViewById(R.id.lottie_card_view_question);
//        cardView.setVisibility(View.INVISIBLE);

        //initialize database
        databaseHelper = new QuizDatabaseHelper(getApplicationContext());
        databaseHelper.getReadableDatabase();

        category = getIntent().getStringExtra("quiz_type");
        difficulty_level = getIntent().getStringExtra("difficulty_level");
        current_user = Parcels.unwrap(getIntent().getParcelableExtra("current_user"));

        question_statement = findViewById(R.id.text_view_question);
        answerRadioGroup = findViewById(R.id.radio_group);
        answer_1 = findViewById(R.id.radio_button1);
        answer_2 = findViewById(R.id.radio_button2);
        answer_3 = findViewById(R.id.radio_button3);
        answer_4 = findViewById(R.id.radio_button4);
        confirm_button = findViewById(R.id.button_confirm_next);

        loadQuiz(category, difficulty_level);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        MenuItem home_btn = menu.findItem(R.id.back_to_home);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.back_to_home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //load the database and show question
    private void loadQuiz(String category, String difficulty_level) {
        //start the quiz and get questions from the database
        questionList = databaseHelper.getQuestionsOfCategoryAndDifficulty(difficulty_level,category);
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        //display the question
        showNextQuestion();


        //confirm button
        confirm_button.setOnClickListener(v -> {
            //if the question is not already answered check if anything is selected and if yes, then check for answer
            if(!answered){
                if(answer_1.isChecked() || answer_2.isChecked() || answer_3.isChecked() || answer_4.isChecked()){
                    check_answer();
                } else{
                    Snackbar.make(findViewById(R.id.quiz_question_id), "Please select an answer to proceed.", Snackbar.LENGTH_LONG).show();
                }
            }
            //if the question is already answered show the solution
            else{
                showNextQuestion();
            }
        });
    }


    private void check_answer() {
        animationView.setVisibility(View.VISIBLE);
//        cardView.setVisibility(View.VISIBLE);
        answered = true;
        RadioButton rbSelected = findViewById(answerRadioGroup.getCheckedRadioButtonId());
        int answerNr = answerRadioGroup.indexOfChild(rbSelected) + 1;
        if (answerNr == currentQuestion.getCorrect_answer()) {
            score++;
            animationView.setAnimation(R.raw.correct_tick);
        }
        else{
            animationView.setAnimation(R.raw.wrong_cross);
        }
        animationView.playAnimation();
        showSolution();
    }


    private void showSolution() {
        answer_1.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));
        answer_2.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));
        answer_3.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));
        answer_4.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));

        answer_1.setEnabled(false);
        answer_2.setEnabled(false);
        answer_3.setEnabled(false);
        answer_4.setEnabled(false);

        switch (currentQuestion.getCorrect_answer()) {
            case 1:
                answer_1.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer_background));
                break;
            case 2:
                answer_2.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer_background));
                break;
            case 3:
                answer_3.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer_background));
                break;
            case 4:
                answer_4.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_answer_background));
                break;
        }


        if(questionCounter == questionCountTotal){
            confirm_button.setText(R.string.button_text_show);
        }
        else if (questionCounter < questionCountTotal) {
            confirm_button.setText(R.string.button_text_next);
        }
        else {
            confirm_button.setText(R.string.confirm_button_text);
        }
    }


    private void showNextQuestion(){
        //set normal background for unanswered
//        cardView.setVisibility(View.INVISIBLE);
        animationView.setVisibility(View.INVISIBLE);
        answer_1.setBackground(ContextCompat.getDrawable(this, R.drawable.unanswered_background));
        answer_2.setBackground(ContextCompat.getDrawable(this, R.drawable.unanswered_background));
        answer_3.setBackground(ContextCompat.getDrawable(this, R.drawable.unanswered_background));
        answer_4.setBackground(ContextCompat.getDrawable(this, R.drawable.unanswered_background));
        answer_1.setEnabled(true);
        answer_2.setEnabled(true);
        answer_3.setEnabled(true);
        answer_4.setEnabled(true);
        answerRadioGroup.clearCheck();

        //if any questions left, show the questions
        if(questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);
            question_statement.setText(currentQuestion.getQuestion_statement());
            answer_1.setText(currentQuestion.getAnswer_1());
            answer_2.setText(currentQuestion.getAnswer_2());
            answer_3.setText(currentQuestion.getAnswer_3());
            answer_4.setText(currentQuestion.getAnswer_4());
            confirm_button.setText(R.string.confirm_button_text);
            questionCounter++;
            answered = false;
        }
        else{
            updateUserScore();
            finishQuiz();
        }
    }


    private void updateUserScore(){
        float flt_score = Float.parseFloat(String.valueOf(score))/ Float.parseFloat(String.valueOf(questionCountTotal));
        String updated_score = String.valueOf(flt_score);

        switch (category) {
            case "python":
                switch (difficulty_level) {
                    case Question.DIFFICULTY_EASY:
                        current_user.setPython_score_easy(updated_score);
                        break;
                    case Question.DIFFICULTY_MEDIUM:
                        current_user.setPython_score_medium(updated_score);
                        break;
                    case Question.DIFFICULTY_HARD:
                        current_user.setPython_score_hard(updated_score);
                        break;
                }
                break;
            case "java":
                switch (difficulty_level) {
                    case Question.DIFFICULTY_EASY:
                        current_user.setJava_score_easy(updated_score);
                        break;
                    case Question.DIFFICULTY_MEDIUM:
                        current_user.setJava_score_medium(updated_score);
                        break;
                    case Question.DIFFICULTY_HARD:
                        current_user.setJava_score_hard(updated_score);
                        break;
                }
                break;
            case "cpp":
                switch (difficulty_level) {
                    case Question.DIFFICULTY_EASY:
                        current_user.setCpp_score_easy(updated_score);
                        break;
                    case Question.DIFFICULTY_MEDIUM:
                        current_user.setCpp_score_medium(updated_score);
                        break;
                    case Question.DIFFICULTY_HARD:
                        current_user.setCpp_score_hard(updated_score);
                        break;
                }
                break;
            case "oop":
                switch (difficulty_level) {
                    case Question.DIFFICULTY_EASY:
                        current_user.setOop_score_easy(updated_score);
                        break;
                    case Question.DIFFICULTY_MEDIUM:
                        current_user.setOop_score_medium(updated_score);
                        break;
                    case Question.DIFFICULTY_HARD:
                        current_user.setOop_score_hard(updated_score);
                        break;
                }
                break;
        }

        databaseHelper.updateUserScore(current_user);
    }


    private void finishQuiz() {
        Intent showScore = new Intent(DisplayQuestionActivity.this, ShowScore.class);
        showScore.putExtra("score",score);
        showScore.putExtra("totalQuestions",questionCountTotal);
        startActivity(showScore);
        finishAfterTransition();
    }
}