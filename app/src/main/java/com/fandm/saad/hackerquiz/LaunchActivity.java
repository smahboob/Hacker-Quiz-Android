package com.fandm.saad.hackerquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fandm.saad.hackerquiz.database.QuizDatabaseHelper;

import java.util.Objects;

public class LaunchActivity extends AppCompatActivity {

    private QuizDatabaseHelper databaseHelper;
    Animation topAnim, bottomAnim;
    ImageView splash_image;
    private static final int SPLASH_SCREEN = 1500; //means 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom_center);
        TextView tv = findViewById(R.id.action_bar_title_center);
        tv.setText(getResources().getString(R.string.quiz_title));

        //initialize database
        databaseHelper = new QuizDatabaseHelper(getApplicationContext());
        databaseHelper.getReadableDatabase();

        //loading animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottome_anim);
        splash_image = findViewById(R.id.splash_image);
        splash_image.setAnimation(topAnim);


        //call new activity delayed for 3 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //check if the user already exists, then send them to the Home Page
            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            if(databaseHelper.user_already_exists(android_id)){
                Intent startQuiz = new Intent(this, QuizHomeActivity.class);
                startQuiz.putExtra("user_device_id", android_id);
                startActivity(startQuiz);
                finish();
            }
            else{
                Intent registerUser = new Intent(this, RegisterUserActivity.class);
                registerUser.putExtra("user_device_id", android_id);
                startActivity(registerUser);
                finish();
            }

        }, SPLASH_SCREEN);

    }
}