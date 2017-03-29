package com.mouthofrandom.cardshark.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mouthofrandom.cardshark.R;

/**
 * Created by Elliot
 */



public class TitleScreenActivity extends AppCompatActivity{

    AnimationDrawable titleAnimation;
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

        //get the image view from the activity_title_screen layout and add the background resource then begin animation
        ImageView sharksplash = (ImageView) findViewById(R.id.sharksplash);
        sharksplash.setBackgroundResource(R.drawable.title_animation);
        titleAnimation = (AnimationDrawable) sharksplash.getBackground();


        //set up activity to switch to
        casino_activity = new Intent(this, CasinoActivity.class);

        //set the button listener
        startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(startButton))
            }
        });

        //start the animation
        titleAnimation.start();

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
