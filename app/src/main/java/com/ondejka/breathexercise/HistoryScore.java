package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HistoryScore extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    TextView nickName1TextView, nickName2TextView;
    TextView date1TextView, time1TextView, time11TextView, time21TextView, time31TextView, time41TextView;
    TextView timeMax1TextView, timeAvg1TextView;
    TextView date2TextView, time2TextView, time12TextView, time22TextView, time32TextView, time42TextView;
    TextView timeMax2TextView, timeAvg2TextView;
    String[][] history = {{"","","","","","","","","",""},{"","","","","","","","","",""}};
    int historyRows;
    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i("HistoryScore 01", "OK");
        super.onCreate(savedInstanceState);
        Log.i("HistoryScore 02", "OK");

        setContentView(R.layout.activity_history_score);
        Log.i("HistoryScore 03", "OK");

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;

        nickName1TextView = findViewById(R.id.nickName1TextView);
        date1TextView = findViewById(R.id.date1TextView);
        time1TextView = findViewById(R.id.time1TextView);
        time11TextView = findViewById(R.id.time11TextView);
        time21TextView = findViewById(R.id.time21TextView);
        time31TextView = findViewById(R.id.time31TextView);
        time41TextView = findViewById(R.id.time41TextView);
        timeMax1TextView = findViewById(R.id.timeMax1TextView);
        timeAvg1TextView = findViewById(R.id.timeAvg1TextView);

        nickName2TextView = findViewById(R.id.nickName2TextView);
        date2TextView = findViewById(R.id.date2TextView);
        time2TextView = findViewById(R.id.time2TextView);
        time12TextView = findViewById(R.id.time12TextView);
        time22TextView = findViewById(R.id.time22TextView);
        time32TextView = findViewById(R.id.time32TextView);
        time42TextView = findViewById(R.id.time42TextView);
        timeMax2TextView = findViewById(R.id.timeMax2TextView);
        timeAvg2TextView = findViewById(R.id.timeAvg2TextView);
        

        load();
        showData();
    }

    public void load(){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int l = 0;
            while ((l < 4) && ((text = br.readLine()) != null)){
//                sb.append(text).append("\n");
                sb.append(text);
            }
            text = sb.toString();

            historyRows = 2;
            int j = 0;
            for (int k = 0; k < historyRows; k++) {
                for (int i = 0; ((i < 10) && (j < text.length())); i++) {
                    int start = j;
                    while ((j < text.length()) && (!text.substring(j, j + 1).equals(";"))) {
                        Log.i("load 01 ", text.substring(j, j + 1));
                        j++;
                    }
                    history[k][i] = text.substring(start, j);
                    Log.i("load 02 ", history[k][i]);
                    j++;
                }
            }
            Log.i("load 03 ", "OK");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showData(){
        //---- 1 ----
        nickName1TextView.setText(history[0][0]);
        date1TextView.setText(history[0][2] + " at " + history[0][3]);
        time1TextView.setText("");
        time11TextView.setText(SecondsToTimeString(Integer.parseInt(history[0][4])));
        time21TextView.setText( SecondsToTimeString(Integer.parseInt(history[0][5])));
        time31TextView.setText(SecondsToTimeString(Integer.parseInt(history[0][6])));
        time41TextView.setText(SecondsToTimeString(Integer.parseInt(history[0][7])));
        timeMax1TextView.setText("Max: " + SecondsToTimeString(Integer.parseInt(history[0][8])));
        timeAvg1TextView.setText("Avg: " + SecondsFloatToTimeString(Float.parseFloat(history[0][9])));

        //---- 2 ----
        if(historyRows > 1) {
            nickName2TextView.setText(history[1][0]);
            date2TextView.setText(history[1][2] + " at " + history[1][3]);
            time2TextView.setText("");
            time12TextView.setText(SecondsToTimeString(Integer.parseInt(history[1][4])));
            time22TextView.setText(SecondsToTimeString(Integer.parseInt(history[1][5])));
            time32TextView.setText(SecondsToTimeString(Integer.parseInt(history[1][6])));
            time42TextView.setText(SecondsToTimeString(Integer.parseInt(history[1][7])));
            timeMax2TextView.setText("Max: " + SecondsToTimeString(Integer.parseInt(history[1][8])));
            timeAvg2TextView.setText("Avg: " + SecondsFloatToTimeString(Float.parseFloat(history[1][9])));
        }else {
            date2TextView.setText("");
            time2TextView.setText("");
            time12TextView.setText("");
            time22TextView.setText("");
            time32TextView.setText("");
            time42TextView.setText("");
            timeMax2TextView.setText("");
            timeAvg2TextView.setText("");
        }
    }

    //duplicite with MainActivity
    private String SecondsToTimeString(int secondsTotal){
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal % 60;

        String secondsString;
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        }
        else secondsString = String.valueOf(seconds);

        return String.valueOf(minutes) + ":" + secondsString;
    }

    //duplicite with MainActivity
    @SuppressLint("LongLogTag")
    private String SecondsFloatToTimeString(Float secondsTotal){
        String timeString = "";
        String secondsString = String.valueOf(secondsTotal);

        int pos = secondsString.indexOf(".");
        if (pos != -1) {
            timeString =  SecondsToTimeString(Integer.parseInt(secondsString.substring(0, pos))) + secondsString.substring(pos);
        } else {
            timeString = SecondsToTimeString(Integer.parseInt(secondsString));
        }

        return timeString;
    }


}