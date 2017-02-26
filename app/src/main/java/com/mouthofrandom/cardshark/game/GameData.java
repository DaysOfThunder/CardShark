package com.mouthofrandom.cardshark.game;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tylerwoodfin on 2/23/17.
 * Used to store and update the game's balance.
 */

public interface GameData
{
    //Sets the overall balance
    double getBalance(Context c);

    //Sets the overall balance
    void setBalance(Context c, double balance);

    //Increments the balance (use negative numbers to decrement)
    void setIncrementBalance(Context c, double balance);

    //Used within getBalance and setBalance- you probably don't need to ever call this
    void saveData(Context c, String filename, String contents);

    //flashes a message on the Android device; use for testing or as a placeholder
    void toast(Context c, String message);

    class BasicGameData implements GameData
    {

        @Override
        public double getBalance(Context c)
        {
            String filename = "balance.csc";

            try
            {
                FileInputStream fileIn=c.openFileInput(filename);
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
                setBalance(c, 0);
                return 2000;
            }
            catch(IOException e)
            {
                toast(c, "IOException: " + filename);
            }
            return -1;
        }

        @Override
        public void setBalance(Context c, double balance)
        {
            saveData(c, "balance.csc", Double.toString(balance));
        }

        @Override
        public void setIncrementBalance(Context c, double balance) {
            setBalance(c, balance += getBalance(c));
        }

        @Override
        public void saveData(Context c, String filename, String contents)
        {
            try
            {
                FileOutputStream fos = c.openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(contents.getBytes());
                fos.close();
                //toast("Saved Successfully under Title: " + filename);
            }
            catch(Exception e)
            {
                toast(c, "There was a problem saving.");
            }
        }

        @Override
        public void toast(Context c, String message)
        {
            Toast.makeText(c, message,
                    Toast.LENGTH_SHORT).show();
        }
    }
}


