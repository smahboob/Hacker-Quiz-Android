package com.fandm.saad.hackerquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;
import com.fandm.saad.hackerquiz.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Random;

import java.util.Objects;
import java.util.UUID;

public class LaunchQuizActivity extends AppCompatActivity {

    private TextInputLayout phone_number_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_launcher);

        QuizDatabaseHelper databaseHelper = new QuizDatabaseHelper(getApplicationContext());
        databaseHelper.getReadableDatabase();

        //lottie animation
        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.welcome);

        //user name
        phone_number_ET = findViewById(R.id.user_phone_number);

        //button to start the quiz
        Button start = findViewById(R.id.start_button_launch);
        start.setOnClickListener(v -> authorizeUser(databaseHelper));
    }

    private void authorizeUser(QuizDatabaseHelper databaseHelper) {
        String phone_number = Objects.requireNonNull(phone_number_ET.getEditText()).getText().toString();

        if(phone_number.isEmpty()){
            Snackbar.make(findViewById(R.id.launch_activity), "Please enter the phone number to continue.", Snackbar.LENGTH_LONG).show();
            phone_number_ET.setError("Phone number is required");
            phone_number_ET.requestFocus();
        }

        if(!isValidMobile(phone_number)){
            Snackbar.make(findViewById(R.id.launch_activity), "Please enter a valid phone number to continue.", Snackbar.LENGTH_LONG).show();
            phone_number_ET.setError("Valid Phone number is required");
            phone_number_ET.requestFocus();
        }

        else {
            String verification_code = usingUUID().substring(0,6);
            Log.d("TAG: ", verification_code);

            //if the user exists in database, send them code to their phone number to identify them correctly and log them in
            if(databaseHelper.user_already_exists(phone_number)){
                if (ContextCompat.checkSelfPermission(LaunchQuizActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendMessage(phone_number,verification_code);
                }
                else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},2);
                }
            }
            else{
                Intent registerNewUser = new Intent(this, GetNameActivity.class);
                registerNewUser.putExtra("phone_number", phone_number);
                registerNewUser.putExtra("random_code", verification_code);
                startActivity(registerNewUser);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Enable sms button.
                Toast.makeText(this, "Permission granted! Send message now.", Toast.LENGTH_SHORT).show();

            } else {
                // Permission denied.
                Toast.makeText(this, "Failed to get SMS permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    static String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }

    private void sendMessage(String phone_number, String verification_code){
        //generate a random code and send sms
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone_number,null,verification_code,null,null);
        Toast.makeText(this, "Verification code sent successfully!", Toast.LENGTH_SHORT).show();

        //start activity to verify the code
        Intent verify_existing_user = new Intent(this, VerificationCodeActivity.class);
        verify_existing_user.putExtra("verification_code", verification_code);
        verify_existing_user.putExtra("phone_number", phone_number);
        startActivity(verify_existing_user);
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}