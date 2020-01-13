package com.example.room_sql_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            //Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            Intent intent = new Intent(SplashScreen.this, FragmentActivity.class);
            startActivity(intent);
            finish();
        }, 4000);
    }
}
