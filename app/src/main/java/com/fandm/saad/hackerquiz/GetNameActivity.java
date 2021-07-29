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
            String verification_code = getIntent().getStringExtra("random_code");

            if(full_name.isEmpty() || full_name.length() < 2){
                Snackbar.make(findViewById(R.id.new_user_verification_code), "Valid Name Required", Snackbar.LENGTH_LONG).show();
                user_full_name_text.setError("Valid Name Required!");
                user_full_name_text.requestFocus();
            }
            else{
                if (ContextCompat.checkSelfPermission(GetNameActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendMessage(phone_number,verification_code,full_name);
                }
                else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},2);
                }
            }
        });
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


    private void sendMessage(String phone_number, String verification_code, String full_name){

        Log.d("TAG: code", verification_code);
        Log.d("TAG: status", "calling send sms message");

        //generate a random code and send sms
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone_number,null,verification_code,null,null);
        Toast.makeText(this, "Verification code sent successfully!", Toast.LENGTH_SHORT).show();

        //start activity to verify the code
        Intent register_new_user = new Intent(this, VerifyNewUserCode.class);
        register_new_user.putExtra("verification_code", verification_code);
        register_new_user.putExtra("phone_number", phone_number);
        register_new_user.putExtra("full_name", full_name);
        startActivity(register_new_user);
    }

}