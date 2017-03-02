package com.mouthofrandom.cardshark.activities;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.graphics.ActionButton;
import com.mouthofrandom.cardshark.graphics.Sprite;
import com.mouthofrandom.cardshark.graphics.TileMap;
import com.mouthofrandom.cardshark.graphics.utility.CardSharkView;

public class CasinoActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //lock portrait mode
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //housekeeping
        super.onCreate(savedInstanceState);

        new TileMap(getApplicationContext());

        //call main activity
        setContentView(R.layout.activity_casino);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen
    }
}

class CasinoView extends CardSharkView
{
    private static ActionButton actionButton = new ActionButton();

    public CasinoView(Context context, AttributeSet attrs)
    {
        super(context, attrs, new TileMap(context, actionButton.setContext(context)), new Sprite(context), actionButton);
    }
}

