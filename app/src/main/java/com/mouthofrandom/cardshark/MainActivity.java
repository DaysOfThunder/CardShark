package com.mouthofrandom.cardshark;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import com.mouthofrandom.cardshark.graphics.utility.CanvasView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Create Game Data
        GameData gd = new BasicGameData();

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //lock portrait mode
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //housekeeping
        super.onCreate(savedInstanceState);

        //call main activity
        setContentView(R.layout.activity_main);

        //Update Status Bar with current balance
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Typeface status_font = Typeface.createFromAsset(getAssets(),  "fonts/8bitOperatorPlusSC-Regular.ttf");
        TextView status_text = (TextView)findViewById(R.id.statustext);
        status_text.setTypeface(status_font);
        status_text.setText(formatter.format(gd.getBalance(getApplicationContext())));
    }
}
