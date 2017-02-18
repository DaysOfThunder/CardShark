package com.mouthofrandom.cardshark;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //set fullscreen

        //Update Status Bar with current balance
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Typeface status_font = Typeface.createFromAsset(getAssets(),  "fonts/8bitOperatorPlusSC-Regular.ttf");
        TextView status_text = (TextView)findViewById(R.id.statustext);
        status_text.setTypeface(status_font);
        status_text.setText(formatter.format(getBalance()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Save data as an internal text file. Can be replaced in a future iteration.
    public void saveData(String filename, String contents)
    {
        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(contents.getBytes());
            fos.close();
            //toast("Saved Successfully under Title: " + filename);
        }
        catch(Exception e)
        {
            toast("There was a problem saving.");
        }
    }

    //Save new Balance. Parameter replaces old balance.
    public void setBalance(double balance)
    {
        saveData("balance.csc", Double.toString(balance));
    }

    //Display a toast message
    public void toast(String message)
    {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    //Load Balance
    public double getBalance()
    {

        String filename = "balance.csc";

        try
        {
            FileInputStream fileIn=openFileInput(filename);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0)
            {
                // char to string conversion
                String string=String.copyValueOf(inputBuffer,0,charRead);
                s +=string;
            }
            InputRead.close();
            // toast(filename + " read successfully");
            return Double.parseDouble(s);
        }
        catch(FileNotFoundException e)
        {
            setBalance(0);
            return 2000;
        }
        catch(IOException e)
        {
            toast("IOException: " + filename);
        }
        return -1;
    }
}
