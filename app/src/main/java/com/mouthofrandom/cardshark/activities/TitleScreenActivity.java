package com.mouthofrandom.cardshark.activities;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mouthofrandom.cardshark.R;

/**
 * Created by Elliot on 3/27/2017.
 */



public class TitleScreenActivity extends AppCompatActivity{

    AnimationDrawable titleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        //get the image view from the activity_title_screen layout and add the background resource then begin animation
        ImageView sharksplash = (ImageView) findViewById(R.id.sharksplash);
        sharksplash.setBackgroundResource(R.drawable.title_animation);
        titleAnimation = (AnimationDrawable) sharksplash.getBackground();
        titleAnimation.start();



    }
}
