package com.mouthofrandom.cardshark.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.graphics.utility.CustomAnimationDrawable;

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


        CustomAnimationDrawable buttonAnimation = new CustomAnimationDrawable() {

            @Override
            onAnimationFinish(){

            }

        };
        //start the animation
        titleAnimation.start();

        //set up activity to switch to
        casino_activity = new Intent(this, CasinoActivity.class);

        //set the button listener
        startButton = (Button) findViewById(R.id.start_button);

        //set up the button's background to be animated
        final AnimationDrawable buttonAnimation = (CustomAnimationDrawable) startButton.getBackground();

        //prepare listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonAnimation.start();

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
