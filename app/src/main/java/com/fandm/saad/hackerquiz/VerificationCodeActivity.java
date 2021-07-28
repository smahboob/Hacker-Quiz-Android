package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import java.util.Objects;

public class VerificationCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertification_code);

        String phone_number = getIntent().getStringExtra("phone_number");
        String verification_code = getIntent().getStringExtra("verification_code");

        TextInputLayout verification_code_input = findViewById(R.id.verification_code_text_input);
        Button verification_code_btn = findViewById(R.id.verification_code_continue_btn);
        verification_code_btn.setOnClickListener(v -> {
            String input_code = Objects.requireNonNull(verification_code_input.getEditText()).getText().toString();
            if(input_code.isEmpty()){
                Snackbar.make(findViewById(R.id.verification_code_activity), "Invalid Verification Code! Check SMS", Snackbar.LENGTH_LONG).show();
                verification_code_input.setError("Check your text messages for the code!");
                verification_code_input.requestFocus();
            }

            if(Objects.requireNonNull(verification_code_input.getEditText()).getText().toString().equals(verification_code)){
                QuizDatabaseHelper databaseHelper = new QuizDatabaseHelper(this);
                User current_user = databaseHelper.getFullUserInformation(phone_number);

                Intent startQuiz = new Intent(this, QuizHomeActivity.class);
                startQuiz.putExtra("user_data", Parcels.wrap(current_user));
                startActivity(startQuiz);
                finishAfterTransition();
            }
        });
    }
}