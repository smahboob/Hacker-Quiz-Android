package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DisplayQuestionActivity extends AppCompatActivity {

    private TextView question_statement;
    private RadioGroup answerRadioGroup;
    private RadioButton answer_1;
    private RadioButton answer_2;
    private RadioButton answer_3;
    private RadioButton answer_4;
    private Button confirm_button;
    private List<Question> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        TextView tv = findViewById(R.id.action_bar_title);
        tv.setText(getResources().getString(R.string.quiz_title));


        String category = getIntent().getStringExtra("quiz_type");
        String difficulty_level = getIntent().getStringExtra("difficulty_level");
        String android_id = getIntent().getStringExtra("user_device_id");

        question_statement = findViewById(R.id.text_view_question);
        answerRadioGroup = findViewById(R.id.radio_group);
        answer_1 = findViewById(R.id.radio_button1);
        answer_2 = findViewById(R.id.radio_button2);
        answer_3 = findViewById(R.id.radio_button3);
        answer_4 = findViewById(R.id.radio_button4);
        confirm_button = findViewById(R.id.button_confirm_next);

        loadQuiz(category, difficulty_level);
    }

    //load the database and show question
    private void loadQuiz(String category, String difficulty_level) {
        QuizDatabaseHelper quizDatabaseHelper = new QuizDatabaseHelper(this);
        questionList = quizDatabaseHelper.getQuestionsOfCategoryAndDifficulty(difficulty_level,category);
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        confirm_button.setOnClickListener(v -> {
            if(!answered){
                if(answer_1.isChecked() || answer_2.isChecked() || answer_3.isChecked() || answer_4.isChecked()){
                    check_answer();
                } else{
                    Snackbar.make(findViewById(R.id.quiz_question_id), "Please select an answer to proceed.", Snackbar.LENGTH_LONG).show();
                }
            } else{
                showNextQuestion();
            }
        });
    }

    private void check_answer() {
        answered = true;
        RadioButton rbSelected = findViewById(answerRadioGroup.getCheckedRadioButtonId());
        int answerNr = answerRadioGroup.indexOfChild(rbSelected) + 1;
        if (answerNr == currentQuestion.getCorrect_answer()) {
            score++;
        }
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
        if (questionCounter < questionCountTotal) {
            confirm_button.setText(R.string.button_text_next);
        } else {
            confirm_button.setText(R.string.confirm_button_text);
        }
    }

    private void showNextQuestion(){
        //set normal background for unanswered
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
            questionCounter++;
            answered = false;
            confirm_button.setText(R.string.confirm_button_text);
        }
        else{
         finishQuiz();
        }
    }

    private void finishQuiz() {
        finish();
    }

}