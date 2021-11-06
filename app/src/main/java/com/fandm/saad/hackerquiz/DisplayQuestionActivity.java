package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.Question;
import com.fandm.saad.hackerquiz.models.User;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import org.parceler.Parcels;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DisplayQuestionActivity extends AppCompatActivity {

    private final String TAG = "DisplayQuestion_TAG";
    private TextView question_statement;
    private RadioButton answer_1, answer_2, answer_3, answer_4;
    private Button confirm_button; private ImageButton reset_button;
    private List<Question> questionList;

    private int questionCounter = 0;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private QuizDatabaseHelper databaseHelper;
    private User current_user;
    private String category, difficulty_level;
    private LottieAnimationView animationView;

    private TextView question_display_counter;

    private MaterialCardView option1, option2, option3, option4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0B4879")));

        initialize();
        String title = category.substring(0, 1).toUpperCase() + category.substring(1) +  " Quiz";
        actionBar.setTitle(title);

        update_radio_buttons(false,false,false,false);
        loadQuiz(category, difficulty_level);
    }

    private void update_radio_buttons(boolean b, boolean b1, boolean b2, boolean b3) {
        answer_1.setChecked(b);answer_2.setChecked(b1);
        answer_3.setChecked(b2);answer_4.setChecked(b3);
    }


    private void initialize(){
        //animation
        animationView = findViewById(R.id.animationViewDisplayQuestion);

        //initialize database
        databaseHelper = new QuizDatabaseHelper(getApplicationContext());
        databaseHelper.getReadableDatabase();

        category = getIntent().getStringExtra("quiz_type");
        difficulty_level = getIntent().getStringExtra("difficulty_level");
        current_user = Parcels.unwrap(getIntent().getParcelableExtra("current_user"));

        question_statement = findViewById(R.id.text_view_question);

        answer_1 = findViewById(R.id.radio_button1);answer_2 = findViewById(R.id.radio_button2);
        answer_3 = findViewById(R.id.radio_button3);answer_4 = findViewById(R.id.radio_button4);

        answer_1.setOnClickListener(v -> update_radio_buttons(true,false,false,false));
        answer_2.setOnClickListener(v -> update_radio_buttons(false,true,false,false));
        answer_3.setOnClickListener(v -> update_radio_buttons(false,false,true,false));
        answer_4.setOnClickListener(v -> update_radio_buttons(false,false,false,true));

        option1 = findViewById(R.id.radio_card_1);option2 = findViewById(R.id.radio_card_2);
        option3 = findViewById(R.id.radio_card_3);option4 = findViewById(R.id.radio_card_4);


        confirm_button = findViewById(R.id.button_confirm_next);
        reset_button = findViewById(R.id.quiz_reset_button);

        reset_button.setOnClickListener(v -> resetQuiz());
        question_display_counter = findViewById(R.id.question_display_counter);
    }

    private void resetQuiz() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Restart")
                .setMessage("Are you sure you want to restart this quiz?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    questionCounter = 0;

                    //initialize
                    initialize();

                    //check which one is clicked
                    update_radio_buttons(false,false,false,false);

                    loadQuiz(category, difficulty_level);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            Log.d(TAG, "Came to back button");
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
        answered = true;

        int answer_selected = -1;
        if (answer_1.isChecked()) {
            answer_selected = 1;
        }
        else if (answer_2.isChecked()){
            answer_selected = 2;
        }
        else if (answer_3.isChecked()){
            answer_selected = 3;
        }
        else if (answer_4.isChecked()){
            answer_selected = 4;
        }


        if (answer_selected == currentQuestion.getCorrect_answer()) {
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
        option1.setStrokeColor(Color.parseColor("#FF0000"));option2.setStrokeColor(Color.parseColor("#FF0000"));
        option3.setStrokeColor(Color.parseColor("#FF0000"));option4.setStrokeColor(Color.parseColor("#FF0000"));
        option1.setStrokeWidth(2);option2.setStrokeWidth(2);option3.setStrokeWidth(2);option4.setStrokeWidth(2);

        answer_1.setEnabled(false);answer_2.setEnabled(false);answer_3.setEnabled(false);answer_4.setEnabled(false);

        switch (currentQuestion.getCorrect_answer()) {
            case 1:
                option1.setStrokeColor(Color.parseColor("#00A300"));
                option1.setStrokeWidth(4);
                break;
            case 2:
                option2.setStrokeColor(Color.parseColor("#00A300"));
                option2.setStrokeWidth(4);
                break;
            case 3:
                option3.setStrokeColor(Color.parseColor("#00A300"));
                option3.setStrokeWidth(4);
                break;
            case 4:
                option4.setStrokeColor(Color.parseColor("#00A300"));
                option4.setStrokeWidth(4);
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
        animationView.setVisibility(View.INVISIBLE);
        option1.setStrokeColor(Color.parseColor("#000000"));option2.setStrokeColor(Color.parseColor("#000000"));
        option3.setStrokeColor(Color.parseColor("#000000"));option4.setStrokeColor(Color.parseColor("#000000"));
        option1.setStrokeWidth(1);option2.setStrokeWidth(1);option3.setStrokeWidth(1);option4.setStrokeWidth(1);

        answer_1.setEnabled(true);answer_2.setEnabled(true);
        answer_3.setEnabled(true);answer_4.setEnabled(true);


        update_radio_buttons(false,false,false,false);
        answer_1.clearFocus();answer_2.clearFocus();answer_3.clearFocus();answer_4.clearFocus();

        //if any questions left, show the questions
        if(questionCounter < questionCountTotal){

            String text = (questionCounter+1) + "/" + questionCountTotal;
            question_display_counter.setText(text);

            currentQuestion = questionList.get(questionCounter);
            question_statement.setText(currentQuestion.getQuestion_statement());
            answer_1.setText(currentQuestion.getAnswer_1());answer_2.setText(currentQuestion.getAnswer_2());
            answer_3.setText(currentQuestion.getAnswer_3());answer_4.setText(currentQuestion.getAnswer_4());
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