package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class GetNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        TextInputLayout user_full_name_text = findViewById(R.id.user_full_name);
        TextInputEditText textInputEditTextPhone = findViewById(R.id.showUserPhoneET);
        String phone_number = getIntent().getStringExtra("phone_number");
        textInputEditTextPhone.setText(phone_number);

        Button continue_btn = findViewById(R.id.get_name_continue_btn);

        continue_btn.setOnClickListener(v -> {
            String full_name = Objects.requireNonNull(user_full_name_text.getEditText()).getText().toString();
            String verification_code = getIntent().getStringExtra("verification_code");

            if(full_name.isEmpty() || full_name.length() < 2){
                Snackbar.make(findViewById(R.id.new_user_verification_code), "Valid Name Required", Snackbar.LENGTH_LONG).show();
                user_full_name_text.setError("Valid Name Required!");
                user_full_name_text.requestFocus();
            }
            else{
                Intent register_new_user = new Intent(this, VerifyNewUserCode.class);
                register_new_user.putExtra("verification_code", verification_code);
                register_new_user.putExtra("phone_number", phone_number);
                register_new_user.putExtra("full_name", full_name);
                startActivity(register_new_user);
            }
        });
    }
}