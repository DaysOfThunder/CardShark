package com.mouthofrandom.cardshark.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mouthofrandom.cardshark.R;

/**
 * Created by Elliot
 */



public class TitleScreenActivity extends AppCompatActivity{

    AnimationDrawable backgroundAnimation;
    Button startButton;
    Intent casino_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //lock portrait mode
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen

        //set up titleAnimation and backgroundanimation
        backgroundAnimation = (AnimationDrawable) findViewById(R.id.backdrop).getBackground();

        //start the animation
        backgroundAnimation.start();

        //set up activity to switch to
        casino_activity = new Intent(this, CasinoActivity.class);

        //set the button listener
        startButton = (Button) findViewById(R.id.start_button);

        //set up the button's background to be animated
        final AnimationDrawable buttonAnimation = (AnimationDrawable)startButton.getBackground();

        //Set up Music:
        final MediaPlayer player = MediaPlayer.create(this, R.raw.cardshark_menu_screen);
        player.setLooping(true);
        float vol = (float).75;
        player.setVolume(vol,vol);
        player.start();

        //prepare listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonAnimation.start();

                new CountDownTimer(1500, 1000) {
                    public void onFinish() {
                        player.stop();
                        startActivity(casino_activity);
                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

            }
        });



    }

    @Override
    protected void onPause()
    {
        super.onPause();


    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }
}

