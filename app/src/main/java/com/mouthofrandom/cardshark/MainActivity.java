package com.mouthofrandom.cardshark;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mouthofrandom.cardshark.graphics.utility.CanvasView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Create Game Data
        GameData gd = new BasicGameData();

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        CanvasView customCanvas = (CanvasView) findViewById(R.id.mapcanvas);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen

        //Update Status Bar with current balance
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Typeface status_font = Typeface.createFromAsset(getAssets(),  "fonts/8bitOperatorPlusSC-Regular.ttf");
        TextView status_text = (TextView)findViewById(R.id.statustext);
        status_text.setTypeface(status_font);
        status_text.setText(formatter.format(gd.getBalance(getApplicationContext())));
    }
}
