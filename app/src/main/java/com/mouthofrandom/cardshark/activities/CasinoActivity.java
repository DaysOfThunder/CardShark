package com.mouthofrandom.cardshark.activities;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.game.ActionFactory;
import com.mouthofrandom.cardshark.graphics.Sprite;
import com.mouthofrandom.cardshark.graphics.TileMap;
import com.mouthofrandom.cardshark.graphics.utility.CardSharkView;

public class CasinoActivity extends AppCompatActivity
{
    FloatingActionButton actionButton;
    private final Activity activity = this;

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

        actionButton = (FloatingActionButton) findViewById(R.id.mapActionButton);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen
    }

    public void setButtonAction(final ActionFactory.Action action)
    {
        actionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                action.doAction(activity);
            }
        });
    }

    public void setActionButtonVisibility(boolean visible)
    {
        if(!visible)
        {
            actionButton.setVisibility(View.GONE);
        }
        else
        {
            actionButton.setVisibility(View.VISIBLE);
        }
    }
}

class CasinoView extends CardSharkView
{
    public CasinoView(Context context, AttributeSet attrs)
    {
        super(context, attrs, new TileMap(context), new Sprite(context));
    }
}

