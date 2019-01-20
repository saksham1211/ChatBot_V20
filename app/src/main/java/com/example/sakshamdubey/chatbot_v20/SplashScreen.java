package com.example.sakshamdubey.chatbot_v20;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        VideoView view = findViewById(R.id.video);
        String path = "android.resource://com.example.sakshamdubey.bot/"+R.raw.intro;
        Uri uri = Uri.parse(path);
        view.setVideoURI(uri);
        view.start();

        new Handler().postDelayed(() -> {


            Intent mySuperIntent;
            mySuperIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(mySuperIntent);

            finish();
        }, SPLASH_TIME);


    }
}
