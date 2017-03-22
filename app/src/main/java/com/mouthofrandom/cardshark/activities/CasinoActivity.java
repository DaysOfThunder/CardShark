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

        setContentView(R.layout.activity_casino);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        CasinoView.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        CasinoView.resume();
    }


}

class CasinoView extends CardSharkView
{
    private static CasinoView instance = null;

    private static ActionButton actionButton = new ActionButton();

    public CasinoView(Context context, AttributeSet attrs)
    {
        super(context, attrs, new TileMap(context, actionButton.setContext(context)), new Sprite(context), actionButton);

        instance = this;
    }

    public static void pause()
    {
        instance.onPause();
    }

    public static void resume()
    {
        instance.onResume();
    }
}

